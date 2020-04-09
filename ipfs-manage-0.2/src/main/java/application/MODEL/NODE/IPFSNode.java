package application.MODEL.NODE;

public class IPFSNode {
	private String ip;
	private boolean online;
	
	public IPFSNode() {}
	
	public IPFSNode(String _ip,boolean _online) 
	{
		ip = _ip;
		online = _online;
	}
	
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	
	
	
}
