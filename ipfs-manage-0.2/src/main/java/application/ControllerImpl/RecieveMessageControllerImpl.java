package application.ControllerImpl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.sound.midi.Receiver;

import org.springframework.stereotype.Controller;

import application.Controller.RecieveMessageController;
import application.Controller.SelfdispatchController;
import application.Controller.backupController;
import application.Controller.blockChainController;
import application.MODEL.NET.Message;
import application.MODEL.NET.UnlineMessage;
import application.MODEL.TABLE.IPFSFileTable;
import application.Service.*;

@Controller("RecieveMessageController")
public class RecieveMessageControllerImpl implements RecieveMessageController {

	
	
	@Resource(name = "blockChainController")
	blockChainController blockChain;
	@Resource(name = "IPFSFileTableService")
	IPFSFileTableService file_node;
	
	@Resource(name = "RecieveMessageService")
	RecieveMessageService recieveMessage;
	@Resource(name = "RecieveUnlineMessageService")
	RecieveUnlineMessageService recieveUnlineMessage;
	@Resource(name = "backupController")
	backupController backup;
	@Resource(name = "SendMessageService")
	SendMessageService sendService;
	
	@Resource(name = "SelfdispatchController")
	SelfdispatchController selfdispatch;
	
	
	private boolean service;
	private long waittime = 1000*30;
	Thread messageThread,unlinemessageThread;
	
	private Logger log = Logger.getLogger("ipfs-manage-Controller");
	
	public void close() 
	{
		recieveMessage.closeService();
		recieveUnlineMessage.closeService();
		service = false;
	}
	
//	public synchronized void addMessage(Message message) 
//	{
//		messages.add(message);
//	}
//	
//	public synchronized Message removeMessage(int location) 
//	{
//		return messages.remove(location);
//	}
//
//	public synchronized void addUnlineMessage(UnlineMessage unlinemessage) 
//	{
//		unlinemessages.add(unlinemessage);
//	}
//	
//	public synchronized UnlineMessage removeUnlinemessage(int location) 
//	{
//		return unlinemessages.remove(location);
//	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		(messageThread = new Thread(recieveMessage)).start();
		(unlinemessageThread = new Thread(recieveUnlineMessage)).start();
		service = true;
		
		InetAddress ip = null;
		log.info("正在尝试开启消息处理服务");
		try {
			ip = InetAddress.getLocalHost();
	
			while(service) 
			{
				while(!messages.isEmpty()) 
				{
					Message one = RecieveMessageController.removeMessage(0);
					
					if(one.isBackup()) 
					{
						if(backupController.backuplist.containsKey(one.getFilehash())) 
						{
							blockChain.updateLocalNodebackTable(one.getFilehash());
							IPFSFileTable node_filetable = file_node.getIPFSFileTablebyhash(one.getFilehash());							
							backupController.backuplist.get(one.getFilehash()).setNum(node_filetable.getOnlinenum());
						}		
					}
					
					else if(!one.isBackup() && one.getBackupIp().equalsIgnoreCase(ip.getHostAddress()))
					{						
						one.setBackup(backup.selfbackup(one.getFilehash()));
						sendService.sendReturnMessage(one);
						log.info("已备份文件："+one.getFilehash()+";");
						//sendService.sendMessage();						
					}	
					
					log.info("已处理来自："+one.getSenderIp()+"的消息");
				}
				
				
				while(!unlinemessages.isEmpty()) 
				{				
					UnlineMessage one = unlinemessages.remove(0);
					
					selfdispatch.selfdiapatch(one.getUnlineip());
					
					log.info("已处理来自:"+one.getUnlineip()+"的下线信息");
					
					
					
				}
							
				try {
					Thread.sleep(waittime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
}
