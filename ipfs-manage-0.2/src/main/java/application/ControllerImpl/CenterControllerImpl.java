package application.ControllerImpl;
import application.Controller.CenterController;
import application.Controller.IpfsController;
import application.Controller.RecieveMessageController;
import application.Controller.SelfdispatchController;
import application.Controller.SendMessageController;
import application.Controller.backupController;
import application.Controller.blockChainController;
import application.MODEL.NET.UnlineMessage;
import application.MODEL.NODE.IPFSNode;
import application.MODEL.NODE.MainNode;
import application.MODEL.NODE.hashnode;
import application.MODEL.NODE.initNode;
import application.MODEL.TABLE.FileBackupTable;
import application.MODEL.TABLE.IPFSFileTable;
import application.MODEL.TABLE.MainNodeTable;
import application.MODEL.TABLE.NamehashTable;
import application.Service.*;
import io.ipfs.api.IPFS.Update;

import org.springframework.stereotype.Controller;
//import jdk.internal.org.jline.utils.Log;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.*;
import javax.swing.plaf.SliderUI;

@Controller("CenterController")
public class CenterControllerImpl implements CenterController {

	@Resource(name = "OnlineNodeTableService")
	private OnlineNodeTableService onlineTable;
	@Resource(name =  "blockChainController")
	private blockChainController blockChain;
	@Resource(name = "IpfsServices")
	private IpfsServices ipfs;
	@Resource(name = "RecieveMessageController")
	private RecieveMessageController recieveMessage;
	@Resource(name = "backupController")
	private backupController backup;
	@Resource(name = "SendMessageController")
	private SendMessageController sendMessage;
	@Resource(name = "FileBackupTableService")
	private FileBackupTableService nodefiletable;
	@Resource(name = "IPFSFileTableService")
	private IPFSFileTableService fileonlinetable;
	@Resource(name = "MainNodeTableService")
	private MainNodeTableService mainnodetable;
	@Resource(name = "SelfdispatchController")
	private SelfdispatchController selfdispatch;
	@Resource(name = "SendMessageService")
	private SendMessageService send;
	@Resource(name = "IpfsController")
	private IpfsController ipfsuse;
	@Resource(name = "NameHashTableService")
	private NameHashTableService namehash;
	@Resource(name = "initService")
	private initService initService;
	
	private Thread RecieveThread,backupThread,sendThread,selfdispatchThread;
	
	private Logger log = Logger.getLogger("ipfs-manage-controller");
	
	private boolean service =false;
	
	//启动中心服务器，作用：
	//1.更新在线节点表
	//2.开启接受信息的服务
	//3.开启备份服务
	//4.开启发送信息服务
	//5.开启自我检查待备份列表的服务
	public void start() throws Exception 
	{
		
		ipfsuse.startIpfs();
		blockChain.startBlockChain();
		
		try {
			String hash;
			InetAddress ip = InetAddress.getLocalHost();
			
			
			hashnode newone = new hashnode();
			//更新在线节点表
			do 
			{
				//blockChain.updateLocalTable();
				hashnode online = blockChain.updateLocalOnlinenodeTable();
				onlineTable.InsertIp(ip.getHostAddress());
				hash = ipfs.UploadFile(ipfs.getTableaddr()+onlineTable.getTABLE());	
				newone.setHash(hash);
				newone.setVersion(online.getVersion());
				
			}while(!blockChain.updateOnlinenodeTable(newone));
			
			
			
			
			//更新该节点所备份的所有节点有关该节点的在线情况
			blockChain.updateLocalNodefileTable(ip.getHostAddress());
			FileBackupTable nodetable = nodefiletable.getTable(ip.getHostAddress());
			
			IPFSNode inode = new IPFSNode();
			inode.setIp(ip.getHostAddress());
			inode.setOnline(true);
			
			for(int i=0;i<nodetable.getNum();i++) 
			{
				String filehash = nodetable.getFiles().get(i) ;
				hashnode ha = new hashnode();
				do 
				{			
					log.info(">>>???");
					log.info(filehash);
					hashnode nodeback = blockChain.updateLocalNodebackTable(filehash);		
					log.info("是这儿？");
					fileonlinetable.InsertNode(inode,filehash);
					String updatehash = ipfs.UploadFile(ipfs.getTableaddr()+fileonlinetable.getTABLE(filehash));
					ha.setHash(updatehash);
					ha.setVersion(nodeback.getVersion());
					log.info("还是这儿？");
					
				}while(!blockChain.updateNodebackTable(filehash, ha));
				
			}


			RecieveThread = new Thread(recieveMessage);
			RecieveThread.start();
			sendThread = new Thread(sendMessage);
			sendThread.start();
			backupThread = new Thread(backup);
			backupThread.start();
			selfdispatchThread = new Thread(selfdispatch);
			selfdispatchThread.start();
			
			service = true;
			log.info("ipfs自我调度备份程序启动");
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			log.info("启动ipfs自我调度程序异常，已退出");
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		
	}
	
	
	//关闭中心服务器
	//1.更新在线节点表
	//2.关闭接受信息的服务
	//3.关闭信息发送服务
	//4.关闭备份服务
	//5.关闭检查待备份列表服务
	//6.发送一个下线广播
	public void exit() throws Exception
	{
		try {
			//String hash;
			String newhash;
			InetAddress ip;
			ip = InetAddress.getLocalHost();
			
			
			log.info("正在准备关闭中心控制器···");
			hashnode newone = new hashnode();
			//更新在线节点表，将该地址从在线节点表中删除
			do
			{
				hashnode online = blockChain.updateLocalOnlinenodeTable();
				onlineTable.deleteIp(ip.getHostAddress());
				newhash = ipfs.UploadFile(ipfs.getTableaddr()+onlineTable.getTABLE());
				newone.setHash(newhash);
				newone.setVersion(online.getVersion());
				log.info("正在尝试更新在线节点表...");
			}while(!blockChain.updateOnlinenodeTable(newone));
			log.info("更新在线节点表成功");
			
			//RecieveThread.interrupt();
			//recieveService.closeService();
			
			
			//更新该节点所备份的所有节点有关该节点的在线情况
			
			FileBackupTable nodetable = nodefiletable.getTable(ip.getHostAddress());
			
			IPFSNode inode = new IPFSNode();
			inode.setIp(ip.getHostAddress());
			inode.setOnline(false);
			
			for(int i=0;i<nodetable.getNum();i++) 
			{
				String filehash = nodetable.getFiles().get(i) ;
				hashnode ha = new hashnode();
				do 
				{
					
					hashnode nodeback = blockChain.updateLocalNodebackTable(filehash);			
					fileonlinetable.updateNode(inode,filehash);
					String updatehash = ipfs.UploadFile(ipfs.getTableaddr()+fileonlinetable.getTABLE(filehash));
					ha.setHash(updatehash);
					ha.setVersion(nodeback.getVersion());
				}while(!blockChain.updateNodebackTable(filehash, ha));
				
			}
			
			recieveMessage.close();
			sendMessage.closeSevice();
			backup.closeService();
			selfdispatch.close();
			
			UnlineMessage unline =new UnlineMessage();
			unline.setAimip("255.255.255.255");
			unline.setUnlineip(ip.getHostAddress());
			send.sendUnlineMessage(unline);
			
			
			service = false;
			log.info("已关闭ipfs自我调度服务···");
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	
	public void downloadFile(String hash) throws Exception 
	{
		ipfsuse.download(hash);
	}


	@Override
	public void uploadFile(String addr, String file) throws Exception {
		// TODO Auto-generated method stub
		log.info("上传开始");
		ipfsuse.upload(addr, file);
		log.info("上传结束");
	}


	public boolean isService() {
		return service;
	}

	public NamehashTable getFileHashInfo() throws Exception 
	{
		blockChain.updateLocalTable();
		return namehash.getTable();
		
	}
	
	public IPFSFileTable getFileonlinebackupInfo(String filehash) throws Exception 
	{
		blockChain.updateLocalNodebackTable(filehash);
		return fileonlinetable.getIPFSFileTablebyhash(filehash);
	}


	@Override
	public initNode readInitInfo() throws IOException {
		// TODO Auto-generated method stub
		
		return initService.readInfo();
		
		//return null;
	}


	@Override
	public void initProgram(String ipfsIp, String blockChainIp, String filepath, String password) throws IOException {
		// TODO Auto-generated method stub
		
		initService.initProgram(ipfsIp, blockChainIp, filepath, password);
	}

	
	
}
