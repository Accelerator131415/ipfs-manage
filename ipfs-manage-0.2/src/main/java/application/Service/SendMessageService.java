package application.Service;

import application.MODEL.NET.Message;
import application.MODEL.NET.UnlineMessage;

public interface SendMessageService {

	public boolean sendMessage(Message message);

	public boolean sendReturnMessage(Message message);
	
	public boolean sendUnlineMessage(UnlineMessage message);
}
