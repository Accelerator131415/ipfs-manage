import java.util.logging.Logger;

import org.junit.*;

import application.Service.OnlineNodeTableService;
import application.ServiceImpl.OnlineNodeTableServiceImpl;


public class OnlineNodeTableServiceTest {

	private Logger log = Logger.getLogger("asdas");
	
	//@Test
	public void test1() 
	{
		OnlineNodeTableService onts = new OnlineNodeTableServiceImpl();
		
		onts.createTable();
		onts.InsertIp("11.12.1.1");
		onts.InsertIp("127.0.0.1");
		onts.InsertIp("127.0.0.1");
		//onts.deleteIp("127.0.0.1");
		onts.getTable();
	}
	
	@Test
	public void test2() 
	{
		OnlineNodeTableService onts = new OnlineNodeTableServiceImpl();
		
		for(int i=0;i<onts.getTable().getNum();i++) 
		{
			log.info(onts.getTable().getOnlineNodes().get(i));
		}
	}
}
