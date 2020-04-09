package application.Service;

import application.MODEL.NODE.NameHashNode;
import application.MODEL.TABLE.NamehashTable;


//这个表改作存储文件的格式，毕竟文件名可能重复，拿来没啥用，存储格式，用来还原文件；
public interface NameHashTableService {
	
	//创建一个空的表
	public boolean createTable();
	
	//插入一个节点，如果节点已存在表中，则可以修改表中的节点信息
	public boolean InsertNode(NameHashNode nnode);
	
	//修改一个节点信息，但是如果节点不在表中，则会报错
	public boolean updateNode(NameHashNode nnode);
	
	//检查一个节点是否存在于表中
	public boolean isExistNode(NameHashNode nnode);
	
	//通过文件名获得这个节点的信息，但如果节点不存在与表中，则返回的哈希值为""
	//public NameHashNode getNodeByName(String name);
	
	public NameHashNode getNodeByHash(String hash);
	
	//获取表，前提是表已经下载了，若没有下载，则会报错
	public NamehashTable getTable();
	
	//获取表的存储地址
	public String getAddr();
	
	//修改表的存储地址
	public void setAddr(String addr);
	
	public String getTABLE();
}
