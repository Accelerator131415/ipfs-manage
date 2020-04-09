package application.MODEL.NET;

public class UnlineMessage {
	
	private String unlineip;
	private String aimip;
	
	public UnlineMessage() {}
	
	public UnlineMessage(String _unlineip,String _aimip) 
	{
		unlineip = _unlineip;
		aimip = _aimip;
	}
	
	
	public String getUnlineip() {
		return unlineip;
	}
	public void setUnlineip(String unlineip) {
		this.unlineip = unlineip;
	}
	public String getAimip() {
		return aimip;
	}
	public void setAimip(String aimip) {
		this.aimip = aimip;
	}
	
	
}
