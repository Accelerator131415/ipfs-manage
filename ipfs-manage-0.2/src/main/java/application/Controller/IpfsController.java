package application.Controller;

public interface IpfsController {

	
	
	//根据文件地址上传一个文件
	public String upload(String addr,String filename);
	
	//根据文件hash值下载文件
	public void download(String hash);
	
	
	public boolean backup(String hash);
	
	
	
	
}
