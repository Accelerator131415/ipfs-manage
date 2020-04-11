import java.util.logging.Logger;

import org.junit.*;

import application.Service.*;
import application.ServiceImpl.IPFSFileTableServiceImpl;
import jdk.internal.org.jline.utils.Log;
import application.MODEL.*;
import application.MODEL.NODE.IPFSNode;

public class IPFSFileTableServiceTest {

	String addr = "D:\\我的大学\\毕业设计\\节点维护表";
	String filename = "this is file";
	private Logger log = Logger.getLogger("asdasd");
	
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
		//ifts.getNodeInfo(filename);
		for(int i=0;i<ifts.getIPFSFileTablebyhash("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT").getNodes().size();i++) 
		{
			log.info(ifts.getIPFSFileTablebyhash("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT").getNodes().get(i).getIp()+"-"+ifts.getIPFSFileTablebyhash("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT").getNodes().get(i).isOnline());
		}
	}
}
