import java.util.logging.Logger;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import application.Controller.RecieveMessageController;
import application.ControllerImpl.RecieveMessageControllerImpl;
import application.MODEL.NET.Message;

public class RecieveControllerTest {

	private Logger log = Logger.getLogger("ipfs-test");
	
	@Test
	public void test1() 
	{
		String xmlpath = "classpath:applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		
		
		RecieveMessageController r = (RecieveMessageController)applicationContext.getBean("RecieveMessageController");;
		
		new Thread(r).start();
		
		while(true)
		{
			if(!RecieveMessageController.messages.isEmpty()) 
			{
				Message a = r.removeMessage(0);
				log.info("senderip:"+a.getSenderIp()+",bakcupip:"+a.getBackupIp()
						+",filehash:"+a.getFilehash());
			}
		}
	}
}
