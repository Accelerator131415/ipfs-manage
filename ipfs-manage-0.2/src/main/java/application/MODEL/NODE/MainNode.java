package application.MODEL.NODE;

public class MainNode {

	private String filehash;
	private String mainIp;
	
	public MainNode() {}
	
	public MainNode(String _filehash,String _mainIp) 
	{
		filehash = _filehash;
		mainIp = _mainIp;
		
	}
	
	public String getFilehash() {
		return filehash;
	}
	public void setFilehash(String filehash) {
		this.filehash = filehash;
	}
	public String getMainIp() {
		return mainIp;
	}
	public void setMainIp(String mainIp) {
		this.mainIp = mainIp;
	}
}
