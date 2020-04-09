import org.junit.*;

import Service.*;
import ServiceImpl.IPFSFileTableServiceImpl;
import MODEL.*;
import MODEL.NODE.IPFSNode;

public class IPFSFileTableServiceTest {

	String addr = "D:\\我的大学\\毕业设计\\节点维护表";
	String filename = "this is file";
	
	
	public void test1() 
	{
		IPFSFileTableService ifts = new IPFSFileTableServiceImpl();
		IPFSNode inode = new IPFSNode();
		ifts.createTable(filename);
		inode.setIp("12.13.49.121");
		inode.setOnline(false);
		ifts.InsertNode(inode,filename);
		inode.setIp("11.12.33.44");
		inode.setOnline(true);
		ifts.InsertNode(inode, filename);
		ifts.InsertNode(inode,filename);
		ifts.getNodesNum (filename);
	
	}
	@Test
	public void test2() 
	{
		IPFSFileTableService ifts = new IPFSFileTableServiceImpl();
		ifts.getNodeInfo(filename);
		
	}
}
