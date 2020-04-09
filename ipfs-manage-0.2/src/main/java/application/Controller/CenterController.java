package application.Controller;

import application.MODEL.TABLE.IPFSFileTable;
import application.MODEL.TABLE.NamehashTable;

public interface CenterController {

	
	public void start();
	
	public void uploadFile(String addr,String file);
	
	public void downloadFile(String hash);
		
	public void exit();


	public boolean isService();
	
	public NamehashTable getFileHashInfo();
	
	public IPFSFileTable getFileonlinebackupInfo(String filehash);
	
}
