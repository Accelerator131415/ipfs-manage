package application.Service;

import application.MODEL.TABLE.OnlineNodeTable;


//当前在线的节点表
public interface OnlineNodeTableService {

	
	public boolean createTable();

	public boolean InsertIp(String ip);
	
	public boolean deleteIp(String ip);
	
	public boolean isExistIp(String ip);
	
	public OnlineNodeTable getTable();
	
	public String getTABLE();

	public void setAddr(String addr);
	
	public String getAddr();
}
