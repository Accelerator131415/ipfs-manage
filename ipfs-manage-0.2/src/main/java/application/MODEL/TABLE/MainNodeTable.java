package application.MODEL.TABLE;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import application.MODEL.NODE.MainNode;

public class MainNodeTable {

	
	private List<MainNode> table;
	private int num;
	private Logger log = Logger.getLogger("ipfs-manage-MODEL");
	
	public List<MainNode> getTable() {
		return table;
	}
	public void setTable(List<MainNode> table) {
		this.table = table;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}

	//根据文件哈希获取主节点信息,若没有，则返回null
	public MainNode getNodebyhash(String hash) 
	{
		for(int i=0;i<table.size();i++) 
		{
			if(table.get(i).getFilehash().equalsIgnoreCase(hash)) 
			{
				log.info("已在主节点表中找到节点:\""+hash+"\";");
				return table.get(i);
			}
		}
		
		
		log.info("主节点表中不存在节点：\""+hash+"\"s");
		return null;
	}
	
	//根据IP地址获取主节点信息，返回的是一个链表，一个IP可以当做多个文件的主节点
	public List<MainNode> getNodebyIp(String ip) 
	{
		List<MainNode> list = new ArrayList<MainNode>();
		
		for(int i=0;i<table.size();i++) 
		{
			if(table.get(i).getMainIp().equalsIgnoreCase(ip)) 
			{
				log.info("已在主节点表中获取一个ip:\""+ip+"\"的节点信息");
				list.add(table.get(i));
			}
		}
		
		log.info("已获取所有IP为\""+ip+"\"的节点信息");
		return list;
	}
	
}
