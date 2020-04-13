package application.ControllerImpl;

import application.MODEL.NODE.MainNode;
import application.MODEL.TABLE.*;
import application.Service.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import application.Controller.*;

@Controller("SelfdispatchController")
public class SelfdispatchControllerImpl implements SelfdispatchController {

	private Logger log = Logger.getLogger("ipfs-manage-Controller");
	private boolean service;
	private long waittime = 1000*10;
	
	@Resource(name = "blockChainController")
	blockChainController blockChain;
	@Resource(name = "MainNodeTableService")
	MainNodeTableService mainnodetable;
	@Resource(name = "IPFSFileTableService")
	IPFSFileTableService nodebackuptable;
	@Resource(name = "FileBackupTableService")
	FileBackupTableService nodefiletable;
	@Resource(name = "backupController")
	backupController backup;
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
		service = false;
	}

	@Override
	public boolean isNeeddispatch(String filehash) throws Exception {
		// TODO Auto-generated method stub
		blockChain.updateLocalNodebackTable(filehash);
		blockChain.updateLocalTable();
		MainNodeTable mainnode = mainnodetable.getTable();
		IPFSFileTable nodebackup = nodebackuptable.getIPFSFileTablebyhash(filehash);
		
		MainNode main  = mainnode.getNodebyhash(filehash);
		
		if(nodebackup.getNodebyIp(main.getMainIp()).isOnline()) 
		{
			
			//在线节点的数量/limit的数量来求出在线率，在线率低于阈值时，返回true
			if(((double)nodebackup.getOnlinenum()/(double)nodebackup.getLimit()) < nodebackup.getYu_main())
			{
				return true;
			}
			return false;	
		}
		else if(!nodebackup.getNodebyIp(main.getMainIp()).isOnline()) 
		{
			if(((double)nodebackup.getOnlinenum()/(double)nodebackup.getLimit()) < nodebackup.getYu_no_main())
			{
				return true;
			}
			 
			return false;
		}	
		return false;
	}

	@Override
	public void selfdiapatch(String ip) 
	{
		// TODO Auto-generated method stub
		
		
		try {
			
			blockChain.updateLocalTable();
			blockChain.updateLocalNodefileTable(ip);
			InetAddress local;
			
			local = InetAddress.getLocalHost();
			
			blockChain.updateLocalNodefileTable(local.getHostAddress());
			FileBackupTable nodefile = nodefiletable.getTable(ip);
			FileBackupTable localfile = nodefiletable.getTable(local.getHostAddress());
			for(int i=0;i<nodefile.getFiles().size();i++) 
			{
				if(isNeeddispatch(nodefile.getFiles().get(i))) 
				{
					if(!localfile.isExistFile(nodefile.getFiles().get(i))) 
					{
						backup.selfbackup(nodefile.getFiles().get(i));
					}	
					backup.noticebackup(nodefile.getFiles().get(i));
				}
				
			}	
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			log.info("自我调度失败，原因：没有找到主机IP");
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		service = true;
		log.info("正在启动备份检查服务");
		
		while(service) 
		{
			if(!backupController.backuplist.isEmpty()) 
			{
				Set<String> set = backupController.backuplist.keySet();
				Iterator<String> it = set.iterator();
				//更新备份控制器里的每个需要备份文件的已备份情况
				while(it.hasNext()) 
				{
					String filehash = it.next();
					try 
					{
						blockChain.updateLocalNodebackTable(filehash);
					}
					catch (Exception e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					IPFSFileTable itable = nodebackuptable.getIPFSFileTablebyhash(filehash);
					synchronized(backupController.class) 
					{
						backupController.backuplist.get(filehash).setNum(itable.getOnlinenum());
					}
					
					
					
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
	
	
}
