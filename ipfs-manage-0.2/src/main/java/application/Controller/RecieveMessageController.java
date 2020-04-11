package application.Controller;

import java.util.ArrayList;
import java.util.List;
import application.MODEL.NET.*;


//这是个处理接收到信息的控制器
public interface RecieveMessageController extends Runnable{

	public static List<Message> messages = new ArrayList<Message>();
	public static List<UnlineMessage> unlinemessages = new ArrayList<UnlineMessage>();

		
	//关闭接受信息服务
	public void close();
	
	
	public static void addMessage(Message message) 
	{
		synchronized(RecieveMessageController.class) 
		{
			messages.add(message);
		}
	}
	
	
	public static Message removeMessage(int location) 
	{
		synchronized(RecieveMessageController.class) 
		{
			return messages.remove(location);
		}
		
	}
	
	public static void addUnlineMessage(UnlineMessage unlinemessage) 
	{
		synchronized(RecieveMessageController.class) 
		{
			unlinemessages.add(unlinemessage);
		}
		
	}
	
	public static UnlineMessage removeUnlinemessage(int location) 
	{
		synchronized(RecieveMessageController.class) 
		{
			return unlinemessages.remove(location);
		}
	}
	
}
