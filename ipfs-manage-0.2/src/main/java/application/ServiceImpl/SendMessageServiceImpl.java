package application.ServiceImpl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import application.MODEL.NET.Message;
import application.MODEL.NET.UnlineMessage;
import application.Service.SendMessageService;



@Service("SendMessageService")
public class SendMessageServiceImpl implements SendMessageService {

	private int port1 = 12138;
	private int port2 = 12137;
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	
	public boolean sendMessage(Message message) 
	{	
		DatagramSocket ds = null;
		try {
			ds  = new DatagramSocket();
			byte[] info = (message.getFilehash()+","+message.getBackupIp()+","+message.isBackup()+";").getBytes();
			int length = info.length;
			
			InetAddress addr = InetAddress.getByName(message.getBackupIp());
			
			DatagramPacket dp = new DatagramPacket(info,length,addr,port1);
			ds.send(dp);
			log.info("给主机：\""+message.getBackupIp()+"\"发送备份通知成功");
			return true;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			log.info("给主机:\""+message.getBackupIp()+"\"发送备份通知失败，原因：建立Socket失败");
			e.printStackTrace();
		}catch(UnknownHostException e) 
		{
			log.info("给主机:\""+message.getBackupIp()+"\"发送备份通知失败，原因：没有找到目标IP:\""+message.getBackupIp()+"\"");
			e.printStackTrace();
		}catch(IOException e) 
		{
			log.info("给主机:\""+message.getBackupIp()+"\"发送备份通知失败，原因：发送信息失败");
			e.printStackTrace();
		}finally 
		{
			ds.close();
		}
		return false;
	}

	public boolean sendReturnMessage(Message message) 
	{
		DatagramSocket ds = null;
		try {
			ds  = new DatagramSocket();
			byte[] info = (message.getFilehash()+","+message.getBackupIp()+","+message.isBackup()+";").getBytes();
			int length = info.length;
			
			if(!message.getSenderIp().equalsIgnoreCase("")) 
			{
				InetAddress addr = InetAddress.getByName(message.getSenderIp());
				
				DatagramPacket dp = new DatagramPacket(info,length,addr,port1);
				ds.send(dp);
				log.info("给主机：\""+message.getSenderIp()+"\"发送备份回执成功");
				return true;
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			log.info("给主机:\""+message.getSenderIp()+"\"发送备份回执失败，原因：建立Socket失败");
			e.printStackTrace();
		}catch(UnknownHostException e) 
		{
			log.info("给主机:\""+message.getSenderIp()+"\"发送备份回执失败，原因：没有找到目标IP:\""+message.getSenderIp()+"\"");
			e.printStackTrace();
		}catch(IOException e) 
		{
			log.info("给主机:\""+message.getSenderIp()+"\"发送备份回执失败，原因：发送信息失败");
			e.printStackTrace();
		}finally 
		{
			ds.close();
		}
		return false;
	}
	
	public boolean sendUnlineMessage(UnlineMessage message) 
	{
		
		DatagramSocket ds = null;
		try {
			ds  = new DatagramSocket();
			byte[] info = (message.getUnlineip()+","+message.getAimip()+";").getBytes();
			int length = info.length;
			//广播这个下线消息
			InetAddress addr = InetAddress.getByName("255.255.255.255");
			
			DatagramPacket dp = new DatagramPacket(info,length,addr,port2);
			ds.send(dp);
			log.info("给主机：\""+message.getAimip()+"\"发送下线消息成功");
			return true;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			log.info("给主机:\""+message.getAimip()+"\"发送下线消息成功，原因：建立Socket失败");
			e.printStackTrace();
		}catch(UnknownHostException e) 
		{
			log.info("给主机:\""+message.getAimip()+"\"发送下线消息失败，原因：没有找到目标IP:\""+message.getUnlineip()+"\"");
			e.printStackTrace();
		}catch(IOException e) 
		{
			log.info("给主机:\""+message.getAimip()+"\"发送下线消息失败，原因：发送信息失败");
			e.printStackTrace();
		}finally 
		{
			ds.close();
		}
		return false;
		
	}
	
	public int getPort1() {
		return port1;
	}

	public void setPort1(int port) {
		this.port1 = port;
	}


	public int getPort2() {
		return port2;
	}


	public void setPort2(int port2) {
		this.port2 = port2;
	}
}
