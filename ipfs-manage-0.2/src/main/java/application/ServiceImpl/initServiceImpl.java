package application.ServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import application.MODEL.NODE.initNode;
import application.Service.initService;
import io.ipfs.api.NamedStreamable.FileWrapper;

@Service("initService")
public class initServiceImpl implements initService {

	private String beforeIpfsIp = "ipfs ip:";
	private String beforeBlockChainIp = "blockChain ip:";
	private String beforeFilepath = "Filepath:";
	private String beforePassword = "password:";
	private String initFile = "set.ini";
	
	
	public initServiceImpl() {
		// TODO Auto-generated constructor stub
		File path = new File(initService.defaultAddr);
		if(!path.exists()) 
		{
			path.mkdir();
		}
		
	}
	
	@Override
	public boolean isInit() {
		// TODO Auto-generated method stub
		File path = new File(initService.defaultAddr);
		if(!path.exists()) 
		{
			return false;
		}
		File set = new File(initService.defaultAddr+initFile);
		if(!set.exists()) 
		{
			return false;
		}
		return true;
	}

	@Override
	public void initProgram(String ipfsIp, String blockChainIp, String blockChainFilepath, String password) throws IOException {
		// TODO Auto-generated method stub
		File path = new File(defaultAddr);
		File set = new File(defaultAddr+initFile);
		if(!path.exists()) 
		{
			path.mkdir();
		}
		if(!set.exists()) 
		{
			set.createNewFile();
		}
		
		
		StringBuffer bufall = new StringBuffer();
		
		bufall.append(beforeBlockChainIp+blockChainIp);
		bufall.append(System.getProperty("line.separator"));
		bufall.append(beforeFilepath+blockChainFilepath);
		bufall.append(System.getProperty("line.separator"));
		bufall.append(beforePassword+password);
		bufall.append(System.getProperty("line.separator"));
		bufall.append(beforeIpfsIp+ipfsIp);
		bufall.append(System.getProperty("line.separator"));
		FileWriter fw = new FileWriter(set,false);
		fw.write(bufall.toString());
		fw.flush();
		fw.close();
	}

	@Override
	public initNode readInfo() throws IOException {
		// TODO Auto-generated method stub
		if(!isInit()) 
		{
			throw new IOException();
		}

		initNode info = new initNode();
		
		BufferedReader br = new BufferedReader(new FileReader(initService.defaultAddr+initFile));
		String line;
		while((line = br.readLine())!=null) 
		{
			if(line.startsWith(beforeBlockChainIp)) 
			{
				info.setBlockChainIp(line.substring(beforeBlockChainIp.length(),line.length()));
			}
			else if(line.startsWith(beforeFilepath)) 
			{
				info.setBlockChainFilepath(line.substring(beforeFilepath.length(),line.length()));
			}
			else if(line.startsWith(beforePassword)) 
			{
				info.setBlockChainPassword(line.substring(beforePassword.length(),line.length()));
			}
			else if(line.startsWith(beforeIpfsIp)) 
			{
				info.setIpfsIp(line.substring(beforeIpfsIp.length(),line.length()));
			}
		}
		br.close();
		return info;
	}

	
	
}
