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
import application.MODEL.NET.UnlineMessage;
import application.Service.RecieveUnlineMessageService;

@Service("RecieveUnlineMessageService")
public class RecieveUnlineMessageServiceImpl implements RecieveUnlineMessageService {
	private int port = 12137;
	
	private boolean service;
	private List<UnlineMessage> messages = new ArrayList<UnlineMessage>();
	DatagramSocket ds;
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	public UnlineMessage recieveMessage() 
	{
		
		UnlineMessage message =  new UnlineMessage();
		int middle = 0 ,end = 0;
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
				if(data[i] == ',') 
				{
					middle=i;
				}
				else if(data[i]==';') 
				{
					end = i;
				}
				
			}
			String unlineip = new String(data, 0, middle-1);
			String aimip = new String(data,middle,end-middle-1);
			message.setUnlineip(unlineip);
			message.setAimip(aimip);
			
			
			
			log.info("接受到来自:\""+address.getHostAddress()+"\"的unlinemessage");
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
		}
		
		return message;
	}
	
	public List<UnlineMessage> getMessages()
	{
		return messages;
	}

	public boolean hasMessge() 
	{
		if(messages.isEmpty()) 
		{
			return false;
		}
		return true;
	}
	
	public void closeService() 
	{
		service = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		service = true;
		try {
			ds = new DatagramSocket(port);
			
			log.info("正在启动接受下线信息服务");
			while(service) 
			{
				if(Thread.currentThread().isInterrupted()) 
				{
					closeService();
					break;
				};
				
				UnlineMessage message = recieveMessage();	
				synchronized(this) 
				{
					RecieveMessageController.unlinemessages.add(message);
				}
				
			}
			
			log.info("已关闭接受下线信息的服务");
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			log.info("接受下线信息服务端口绑定失败");
			e.printStackTrace();
		}
		
		
	}
	
	
}
