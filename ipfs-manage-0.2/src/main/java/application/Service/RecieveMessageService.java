package application.Service;

import java.util.List;
import application.MODEL.NET.Message;

public interface RecieveMessageService extends Runnable{

	
	public Message recieveMessage();
		
	
	public List<Message> getMessages();

	public boolean hasMessge();
	
	public void closeService();
}
