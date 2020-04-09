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
	
	
	public void addMessage(Message message);
	
	public Message removeMessage(int location);
	
	public void addUnlineMessage(UnlineMessage unlinemessage);
	
	public UnlineMessage removeUnlinemessage(int location);
	
}
