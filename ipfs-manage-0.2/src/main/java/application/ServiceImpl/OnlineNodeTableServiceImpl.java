package application.ServiceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import application.MODEL.TABLE.OnlineNodeTable;
import application.Service.OnlineNodeTableService;
import application.Service.initService;


@Service("OnlineNodeTableService")
public class OnlineNodeTableServiceImpl implements OnlineNodeTableService {

	private String addr = initService.defaultAddr+"节点维护表\\";
	private final String TABLE = "OnlineNode.table";
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	
	public OnlineNodeTableServiceImpl() 
	{
		File path = new File(addr);
		if(!path.exists()) 
		{
			path.mkdir();
		}
	}
	
	public boolean createTable() 
	{
		File table = new File(addr+TABLE);
		if(!table.exists()) 
		{
			try
			{
				table.createNewFile();
			//	log.info("创建在线节点表成功");
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
		//		log.info("创建在线节点表失败，原因：文件操作异常");
				e.printStackTrace();
				
			}
		}
		return false;
	}

	public boolean InsertIp(String ip) 
	{
		
		String newip = ip + System.getProperty("line.separator");
		try 
		{
			File table = new File(addr+TABLE);
			if(!table.exists()) 
			{
		//		log.info("插入新节点：\""+ip+"\"失败，原因：目标表不存在");
				return false;
			}
			if(isExistIp(ip)) 
			{
				return true;
			}
			FileWriter fw = new FileWriter(new File(addr+TABLE),true);
			fw.write(newip);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
		//	log.info("插入新节点:\""+ip+"\"失败，原因:文件操作异常");
			e.printStackTrace();
		}
		return false;
		
		
		
	}
	
	public boolean isExistIp(String ip) 
	{
		BufferedReader br = null;
		String line;
		
		try 
		{
			
			br = new BufferedReader(new FileReader(addr+TABLE));
			while((line = br.readLine())!=null) 
			{
				if(line.equalsIgnoreCase(ip)) 
				{
			//		log.info("节点:\""+ip+"\"存在在线节点表中");
					return true;
				}
			}
			
		}catch(Exception e) 
		{
		//	log.info("检查节点:"+ip+"失败，原因：文件操作异常");
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
	//	log.info("节点:\""+ip+"\"不在在线节点表中");
		return false;
	}
	
	public boolean deleteIp(String ip) 
	{
		BufferedReader br = null;
		StringBuffer bufall = new StringBuffer();
		FileWriter fw = null;
		String line;
		try
		{
			br = new BufferedReader(new FileReader(addr+TABLE));
			
			while((line = br.readLine())!= null)
			{
				if(line.equalsIgnoreCase(ip))
				{
					continue;
				}
				bufall.append(line);
				bufall.append(System.getProperty("line.separator"));
			}
			
			fw = new FileWriter(new File(addr+TABLE),false);
			fw.write(bufall.toString());
						
		//	log.info("删除节点:\""+ip+"\"成功");
			return true;
		}
		catch(Exception e) 
		{
		//	log.info("删除节点:\""+ip+"\"失败，原因：文件操作异常");
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				fw.flush();
				fw.close();
				br.close();
			}catch(Exception e) 
			{
				e.printStackTrace();
			}
			
		}
		
		return false;
	}
	
	public OnlineNodeTable getTable() 
	{
		BufferedReader br = null;
		String line;
		OnlineNodeTable table = new OnlineNodeTable();
		List<String> nodes = new ArrayList<String>();
		try 
		{
			br = new BufferedReader(new FileReader(addr+TABLE));
			while((line = br.readLine())!=null) 
			{
				nodes.add(line);
			}
			
			table.setOnlineNodes(nodes);
			table.setNum(nodes.size());
		//	log.info("获取在线节点表成功");
			return table;
		}catch(Exception e) 
		{
		//	log.info("获取在线节点表失败，原因：文件操作异常");
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
		
		//log.info("获取在线节点表失败，原因：未知");
		return table;
		
		
	}
	
	public String getTABLE() 
	{
		return TABLE;
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
