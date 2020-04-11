package application.Controller;

import java.io.IOException;

import org.web3j.crypto.CipherException;

import application.MODEL.NODE.initNode;
import application.MODEL.TABLE.IPFSFileTable;
import application.MODEL.TABLE.NamehashTable;

public interface CenterController {

	
	public void start() throws IOException, CipherException, Exception;
	
	public void uploadFile(String addr,String file) throws Exception;
	
	public void downloadFile(String hash) throws Exception;
		
	public void exit() throws Exception;


	public boolean isService();
	
	public NamehashTable getFileHashInfo() throws Exception;
	
	public IPFSFileTable getFileonlinebackupInfo(String filehash) throws Exception;

	public initNode readInitInfo() throws IOException;
	
	public void initProgram(String ipfsIp,String blockChainIp,String filepath,String password) throws IOException;
	
}
