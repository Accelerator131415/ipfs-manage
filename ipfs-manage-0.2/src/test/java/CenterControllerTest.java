import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.web3j.crypto.CipherException;

import application.Controller.CenterController;
import application.MODEL.NET.Message;
import application.Service.SendMessageService;


public class CenterControllerTest {

	private Logger log = Logger.getLogger("aaa");
	@Test
	public void test1() throws IOException, CipherException, Exception 
	{
		String xmlpath = "classpath:applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		
		CenterController cc = (CenterController)applicationContext.getBean("CenterController");
		//SendMessageService ss = (SendMessageService)applicationContext.getBean("SendMessageService");
		cc.start();
		cc.uploadFile("N:\\", "01-基于PKI的二维码安全认证系统-三未信安.docx");
		
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
