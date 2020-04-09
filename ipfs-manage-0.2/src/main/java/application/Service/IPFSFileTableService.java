package application.Service;

import java.util.List;

import application.MODEL.NODE.IPFSNode;
import application.MODEL.TABLE.IPFSFileTable;


//备份文件的在线节点情况表
public interface IPFSFileTableService {

	
	//创建一个新文件的在线Node表,输入的是文件地址和文件名
	public boolean createTable(String filename);
	
	//向表中插入一个新的备份节点inode
	public boolean InsertNode(IPFSNode inode,String filename);
	
	//更新表中某个节点的在线信息
	public boolean updateNode(IPFSNode inode,String filename);
	
	//表中是否已经存在了某个节点的，避免重复插入导致表的失效
	public boolean isExistNode(IPFSNode inode,String filename);
	
	//获取目标表的IPFS的节点的信息
	public List<IPFSNode> getNodeInfo(String filename);
	
	//获取表中已有的节点数量
	public int getNodesNum(String filename);
	
	//获取表中的在线节点数量
	public int getNodesOnlineNum(String filename);
	
	//获取一个备份文件的节点情况表
	public IPFSFileTable getIPFSFileTablebyhash(String filename);

	public void setAddr(String addr);
	
	public String getAddr();
	
	public String getTABLE(String filehash);
}
