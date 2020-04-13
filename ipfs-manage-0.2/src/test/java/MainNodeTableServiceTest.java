import java.util.logging.Logger;

import org.junit.*;

import application.MODEL.NODE.MainNode;
import application.MODEL.TABLE.MainNodeTable;
import application.Service.MainNodeTableService;
import application.ServiceImpl.MainNodeTableServiceImpl;
import jdk.internal.org.jline.utils.Log;

public class MainNodeTableServiceTest {

	private Logger log = Logger.getLogger("asdadsd");
	
	//@Test
	public void test1() 
	{
		MainNodeTableService mts = new MainNodeTableServiceImpl();
		
		mts.createTable();
		MainNode mnode = new MainNode();
		mnode.setFilehash("askaldjasldaaaaksdj");
		mnode.setMainIp("12.131.22.11");
		
		mts.InsertNode(mnode);
	}
	
	//@Test
	public void test2() 
	{
		MainNodeTableService mts = new MainNodeTableServiceImpl();
		MainNodeTable mtable = mts.getTable();
		MainNode n = mts.getNodebyhash("askldjasldaksdj");
		System.out.println("filehash:"+n.getFilehash()+" ip::::"+n.getMainIp());
		for(int i=0;i<mtable.getNum();i++) 
		{
			System.out.println("hash:"+mtable.getTable().get(i).getFilehash()+" ip:"+mtable.getTable().get(i).getMainIp());
			
			
		}
	}
	@Test
	public void test3() 
	{
		MainNodeTableService mts = new MainNodeTableServiceImpl();
		MainNodeTable mtable = mts.getTable();
		for(int i=0;i<mtable.getNum();i++) 
		{
			//System.out.println("hash:"+mtable.getTable().get(i).getFilehash()+" ip:"+mtable.getTable().get(i).getMainIp());
			log.info(mtable.getTable().get(i).getFilehash()+"-"+mtable.getTable().get(i).getMainIp());
			
		}
		
		
	}
}
