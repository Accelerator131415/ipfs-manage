package application.ControllerImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.web3j.crypto.CipherException;

import application.Controller.blockChainController;
import application.MODEL.NODE.hashnode;
import application.MODEL.NODE.initNode;
import application.MODEL.TABLE.OnlineNodeTable;
import application.Service.*;
import application.blockChain.Table;

@Controller("blockChainController")
public class blockChainControllerImpl implements blockChainController {

	private Logger log = Logger.getLogger("ipfs-manage-Controller");
	
	@Resource(name = "IpfsServices")
	IpfsServices ipfs;
	@Resource(name = "blockChainService")
	blockChainService blockChain;
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
	@Resource(name = "initService")
	initService initService;
	
	private static List<String> MainnodehadSend;
	private static List<Thread> MainnodeThreads;
	private static boolean MainnodeSend;
	private static boolean MainnodeLock;
	private static boolean MainnodeSucess;
	
	private static List<String> NamehashhadSend;
	private static List<Thread> NamehashThreads;
	private static boolean NamehashSend;
	private static boolean NamehashLock;
	private static boolean NamehashSucess;
	
	private static List<String> OnlinehadSend;
	private static List<Thread> OnlineThreads;
	private static boolean OnlineSend;
	private static boolean OnlineLock;
	private static boolean OnlineSucess;
	
	private static List<String> NodefilehadSend;
	private static List<Thread> NodefileThreads;
	private static boolean NodefileSend;
	private static boolean NodefileLock;
	private static boolean NodefileSucess;
	
	private static List<String> NodebackuphadSend;
	private static List<Thread> NodebackupThreads;
	private static boolean NodebackupSend;
	private static boolean NodebackupLock;
	private static boolean NodebackupSucess;
	
	@Override
	public void updateLocalTable() throws Exception 
	{
		hashnode MainnodeTable = blockChain.getMainnodeTable();
		hashnode NamehashTable = blockChain.getFilehashTable();
		hashnode OnlinenodeTable = blockChain.getOnlineTable();
		
		ipfs.DownloadTable(MainnodeTable.getHash(), mainnodetable.getTABLE());
		ipfs.DownloadTable(NamehashTable.getHash(), namehashtable.getTABLE());
		ipfs.DownloadTable(OnlinenodeTable.getHash(), onlinetable.getTABLE());
		
		log.info("更新本地表完成");
		
	}
	@Override
	public hashnode updateLocalNodefileTable(String ip) throws Exception 
	{
		hashnode hash = blockChain.getNodeFiletable(ip);
		ipfs.DownloadTable(hash.getHash(),nodefiletable.getTABLE(ip));
		
		log.info("更新:\""+ip+"\"节点备份文件表完成");
		return hash;
	}
	@Override
	public hashnode updateLocalNodebackTable(String filehash) throws Exception 
	{
		hashnode hash = blockChain.getNodebackupTable(filehash);
		ipfs.DownloadTable(hash.getHash(),nodebackuptable.getTABLE(filehash));
		//log.info("更新:\""+filehash+"\"的节点在线表成功");
		return hash;
	}
	@Override
	public hashnode updateLocalMainnodeTable() throws Exception {
		// TODO Auto-generated method stub
		hashnode hash = blockChain.getMainnodeTable();
		ipfs.DownloadTable(hash.getHash(), mainnodetable.getTABLE());
		
		return hash;
	}
	@Override
	public hashnode updateLocalNamehashTable() throws Exception {
		// TODO Auto-generated method stub
		
		hashnode hash = blockChain.getFilehashTable();
		ipfs.DownloadTable(hash.getHash(), namehashtable.getTABLE());
		return hash;
	}
	@Override
	public hashnode updateLocalOnlinenodeTable() throws Exception {
		// TODO Auto-generated method stub
		hashnode hash = blockChain.getOnlineTable();
		ipfs.DownloadFile(hash.getHash(), onlinetable.getTABLE());
		
		return hash;
	}	
	@Override
	public boolean updateMainnodeTable(hashnode hash) throws Exception 
	{
		updateLocalOnlinenodeTable();
		OnlineNodeTable online = onlinetable.getTable();
		MainnodehadSend = new ArrayList<String>();
		MainnodeThreads = new ArrayList<Thread>();
		MainnodeSucess = false;
		MainnodeLock = false;
		MainnodeSend = true;
		Random r = new Random();
		while(MainnodeSend)
		{
			
			Thread thread;
			(thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int num = r.nextInt(online.getNum())%online.getNum();
					if(!isMainnodeSend(online.getOnlineNodes().get(num)))
					{
						MainnodehadSend.add(online.getOnlineNodes().get(num));
						boolean sucess;
						try {
							sucess = blockChain.updateMainnodeTable(hash,online.getOnlineNodes().get(num));
						
							if(MainnodeLock) 
							{
								Thread.sleep(100000000);
							}
							
							MainnodeLock = true;
							
							MainnodeSucess = sucess;
							
							MainnodeSend = false;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}				
					}
				}})).start();
			
			MainnodeThreads.add(thread);
			
			Thread.sleep(100);
		}
		
		for(int i=0;i<MainnodeThreads.size();i++) 
		{
			MainnodeThreads.get(i).interrupt();
		}
		return MainnodeSucess;
	}
	@Override
	public boolean updateNamehashTable(hashnode hash) throws Exception 
	{
		updateLocalOnlinenodeTable();
		OnlineNodeTable online = onlinetable.getTable();
		NamehashhadSend = new ArrayList<String>();
		NamehashThreads = new ArrayList<Thread>();
		NamehashSend = true;
		NamehashLock = false;
		NamehashSucess = false;
		
		Random r = new Random();
		while(NamehashSend) 
		{
			Thread thread;
			(thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int num = r.nextInt(online.getNum())%online.getNum();
					if(!isNamehashSend(online.getOnlineNodes().get(num))) 
					{
						NamehashhadSend.add(online.getOnlineNodes().get(num));
						boolean sucess;
						try {
							sucess = blockChain.updateFilehashTable(hash, online.getOnlineNodes().get(num));
							if(NamehashLock) 
							{
								Thread.sleep(100000000);
							}
							
							NamehashLock  = true;
							
							NamehashSucess = sucess;
							
							NamehashSend = false;
							
						}
						catch(InterruptedException e) 
						{
							e.printStackTrace();
						}
						catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						
						
					}
					
					
				}})).start();
			
			NamehashThreads.add(thread);
			Thread.sleep(100);
		}
		
		for(int i=0;i<NamehashhadSend.size();i++) 
		{
			NamehashThreads.get(i).interrupt();
		}
		
		return NamehashSucess;
	}
	@Override
	public boolean updateOnlinenodeTable(hashnode hash) throws InterruptedException
	{
		OnlineNodeTable online = onlinetable.getTable();
		OnlinehadSend = new ArrayList<String>();
		OnlineThreads = new ArrayList<Thread>();
		OnlineSend = true;
		OnlineLock = false;
		OnlineSucess = false;
		Random r = new Random();
		
		while(OnlineSend) 
		{
			Thread thread;
			(thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int num = r.nextInt(online.getNum())%online.getNum();
					if(!isOnlineSend(online.getOnlineNodes().get(num))) 
					{
						OnlinehadSend.add(online.getOnlineNodes().get(num));
						boolean sucess;
						try {
							sucess = blockChain.updateOnlineTable(hash, online.getOnlineNodes().get(num));
						
							if(OnlineLock) 
							{
								Thread.sleep(100000000);
							}
							
							OnlineLock = true;
							
							OnlineSucess = sucess;
							
							OnlineSend = false;
							
						
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}})).start();
		
			OnlineThreads.add(thread);
			Thread.sleep(100);
		}
		
		
		for(int i=0;i<OnlineThreads.size();i++) 
		{
			OnlineThreads.get(i).interrupt();
		}
		
		
		return OnlineSucess;
	}	
	@Override
	public boolean updateNodefileTable(String ip, hashnode hash) throws Exception {
		// TODO Auto-generated method stub
		updateLocalOnlinenodeTable();
		OnlineNodeTable online = onlinetable.getTable();
		NodefilehadSend = new ArrayList<String>();
		NodefileThreads = new ArrayList<Thread>();
		NodefileSend = true;
		NodefileLock = false;
		NodefileSucess = false;
		Random r = new Random();
		
		while(NodefileSend) 
		{
			Thread thread;
			(thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int num = r.nextInt(online.getNum())%online.getNum();
					if(!isNodefileSend(online.getOnlineNodes().get(num))) 
					{
						NodefilehadSend.add(online.getOnlineNodes().get(num));
						boolean sucess;
						
						try {
							sucess = blockChain.updateNodeFiletable(ip, hash, online.getOnlineNodes().get(num));
							
							if(NodefileLock) 
							{
								Thread.sleep(1000000000);
							}
							
							NodefileLock = true;
							
							NodefileSucess = sucess;
							
							NodefileSend = false;
							
							
						
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					
				}})).start();
			
			
			NodefileThreads.add(thread);
			Thread.sleep(100);
		}
		
		for(int i=0;i<NodefilehadSend.size();i++) 
		{
			NodefileThreads.get(i).interrupt();
		}
		
		return NodefileSucess;
	}
	@Override		
	public boolean updateNodebackTable(String filehash,hashnode hash) throws Exception
	{
		updateLocalOnlinenodeTable();
		OnlineNodeTable online = onlinetable.getTable();
		NodebackuphadSend = new ArrayList<String>();
		NodebackupThreads = new ArrayList<Thread>();
		NodebackupSend = true;
		NodebackupLock = false;
		NodebackupSucess = false;
		Random r = new Random();
		
		while(NodebackupSend) 
		{
			Thread thread;
			(thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					int num = r.nextInt(online.getNum())%online.getNum();
					if(isNodebackupSend(online.getOnlineNodes().get(num))) 
					{
						NodebackuphadSend.add(online.getOnlineNodes().get(num));
						boolean sucess;
						try {
							sucess = blockChain.updateNodebackupTable(filehash, hash, online.getOnlineNodes().get(num));
							
							if(NodebackupLock) 
							{
								Thread.sleep(1000000000);
							}
							
							NodebackupLock = true;
							
							NodebackupSucess = sucess;
							
							NodebackupSend = false;
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
					}					
				}})).start();
			
			NodebackupThreads.add(thread);
			Thread.sleep(100);
		}
		
		for(int i=0;i<NodebackupThreads.size();i++) 
		{
			NodebackupThreads.get(i).interrupt();
		}
		
		return false;
	}

	
//	public hashnode updateLocalNodebackTable(String filehash) 
//	{
//		//String hash = blockChain.getNodebackupTable(filehash);
//		
//		//ipfs.DownloadTable(hash, nodebackuptable.getTABLE(filehash));
//		
//		log.info("更新本地节点备份表成功");
//		return null;
//	}
	
//	public void updateLocalNodefileTable(String ip) 
//	{
//		String hash = blockChain.getNodeFiletable(ip);
//		ipfs.DownloadTable(hash, nodefiletable.getTABLE(ip));
//	}

	@Override
	public void startBlockChain() throws IOException, CipherException {
		// TODO Auto-generated method stub
		initNode info = initService.readInfo();
		blockChain.start(info.getBlockChainIp(), info.getBlockChainFilepath(), info.getBlockChainPassword());
	}


	public boolean isMainnodeSend(String blockip) 
	{
		for(int i=0;i<MainnodehadSend.size();i++) 
		{
			if(MainnodehadSend.get(i).equalsIgnoreCase(blockip)) 
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isNamehashSend(String blockIp) 
	{
		for(int i=0;i<NamehashhadSend.size();i++)
		{
			if(NamehashhadSend.get(i).equalsIgnoreCase(blockIp)) 
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isOnlineSend(String blockIp) 
	{
		for(int i=0;i<OnlinehadSend.size();i++) 
		{
			if(OnlinehadSend.get(i).equalsIgnoreCase(blockIp)) 
			{
				return true;
			}
		}
		
		return false;
	}

	public boolean isNodefileSend(String blockIp) 
	{
		for(int i=0;i<NodefilehadSend.size();i++) 
		{
			if(NodefilehadSend.get(i).equalsIgnoreCase(blockIp)) 
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isNodebackupSend(String blockIp) 
	{
		for(int i=0;i<NodebackuphadSend.size();i++) 
		{
			if(NodebackuphadSend.get(i).equalsIgnoreCase(blockIp)) 
			{
				return true;
			}
		}
		return false;
	}
}
