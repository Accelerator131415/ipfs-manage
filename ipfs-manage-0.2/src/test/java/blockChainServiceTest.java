import org.junit.Test;
import application.Service.*;
import application.ServiceImpl.blockChainServiceImpl;

public class blockChainServiceTest {

	@Test
	public void test1() 
	{
		blockChainService bcs = new blockChainServiceImpl();
		
		//bcs.getFilehashTable();
		//bcs.getMainnodeTable();
		
		while(true) 
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//bcs.updateMainnodeTable("MainnodeTable");
//		String hash = bcs.getMainnodeTable();
//		System.out.println(hash);
//		bcs.updateFilehashTable("this is FilehashTable");
//		bcs.getFilehashTable();
//		bcs.updateOnlineTable(hash);
		//bcs.getLimit();
		//bcs.getYu_main();
		//bcs.getYu();
	}
	
}
