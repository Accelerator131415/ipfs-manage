import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.junit.Test;

import application.MODEL.NET.Message;
import application.Service.RecieveMessageService;
import application.Service.SendMessageService;
import application.ServiceImpl.RecieveMessageServiceImpl;
import application.ServiceImpl.SendMessageServiceImpl;
import jdk.internal.org.jline.utils.Log;

public class SendMessageServiceTest {

	private Logger log = Logger.getLogger("LOG");
	@Test
	public void test1() throws UnknownHostException 
	{
		SendMessageService sms = new SendMessageServiceImpl();
		
		RecieveMessageService rms = new RecieveMessageServiceImpl();
		
		InetAddress addr = InetAddress.getLocalHost();
		
		Message me = new Message();
		me.setBackupIp(addr.getHostAddress());
		me.setFilehash("Qma4vPVnAe3uadUdcgTgVNyfTWdRgNpEAKT1RNVBaDhSZL");
		me.setBackup(false);
		
		log.info(addr.getHostAddress().equalsIgnoreCase("10.0.75.1")+"");
		
		sms.sendMessage(me);
		
		rms.recieveMessage();
	}
}
