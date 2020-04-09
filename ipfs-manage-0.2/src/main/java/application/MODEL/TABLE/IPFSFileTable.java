package application.MODEL.TABLE;


import java.util.List;
import java.util.logging.Logger;

import application.MODEL.NODE.IPFSNode;


//filehash - ip/是否在线
public class IPFSFileTable {
	private int limit;//这个表最大的备份节点数
	private int nodes_had;//这个表中实际记录的备份节点数目
	private List<IPFSNode> nodes;//备份节点的信息
	private double yu_no_main;//重新调度所占的比例，主节点不在线时：在线节点数:实际备份节点=yu_no_main
	private double yu_main;//主节点在线时的所占比，应该比yu_no_main小
	private String filehash;//这个表对应的文件的哈希值

	private Logger log = Logger.getLogger("ipfs-manage-MODEL");
	
	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getNodes_had() {
		return nodes_had;
	}

	public void setNodes_had(int nodes_had) {
		this.nodes_had = nodes_had;
	}

	public List<IPFSNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<IPFSNode> nodes) {
		this.nodes = nodes;
	}

	public double getYu_no_main() {
		return yu_no_main;
	}

	public void setYu_no_main(double yu_no_main) {
		this.yu_no_main = yu_no_main;
	}

	public double getYu_main() {
		return yu_main;
	}

	public void setYu_main(double yu_main) {
		this.yu_main = yu_main;
	}

	public String getFilehash() {
		return filehash;
	}

	public void setFilehash(String filehash) {
		this.filehash = filehash;
	}

	//根据ip获取节点信息，若节点不存在，则返回null
	public IPFSNode getNodebyIp(String ip) 
	{
		for(int i=0;i<nodes.size();i++) 
		{
			if(nodes.get(i).getIp().equalsIgnoreCase(ip)) 
			{
				log.info(filehash+"表成功获取节点\""+ip+"\"的在线信息");
				return nodes.get(i);				
			}
		}
		log.info(filehash+"表没有节点\""+ip+"\"的信息");
		return null;
	}
	
	//获取表中在线的节点总数
	public int getOnlinenum() 
	{
		int num = 0;
		for(int i=0;i<nodes.size();i++) 
		{
			if(nodes.get(i).isOnline()) 
			{
				num++;
			}	
		}
		return num;
	}
	

	
}
