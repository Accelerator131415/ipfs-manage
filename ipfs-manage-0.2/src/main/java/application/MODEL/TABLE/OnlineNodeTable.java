package application.MODEL.TABLE;

import java.util.List;

public class OnlineNodeTable {

	private List<String> onlineNodes;
	private int num;
	
	public List<String> getOnlineNodes() {
		return onlineNodes;
	}
	public void setOnlineNodes(List<String> onlineNodes) {
		this.onlineNodes = onlineNodes;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	//查询某个ip是否在线
	public boolean isOnline(String ip) 
	{
		for(int i=0;i<onlineNodes.size();i++) 
		{
			if(onlineNodes.get(i).equalsIgnoreCase(ip)) 
			{
				return true;
			}
			
		}
		
		return false;
	}
	
}
