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

import application.MODEL.NODE.MainNode;
import application.MODEL.TABLE.MainNodeTable;
import application.Service.MainNodeTableService;
import application.Service.initService;

@Service("MainNodeTableService")
public class MainNodeTableServiceImpl implements MainNodeTableService {

	private String addr = initService.defaultAddr+"节点维护表\\";
	private final String TABLE = "MainNodeTable.table";
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	
	public MainNodeTableServiceImpl() 
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
		try 
		{
			if(!table.exists()) 
			{
				table.createNewFile();
				log.info("成功创建表:"+TABLE);
			}
			return true;
		}catch(Exception e) 
		{
			e.printStackTrace();
			log.info("创建主节点表:"+TABLE+"失败");
		}
		
		return false;
	}
	
	public boolean InsertNode(MainNode mnode) 
	{
		File table = new File(addr+TABLE);
		String content;
		try 
		{
			if(!table.exists())
			{
				log.info("插入节点:"+mnode.getFilehash()+"失败，主节点表不存在");
				return false;
			}
			
			if(isExistNode(mnode)) 
			{
				return updateNode(mnode);
			}
			content = mnode.getFilehash()+","+mnode.getMainIp()+";"+System.getProperty("line.separator");
			FileWriter fw = new FileWriter(table,true);
			fw.write(content);
			fw.flush();
			fw.close();
			log.info("主节点表插入节点:"+mnode.getFilehash()+"成功");
			
			return true;
		}catch(IOException e)
		{
			
			e.printStackTrace();
			log.info("主节点表插入节点:"+mnode.getFilehash()+"失败");
		}

		return false;
	}
	
	public boolean updateNode(MainNode mnode) 
	{
		BufferedReader br = null;
		String line;
		StringBuffer bufall = new StringBuffer();
		int start=0 , end = 0;
		try 
		{
			br = new BufferedReader(new FileReader(addr+TABLE));
			if(!new File(addr+TABLE).exists()) 
			{
				
				log.info("主节点表更新节点:"+mnode.getFilehash()+"失败，原因：目标表不存在");
				return false;
			}
			
			if(!isExistNode(mnode)) 
			{
				log.info("主节点表更新节点:"+mnode.getFilehash()+"失败，原因：表中目标节点不存在");
				return false;
			}
			
			while((line = br.readLine())!=null) 
			{
				StringBuffer buf = new StringBuffer();
				buf.append(line);
				if(line.startsWith(mnode.getFilehash())) 
				{
					for(int i=0;i<line.length();i++) 
					{
						if(line.substring(i, i+1).equalsIgnoreCase(",")) 
						{
							start = i;
						}
						else if(line.substring(i, i+1).equalsIgnoreCase(";")) 
						{
							end = i;
						}
					}
					buf.replace(start+1, end-1, mnode.getFilehash());				
				}
				
				buf.append(System.getProperty("line.separator"));
				bufall.append(buf);
			}
			FileWriter fw = new FileWriter(new File(addr+TABLE),false);
			fw.write(bufall.toString());
			fw.flush();
			fw.close();
			log.info("主节点表更新节点:"+mnode.getFilehash()+"成功");
			return true;
		}catch(IOException e) 
		{
			e.printStackTrace();
			log.info("主节点表更新节点:"+mnode.getFilehash()+"失败");
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
	
	public boolean isExistNode(MainNode mnode) 
	{
		BufferedReader br = null;
		String line;
		try 
		{
			br = new BufferedReader(new FileReader(addr+TABLE));
			
			while((line = br.readLine())!=null) 
			{
				if(line.startsWith(mnode.getFilehash())) 
				{
					log.info("主节点表中节点:"+mnode.getFilehash()+"存在");
					return true;
				}
				
			}
			
			
		}catch(IOException e) 
		{
			e.printStackTrace();
			log.info("读取主节点表失败");
		}finally 
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		log.info("主节点表中节点:"+mnode.getFilehash()+"不存在");
		return false;
	}
	
	public MainNode getNodebyhash(String filehash) 
	{
		BufferedReader br = null;
		MainNode mnode = new MainNode();
		String line;
		mnode.setFilehash(filehash);
		mnode.setMainIp("");
		int start = 0,end = 0;
		try 
		{
			br = new BufferedReader(new FileReader(addr+TABLE));
			while((line = br.readLine())!=null) 
			{
				if(line.startsWith(filehash)) 
				{
					for(int i=0;i<line.length();i++) 
					{
						if(line.substring(i, i+1).equalsIgnoreCase(",")) 
						{
							start = i;
						}
						else if(line.substring(i, i+1).equalsIgnoreCase(";")) 
						{
							end = i;
						}
					}
					
					mnode.setMainIp((line.substring(start+1,end-1)));
					log.info("主节点表获取节点:"+filehash+"信息成功");
					return mnode;
				}
			}
			
			
		}catch(IOException e) 
		{
			e.printStackTrace();
			log.info("主节点获取节点："+filehash+"的信息失败");
		}finally 
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return mnode;
	}
	
	public MainNodeTable getTable() 
	{
		BufferedReader br = null;
		String line;
		MainNodeTable mtable = new MainNodeTable();
		List<MainNode> list = new ArrayList<MainNode>();
		int middle = 0 ,end = 0,num = 0;
		
		try 
		{
			br = new BufferedReader(new FileReader(addr+TABLE));
			
			while((line = br.readLine())!=null) 
			{
				for(int i=0;i<line.length();i++) 
				{
					if(line.substring(i, i+1).equalsIgnoreCase(",")) 
					{
						middle = i;
					}
					else if(line.substring(i,i+1).equalsIgnoreCase(";")) 
					{
						end = i;
					}
					
				}	
				
				MainNode mnode = new MainNode();
				mnode.setFilehash(line.substring(0, middle-1));
				mnode.setMainIp(line.substring(middle+1, end-1));
				list.add(mnode);
				num++;
			}
			
			mtable.setTable(list);
			mtable.setNum(num);
			log.info("获取主节点表成功");
			return mtable;
			
		}catch(IOException e) 
		{
			e.printStackTrace();
			log.info("获取主节点表失败，失败原因：读取目标文件失败");
		}finally 
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return mtable;
		
	}

	
	public List<MainNode> getNodebyIp(String ip)
	{
		BufferedReader br = null;
		MainNode mnode = new MainNode();
		List<MainNode> list = new ArrayList<MainNode>();
		String line;
		mnode.setFilehash("");
		mnode.setMainIp(ip);
		int start = 0,end = 0;
		try 
		{
			br = new BufferedReader(new FileReader(addr+TABLE));
			while((line = br.readLine())!=null) 
			{
			
				for(int i=0;i<line.length();i++) 
				{
					if(line.substring(i, i+1).equalsIgnoreCase(",")) 
					{
						start = i;
					}
					else if(line.substring(i, i+1).equalsIgnoreCase(";")) 
					{
						end = i;
					}
				}
				
				//mnode.setMainIp((line.substring(start+1,end)));
				mnode.setFilehash(line.substring(0,start-1));
				list.add(mnode);
				
				
			}
			
			log.info("主节点表获取节点:"+ip+"的主节点信息成功");
			return list;
			
			
		}catch(IOException e) 
		{
			e.printStackTrace();
			log.info("主节点获取节点："+ip+"的信息失败");
		}finally 
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTABLE() 
	{
		return TABLE;
	}
}
