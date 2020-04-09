import java.net.InetAddress;
import java.util.logging.Logger;

import org.junit.Test;

import application.Controller.RecieveMessageController;
import application.MODEL.NET.Message;
import application.Service.RecieveMessageService;
import application.Service.SendMessageService;
import application.ServiceImpl.RecieveMessageServiceImpl;
import application.ServiceImpl.SendMessageServiceImpl;
import jdk.internal.org.jline.utils.Log;

public class RecieveMessageServiceTest {

	
	private Logger log = Logger.getLogger("log");
	@Test
	public void test1() throws Exception 
	{
		RecieveMessageService rms = new RecieveMessageServiceImpl();
		
		Thread thread = new Thread(rms);
		thread.start();
		
		
		//rms.closeService();
		
		//Thread.sleep(10000);
		//rms.closeService();
		while(true) 
		{
			
			if(!RecieveMessageController.messages.isEmpty()) 
			{
				Message message = RecieveMessageController.messages.remove(0);
				log.info("message:backupip:"+message.getBackupIp()+",filehash:"+message.getFilehash());
				log.info("message:senderip:"+message.getSenderIp()+",isbackup:"+message.isBackup());
			}
			
			
			Thread.sleep(1000);
		}
		
		
		//Message mes = rms.recieveMessage();
		
		
		
		//log.info("message:"+mes.getBackupIp()+","+mes.getFilehash()+","+mes.isBackup());
	//	log.info("over");
	}
}
