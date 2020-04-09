import org.junit.*;

import Service.OnlineNodeTableService;
import ServiceImpl.OnlineNodeTableServiceImpl;


public class OnlineNodeTableServiceTest {

	@Test
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
}
