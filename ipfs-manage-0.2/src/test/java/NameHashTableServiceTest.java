import org.junit.*;

import application.MODEL.NODE.NameHashNode;
import application.MODEL.TABLE.NamehashTable;
import application.Service.NameHashTableService;
import application.ServiceImpl.NameHashTableServiceImpl;


public class NameHashTableServiceTest {

	
	@Test
	public void test1() 
	{
		NameHashTableService ntable = new NameHashTableServiceImpl();
		NameHashNode nnode = new NameHashNode();
		nnode.setFilename("little fox.pptx");
		nnode.setHash("QmUCvpaDziLzgADYiBFeQg5NCH2sC1DHxf346Rj56PYrM8");
		
		ntable.createTable();
		ntable.InsertNode(nnode);
		
		
	}
	
	
	public void test2() 
	{
		NameHashTableService ntable = new NameHashTableServiceImpl();
		NameHashNode nnode = new NameHashNode();
		nnode.setFilename("这还是不是一个文件");
		nnode.setHash("zkkkkskskskskskskskssksksksksksksksk");
	
		ntable.InsertNode(nnode);
	}
	
	public void test3() 
	{
		NameHashTableService ntable = new NameHashTableServiceImpl();
		NamehashTable table = ntable.getTable();
		//NameHashNode nnode = ntable.getNodeByName("这他妈不是一个文件");
		//System.out.println(nnode.getFilename()+" "+nnode.getHash());
		for(int i=0;i<table.getNodes();i++)
		{
			System.out.println("name:"+table.getTable().get(i).getFilename() +" hash:"+table.getTable().get(i).getHash());
		
		}
	
	}
}
