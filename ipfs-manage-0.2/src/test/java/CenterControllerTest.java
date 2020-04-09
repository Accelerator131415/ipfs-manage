import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import application.Controller.CenterController;
import application.MODEL.NET.Message;
import application.Service.SendMessageService;


public class CenterControllerTest {

	private Logger log = Logger.getLogger("aaa");
	@Test
	public void test1() 
	{
		String xmlpath = "classpath:applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		
		CenterController cc = (CenterController)applicationContext.getBean("CenterController");
		//SendMessageService ss = (SendMessageService)applicationContext.getBean("SendMessageService");
		cc.start();
		
		
		while(true) 
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//cc.exit();
//		
//		try {
//			
//			InetAddress addr = InetAddress.getLocalHost();
//			Message ms = new Message();
//			ms.setBackup(true);
//			ms.setBackupIp(addr.getHostAddress());
//			ms.setFilehash("asd");
//			ss.sendMessage(ms);
//			
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}catch(UnknownHostException ee) 
//		{
//			ee.printStackTrace();
//		}
		
		//log.info("关闭");
		
		//applicationContext.
	}
}
