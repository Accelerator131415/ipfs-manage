package application.Controller;

import java.io.IOException;

import org.web3j.crypto.CipherException;

import application.MODEL.NODE.initNode;
import application.MODEL.TABLE.IPFSFileTable;
import application.MODEL.TABLE.NamehashTable;

public interface CenterController {

	
	public void start() throws IOException, CipherException;
	
	public void uploadFile(String addr,String file);
	
	public void downloadFile(String hash);
		
	public void exit();


	public boolean isService();
	
	public NamehashTable getFileHashInfo();
	
	public IPFSFileTable getFileonlinebackupInfo(String filehash);

	public initNode readInitInfo() throws IOException;
	
	public void initProgram(String ipfsIp,String blockChainIp,String filepath,String password) throws IOException;
	
}
