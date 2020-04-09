import org.junit.*;

import MODEL.NODE.MainNode;
import MODEL.TABLE.MainNodeTable;
import Service.MainNodeTableService;
import ServiceImpl.MainNodeTableServiceImpl;

public class MainNodeTableServiceTest {

	
	public void test1() 
	{
		MainNodeTableService mts = new MainNodeTableServiceImpl();
		
		mts.createTable();
		MainNode mnode = new MainNode();
		mnode.setFilehash("askaldjasldaaaaksdj");
		mnode.setMainIp("12.131.22.11");
		
		mts.InsertNode(mnode);
	}
	
	@Test
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
}
