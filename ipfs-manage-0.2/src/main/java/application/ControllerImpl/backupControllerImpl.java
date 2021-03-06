package application.ControllerImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.sound.midi.Receiver;

import org.springframework.stereotype.Controller;

import application.Controller.IpfsController;
import application.Controller.RecieveMessageController;
import application.Controller.SendMessageController;
import application.Controller.backupController;
import application.Controller.blockChainController;
import application.MODEL.NODE.IPFSNode;
import application.MODEL.NODE.MainNode;
import application.MODEL.NODE.backupNode;
import application.MODEL.NODE.hashnode;
import application.MODEL.TABLE.*;
import application.Service.FileBackupTableService;
import application.Service.IPFSFileTableService;
import application.Service.IpfsServices;
import application.Service.MainNodeTableService;
import application.Service.NameHashTableService;
import application.Service.OnlineNodeTableService;
import application.Service.blockChainService;


@Controller("backupController")
public class backupControllerImpl implements backupController {


	private Logger log = Logger.getLogger("ipfs-manage-Controller");
	//private static ThreadLocal<>
	private boolean service;
	Thread recievemessageThread,sendmessageThread;
	
	private long waittime = 1000*10;
	
	@Resource(name = "IpfsServices")
	IpfsServices ipfs;
	@Resource(name = "blockChainController")
	blockChainController blockChain;
	@Resource(name = "MainNodeTableService")
	MainNodeTableService mainnodetable;
	@Resource(name = "NameHashTableService")
	NameHashTableService namehashtable;
	@Resource(name = "OnlineNodeTableService")
	OnlineNodeTableService onlinetable;
	@Resource(name = "IPFSFileTableService")
	IPFSFileTableService nodebackuptable;
	@Resource(name = "FileBackupTableService")
	FileBackupTableService nodefiletable;	
	//@Resource(name = "SendMessageController")
	//SendMessageController sendService;
	
	public void noticebackup(String filehash) throws Exception	
	{
		//OnlineNodeTable online = onlinetable.getTable();
		List<String> files = ipfs.getSonfile(filehash);
		
		for(int i=0;i<files.size();i++) 
		{
			backupNode newone = new backupNode();
			
			//log.info(">>>:"+files.get(i));
			//从区块链中下载该备份文件的已备份的节点表，如果曾经备份过，那就在原来的在线节点的数量的基础上去重新备份文件
			blockChain.updateLocalNodebackTable(files.get(i));
			IPFSFileTable itable = nodebackuptable.getIPFSFileTablebyhash(files.get(i));
			newone.setFilehash(files.get(i));
			newone.setNum(itable.getOnlinenum());
			
			backupController.addBackuplist(files.get(i), newone);
		}
	}

	//接受到通知，自己备份这个文件
	public boolean selfbackup(String filehash) 
	{
		try {
			blockChain.updateLocalNodebackTable(filehash);
			IPFSFileTable itable = nodebackuptable.getIPFSFileTablebyhash(filehash);
			
			if(itable.getOnlinenum()>=limit) 
			{
				//log.info("文件备份数已足够，不需要再进行备份");
				return true;			
			}
				
			ipfs.backup(filehash);
			
			
		
			InetAddress ip;
			
			log.info("正在备份:"+filehash+"文件");
		
			ip = InetAddress.getLocalHost();
			
			String hash;
			String mainnodehash; 
			
			//检查是否更新主节点；
			hashnode update = new hashnode();
			do 
			{
				//blockChain.updateLocalTable();
				hashnode main = blockChain.updateLocalMainnodeTable();
				blockChain.updateLocalOnlinenodeTable();
				MainNodeTable mainnode = mainnodetable.getTable();
				OnlineNodeTable onlinenode = onlinetable.getTable();
				
				mainnode.getNodebyhash(filehash);
				
				//如果该文件的主节点在线，则不修改主节点，进行下一环节。
				if(onlinenode.isOnline(mainnode.getNodebyhash(filehash).getMainIp())) 
				{
					break;
				}
				
				MainNode mnode = new MainNode();
				mnode.setFilehash(filehash);
				mnode.setMainIp(ip.getHostAddress());
				mainnodetable.InsertNode(mnode);
				mainnodehash = ipfs.UploadFile(ipfs.getTableaddr()+mainnodetable.getTABLE());
				
				update.setHash(mainnodehash);
				update.setVersion(main.getVersion());
				
				
			}while(!blockChain.updateMainnodeTable(update));
			
			IPFSNode inode = new IPFSNode();
			update = new hashnode();
			inode.setIp(ip.getHostAddress());
			inode.setOnline(true);
			//更新文件的备份节点表
			do 
			{
				blockChain.updateLocalOnlinenodeTable();
				hashnode nodebackup = blockChain.updateLocalNodebackTable(filehash);
				nodebackuptable.InsertNode(inode, filehash);
				log.info("应该是这里！！！！！");
				hash = ipfs.UploadFile(ipfs.getTableaddr()+nodebackuptable.getTABLE(filehash));
				update.setHash(hash);
				update.setVersion(nodebackup.getVersion());				
				log.info("正在尝试更新"+filehash+".table");
				
				
			}
			while(!blockChain.updateNodebackTable(filehash,update));
			log.info("更新"+filehash+".table成功");
			
			update = new hashnode();
			//更新本地备份文件表
			do 
			{
				hashnode nodefile = blockChain.updateLocalNodefileTable(ip.getHostAddress());
				nodefiletable.InsertFile(filehash);
				hash = ipfs.UploadFile(ipfs.getTableaddr()+nodefiletable.getTABLE(ip.getHostAddress()));
				update.setHash(hash);
				update.setVersion(nodefile.getVersion());
				log.info("正在尝试更新"+ip.getHostAddress()+".table");
			}while(!blockChain.updateNodefileTable(ip.getHostAddress(), update));
			
			log.info("更新"+ip.getHostAddress()+".table成功");			
			log.info("已成功备份:"+filehash+"文件");
			return true;
			
		
		
			
			
			
		
		
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			log.info("备份："+filehash+"文件失败，原因：没有找到主机IP");
			e.printStackTrace();			
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
		
	}

	
	public void closeService() 
	{
		
		//recieveService.close();
		//sendService.closeSevice();
		service = false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = true;
		//(recievemessageThread = new Thread(recieveService)).start();;
		//(sendmessageThread = new Thread(sendService)).start();
		
		
		log.info("正在启动备份文件服务");
		
		
		
		while(service) 
		{
			if(!backuplist.isEmpty()) 
			{
				Set<String> set = backuplist.keySet();
				Iterator<String> it = set.iterator();
				List<String> needRemove = new ArrayList<String>();
				while(it.hasNext()) 
				{
					String filehash = it.next();
					//当备份数量达到要求时，删除链表里该节点的信息，并且为其选出主节点
					if(backuplist.get(filehash).getNum()>=limit) 
					{
						needRemove.add(filehash);
						//backupController.removeBackupNode(filehash);
						//randomselectMain(filehash);
					}
					
				
//					try {
//						Thread.sleep(100);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
				
				for(int i=0;i<needRemove.size();i++) 
				{
					backupController.removeBackupNode(needRemove.get(i));
				}
				
			}
			
			try {
				Thread.sleep(waittime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
		}
		
	}

//	@Override
//	public synchronized backupNode removeBackupNode(String filehash) {
//		// TODO Auto-generated method stub
//		return backuplist.remove(filehash);
//	}

	@Override
	public void randomselectMain(String filehash) throws InterruptedException, Exception {
		// TODO Auto-generated method stub
		
		blockChain.updateLocalTable();
		hashnode nodeback = blockChain.updateLocalNodebackTable(filehash);
		IPFSFileTable nodebackup = nodebackuptable.getIPFSFileTablebyhash(filehash);
		MainNodeTable mtable =mainnodetable.getTable();
		MainNode existNode = mtable.getNodebyhash(filehash);
		
		//检查原来是否有主节点且检查其是否可用
		if(existNode!=null && nodebackup.getNodebyIp(existNode.getMainIp()).isOnline()) 
		{
			log.info("原主节点仍可用，保持原主节点");
			return;
		}
		
		//检查文件有无可用的备份节点
		if(nodebackup.getOnlinenum() <= 0) 
		{
			log.info("为文件"+filehash+"选举主节点失败，原因：该文件在其他节点的无备份");
			return ;
		}
		
		Random r = new Random();
		
		IPFSNode inode;
		
		while(!(inode = nodebackup.getNodes().get(r.nextInt()%nodebackup.getNodes_had())).isOnline()) ;
		
		
		MainNode mnode = new MainNode();
		mnode.setFilehash(filehash);
		mnode.setMainIp(inode.getIp());
		
		hashnode one = new hashnode();
		mainnodetable.InsertNode(mnode);
		String newhash = ipfs.UploadFile(ipfs.getTableaddr()+mainnodetable.getTABLE());
		one.setHash(newhash);
		one.setVersion(nodeback.getVersion());
		blockChain.updateMainnodeTable(one);
		log.info("为文件："+filehash+"选取主节点成功,主节点为："+mnode.getMainIp());
	}

	
	
	
}
