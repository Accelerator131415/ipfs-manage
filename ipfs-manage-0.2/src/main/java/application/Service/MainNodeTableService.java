package application.Service;

import java.util.List;

import application.MODEL.NODE.*;
import application.MODEL.TABLE.MainNodeTable;


//主节点表
public interface MainNodeTableService {

	
	//创建一个空表
	public boolean createTable();
	
	//插入一个主节点信息，如果主节点存在，则更新主节点信息
	public boolean InsertNode(MainNode mnode);
	
	//更新一个主节点信息，如果主节点不存在，则会报错
	public boolean updateNode(MainNode mnode);
	
	//检测目标节点信息是否存在
	public boolean isExistNode(MainNode mnode);
	
	//根据文件hash获取其主节点信息
	public MainNode getNodebyhash(String filehash);
	
	//获取一个节点有关的主节点的信息--一个节点可以是多个文件的主节点
	public List<MainNode> getNodebyIp(String ip);
	
	//获取主节点表
	public MainNodeTable getTable();
	
	//获取表的存储地址
	public String getAddr();
	
	//修改表的存储地址
	public void setAddr(String addr);
	
	public String getTABLE();
}
