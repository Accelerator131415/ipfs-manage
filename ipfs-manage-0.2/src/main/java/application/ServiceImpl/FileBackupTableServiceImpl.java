package application.ServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import application.MODEL.TABLE.FileBackupTable;
import application.Service.FileBackupTableService;
import application.Service.initService;



@Service("FileBackupTableService")
public class FileBackupTableServiceImpl implements FileBackupTableService {

	private String addr = initService.defaultAddr+"节点维护表\\";
	private String TABLE;
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	
	public FileBackupTableServiceImpl() 
	{
		File path = new File(addr);
		if(!path.exists()) 
		{
			path.mkdir();
		}
	}
	
	public boolean createTable() 
	{
		
		try 
		{
			InetAddress ip = InetAddress.getLocalHost();
			File table = new File(addr+getTABLE(ip.getHostAddress()));
			if(!table.exists()) 
			{
				table.createNewFile();
			}
			
			log.info("创建文件备份表成功");
			return true;
			
		}catch(IOException e) 
		{
			log.info("创建文件备份表失败：原因：文件操作异常");
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean InsertFile(String filehash) 
	{
		try 
		{
			String newhash = filehash +System.getProperty("line.separator");
			File table = new File(addr+TABLE);
			if(!table.exists()) 
			{
				log.info("插入新文件:"+filehash+"失败，原因：目标表不存在");
				return false;
			}
			if(isExist(filehash)) 
			{
				return true;
			}
			FileWriter fw = new FileWriter(new File(addr+TABLE),true);
			fw.write(newhash);
			fw.flush();
			fw.close();
			
			log.info("插入新文件："+filehash+"成功");
			return true;
		}catch(IOException e) 
		{
			log.info("插入新文件:"+filehash+"失败，原因：文件操作异常");
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean isExist(String filehash) 
	{
		
		BufferedReader br = null;
		String line;
		
		try 
		{
			br = new BufferedReader(new FileReader(addr + TABLE));
			
			while((line = br.readLine())!=null) 
			{
				if(line.equalsIgnoreCase(filehash)) 
				{
					log.info("文件:\""+filehash+"\"存在于表中");
					return true;
				}
			}
			
			
			
		}catch(Exception e) 
		{
			log.info("检查文件备份表失败，原因：文件操作异常");
			e.printStackTrace();
		}finally 
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return false;
		
		
	}
	
	public FileBackupTable getTable(String ip) 
	{
		BufferedReader br = null;
		String line;
		FileBackupTable fbt = new FileBackupTable();
		List<String> files = new ArrayList<String>();
		
		try 
		{
			br = new BufferedReader(new FileReader(addr + getTABLE(ip)));
			
			while((line = br.readLine())!=null) 
			{
				files.add(line);
			}
			
			fbt.setFiles(files);
			fbt.setNum(files.size());
			log.info("获取文件备份表成功");
			
			return fbt;
		}
		catch(Exception e) 
		{
			log.info("获取文件备份表失败，原因：文件操作异常");
			e.printStackTrace();
		}
		finally
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return fbt;
		
	}

	public String getTABLE(String ip) 
	{
		try
		{
			//InetAddress ip = InetAddress.getLocalHost();
			TABLE = ip+".table";
			return TABLE;
		}catch(Exception e) 
		{
			log.info("获取文件备份表的文件名失败");
			return TABLE;
		}
		
	}
	
	public void setAddr(String addr) 
	{
		this.addr = addr;
	}
	
	public String getAddr() 
	{
		return addr;
	}
}
