import java.util.logging.Logger;

import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import application.Controller.CenterController;
import application.Controller.backupController;
import application.Service.IpfsServices;
import application.Service.blockChainService;
import application.ServiceImpl.IpfsServicesImpl;
import application.ServiceImpl.blockChainServiceImpl;
import jdk.internal.org.jline.utils.Log;

public class backupServiceTest {

	private Logger log = Logger.getLogger("aaa");
	@Test
	public void test1() throws Exception 
	{
		String xmlpath = "classpath:applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		CenterController cc = (CenterController)applicationContext.getBean("CenterController");
		//backupController bc = (backupController)applicationContext.getBean("backupController");
		
		cc.start();
		
		Thread.sleep(1000*30);
		//bc.noticebackup("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT");
		cc.uploadFile("D:\\我的大学\\毕业设计\\实验文件夹", "ceshis");
		Thread.sleep(1000000);
	}
	
	//@Test
	public void test2() throws Exception 
	{
		IpfsServices ipfs = new IpfsServicesImpl();
		blockChainService bcs = new blockChainServiceImpl();
		bcs.start("192.168.99.1", "N:\\blockchain\\data\\keystore\\UTC--2020-03-25T11-33-08.192202800Z--2db370c14100919c6b8d14c5f71ff357d45fbdd3", "123");
		ipfs.start("192.168.99.1");
		log.info(ipfs.UploadFile("C:\\Users\\Administrator\\.selfDispatch\\节点维护表\\QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT.table"));
		log.info(bcs.getNodebackupTable("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT").getHash());
	}
}

