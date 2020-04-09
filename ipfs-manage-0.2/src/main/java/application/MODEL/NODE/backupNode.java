package application.MODEL.NODE;

public class backupNode {

	
	private String filehash;
	private int num;
	public String getFilehash() {
		return filehash;
	}
	public void setFilehash(String filehash) {
		this.filehash = filehash;
	}
	
	public synchronized void backupOne() 
	{
		num++;
	}
	public synchronized void setNum(int num) 
	{
		this.num = num;
	}
	
	public int getNum() {
		return num;
	}
}
