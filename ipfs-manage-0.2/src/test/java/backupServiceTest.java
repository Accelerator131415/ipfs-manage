import org.junit.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import application.Controller.CenterController;
import application.Controller.backupController;

public class backupServiceTest {

	
	@Test
	public void test1() throws Exception 
	{
		String xmlpath = "classpath:applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		CenterController cc = (CenterController)applicationContext.getBean("CenterController");
		backupController bc = (backupController)applicationContext.getBean("backupController");
		
		cc.start();
		
		Thread.sleep(1000*30);
		bc.selfbackup("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT");
		
	}
}

