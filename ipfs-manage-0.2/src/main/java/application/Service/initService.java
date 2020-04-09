package application.Service;

import java.io.IOException;

import application.MODEL.NODE.initNode;

public interface initService {

	public static String defaultAddr = "C:\\Users\\Administrator\\.selfDispathch\\";
	
	public boolean isInit();
	
	public void initProgram(String ipfsIp,String blockChainIp,String blockChainFilepath,String password) throws IOException;
	
	public initNode readInfo() throws IOException;
	
}
