package application.Service;

import java.util.List;

import application.MODEL.NET.*;

public interface RecieveUnlineMessageService  extends Runnable {

	
	public UnlineMessage recieveMessage();
	
	public List<UnlineMessage> getMessages();

	public boolean hasMessge();
	
	public void closeService();
	
}
