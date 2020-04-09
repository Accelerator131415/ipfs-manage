package application.MODEL.TABLE;

import java.util.List;
import java.util.logging.Logger;

import application.MODEL.NODE.NameHashNode;

public class NamehashTable {

	private List<NameHashNode> table;//存储的节点的信息
	private int nodes;//表中的文件总数
	private Logger log = Logger.getLogger("ipfs-manage-MODEL");
	
	public List<NameHashNode> getTable() {
		return table;
	}
	public void setTable(List<NameHashNode> table) {
		this.table = table;
	}
	public int getNodes() {
		return nodes;
	}
	public void setNodes(int nodes) {
		this.nodes = nodes;
	}
	//根据文件名获取一个节点的信息，若没有该节点则返回null
	public NameHashNode getNodebyname(String name) 
	{
		for(int i=0;i<table.size();i++) 
		{
			if(table.get(i).getFilename().equalsIgnoreCase(name)) 
			{
				log.info("文件名-哈希表已获得节点:\""+name+"\"的信息");
				return table.get(i);
			}
			
		}
		
		
		log.info("文件名-哈希表中不存在节点:\""+name+"\"的信息");
		return null;
	}

	//根据文件哈希获取一个节点的信息，若没有则返回null
	public NameHashNode getNodebyhash(String hash) 
	{
		for(int i=0;i<table.size();i++) 
		{
			if(table.get(i).getHash().equalsIgnoreCase(hash)) 
			{
				log.info("文件名-哈希表已获得节点哈希值为:\""+hash+"\"的信息");
				return table.get(i);
			}
			
		}
		log.info("文件名-哈希表中不存在节点哈希值为:\""+hash+"\"的信息");
		return null;
	}
	
}
