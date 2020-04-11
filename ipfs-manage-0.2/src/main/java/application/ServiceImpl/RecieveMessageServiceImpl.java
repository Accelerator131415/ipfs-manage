package application.ServiceImpl;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import application.Controller.RecieveMessageController;
import application.MODEL.NET.Message;
import application.Service.RecieveMessageService;


@Service("RecieveMessageService")
public class RecieveMessageServiceImpl implements RecieveMessageService {

	private int port = 12138;
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	private List<Message> messages = new ArrayList<Message>();
	private boolean service;
	private DatagramSocket ds;
	
	
 	public Message recieveMessage() 
	{
		
		Message message =  new Message();
		int firestdou = 0,seconddou = 0,end = 0;
		boolean firstdou = true;
		try 
		{
			
			byte[] recieve = new byte[1024];
			int length = recieve.length;
			DatagramPacket dp = new DatagramPacket(recieve,length);
			ds.receive(dp);
			
			InetAddress address = dp.getAddress();
			byte[] data = dp.getData();
			for(int i=0;i<dp.getLength();i++) 
			{
				if(data[i] == ',' && firstdou) 
				{
					firestdou=i;
					firstdou = false;
				}
				else if(data[i] == ',' && !firstdou) 
				{
					seconddou = i;
				}
				else if(data[i]==';') 
				{
					end = i;
				}
				
			}
			String hash = new String(data, 0, firestdou);
			String backupip = new String(data,firestdou+1,seconddou-firestdou-1);
			String bool = new String(data,seconddou+1,end-seconddou-1);
			message.setFilehash(hash);
			if(bool.equalsIgnoreCase("true")) 
			{
				message.setBackup(true);
				message.setBackupIp(backupip);
				message.setSenderIp(address.getHostAddress());
				message.setFilehash(hash);
			}else 
			{
				message.setBackup(false);
				message.setBackupIp(backupip);
				message.setSenderIp(address.getHostAddress());
				message.setFilehash(hash);
			}
			
			
			log.info("接受到来自:\""+backupip+"\"的message");
			//log.info("hash:"+hash+", bool:"+bool+"backupIp:"+message.getBackupIp());
		}
		catch(Exception e) 
		{
			log.info("目前没有接受到消息");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//e.printStackTrace();
		}
		
		return message;
	}

 	public List<Message> getMessages()
 	{
 		return messages;
 	}
 	
 	public boolean hasMessge() 
 	{
 		return !messages.isEmpty();
 	}
 	
 	public void closeService() 
 	{
 		ds.close();
 		service = false;
 	}
 	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = true;
		try {
			ds = new DatagramSocket(port);
			log.info("启动接受信息的服务");
			while(service) 
			{
				Message message = recieveMessage();
				synchronized(this)
				{
					RecieveMessageController.messages.add(message);
				}
				
			}	
			log.info("关闭接受信息的服务");
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
		//	log.info("接受信息服务绑定端口失败");
			e.printStackTrace();
		}
		
	}
}
