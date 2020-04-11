package application.ControllerImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import application.Controller.SendMessageController;
import application.Controller.backupController;
import application.Controller.blockChainController;
import application.MODEL.NET.Message;
import application.MODEL.TABLE.IPFSFileTable;
import application.MODEL.TABLE.OnlineNodeTable;
import application.Service.IPFSFileTableService;
import application.Service.OnlineNodeTableService;
import application.Service.SendMessageService;


@Controller("SendMessageController")
public class SendMessageControllerImpl implements SendMessageController {
	
	
	private boolean service;
	private Logger log = Logger.getLogger("ipfs-manage-Controller");
	private long waittime = 1000*60;
	
	@Resource(name = "OnlineNodeTableService")
	OnlineNodeTableService onlinetable;
	@Resource(name = "blockChainController")
	blockChainController blockChain;
	@Resource(name = "SendMessageService")
	SendMessageService sendService;
	@Resource(name = "IPFSFileTableService")
	IPFSFileTableService nodebackuptable;
	public void closeSevice() 
	{
		service = false;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = true;
		
		log.info("正在启动备份信息发送服务");
		while(service) 
		{
			if(!backupController.backuplist.isEmpty()) 
			{
				try {
					blockChain.updateLocalTable();
				} catch (Exception e1) {
					// TODO Auto-generated catch block  
					e1.printStackTrace();
				}
				
				OnlineNodeTable table = onlinetable.getTable();
				boolean issend;
				Random r = new Random();
				
				Message one = new Message();
				
				one.setBackup(false);
				
				Set<String> set = backupController.backuplist.keySet();
				Iterator<String> it = set.iterator();
				List<String> hadsend = new ArrayList<String>();
				//每个文件都给
				while(it.hasNext()) 
				{
					String filehash = it.next();
					try {
						blockChain.updateLocalNodebackTable(filehash);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					IPFSFileTable nodebackup = nodebackuptable.getIPFSFileTablebyhash(filehash);
					
					one.setFilehash(filehash);
					for(int i=0;i<backupController.limit || i<table.getNum();i++) 
					{
						//设置要发送的主机IP。
						issend = false;
						one.setBackupIp(table.getOnlineNodes().get(r.nextInt()%table.getNum()));
						
						
						for(int a=0;a<hadsend.size();a++) 
						{
							if(hadsend.get(i).equalsIgnoreCase(one.getBackupIp()) || nodebackup.getNodebyIp(one.getBackupIp())!=null)
							{
								issend =true;
							}
						}
						if(issend) 
						{
							continue;
						}
						hadsend.add(one.getBackupIp());
						sendService.sendMessage(one);
						
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
		log.info("已关闭备份信息发送服务");
		
		
	}

}
