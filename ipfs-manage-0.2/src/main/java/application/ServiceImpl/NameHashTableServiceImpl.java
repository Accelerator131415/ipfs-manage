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

import application.MODEL.NODE.NameHashNode;
import application.MODEL.TABLE.NamehashTable;
import application.Service.NameHashTableService;
import application.Service.initService;



@Service("NameHashTableService")
public class NameHashTableServiceImpl implements NameHashTableService{

	private final String TABLE = "NameHashTable.table";
	private String addr = initService.defaultAddr+"节点维护表\\";
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	
	public NameHashTableServiceImpl() 
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
	//			log.info("成功创建表:"+TABLE);
			}
			return true;
		}catch(Exception e) 
		{
			e.printStackTrace();
	//		log.info("创建文件名-哈希表:"+TABLE+"失败");
		}
		
		return false;
	}
	
	public boolean InsertNode(NameHashNode nnode) 
	{
		File table = new File(addr+TABLE);
		String content;
		try 
		{
			
			if(!table.exists())
			{
		//		log.info("插入节点:"+nnode.getFilename()+"失败，文件名-哈希表不存在");
				return false;
			}
			
			if(isExistNode(nnode)) 
			{
				return updateNode(nnode);
			}
			content = nnode.getHash()+","+nnode.getFilename()+";"+System.getProperty("line.separator");
			FileWriter fw = new FileWriter(table,true);
			fw.write(content);
			fw.flush();
			fw.close();
	//		log.info("文件名-哈希表插入节点:"+nnode.getHash()+"成功");
			
			return true;
		}catch(IOException e)
		{
			
			e.printStackTrace();
	//		log.info("文件名-哈希表插入节点:"+nnode.getHash()+"失败");
		}

		return false;
	}
	
	public boolean updateNode(NameHashNode nnode) 
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
				
			//	log.info("文件名-哈希表更新节点:"+nnode.getHash()+"失败，原因：目标表不存在");
				return false;
			}
			
			if(!isExistNode(nnode)) 
			{
		//		log.info("文件名-哈希表更新节点:"+nnode.getHash()+"失败，原因：表中目标节点不存在");
				return false;
			}
			
			while((line = br.readLine())!=null) 
			{
				StringBuffer buf = new StringBuffer();
				buf.append(line);
				if(line.startsWith(nnode.getHash())) 
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
					buf.replace(start+1, end, nnode.getFilename());				
				}
				
				buf.append(System.getProperty("line.separator"));
				bufall.append(buf);
			}
			FileWriter fw = new FileWriter(new File(addr+TABLE),false);
			fw.write(bufall.toString());
			fw.flush();
			fw.close();
		//	log.info("文件名-哈希表更新节点:"+nnode.getHash()+"成功");
			return true;
		}catch(IOException e) 
		{
			e.printStackTrace();
		//	log.info("文件名-哈希表更新节点:"+nnode.getHash()+"失败");
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
	
	public boolean isExistNode(NameHashNode nnode) 
	{
		BufferedReader br = null;
		String line;
		try 
		{
			br = new BufferedReader(new FileReader(addr+TABLE));
			
			while((line = br.readLine())!=null) 
			{
				if(line.startsWith(nnode.getHash())) 
				{
			//		log.info("文件名-哈希表中节点:"+nnode.getHash()+"存在");
					return true;
				}
				
			}
			
			
		}catch(IOException e) 
		{
			e.printStackTrace();
	//		log.info("读取文件名-哈希表失败");
		}finally 
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	//	log.info("文件名-哈希表中节点:"+nnode.getHash()+"不存在");
		return false;
	}
	
	public NameHashNode getNodeByName(String name) 
	{
		BufferedReader br = null;
		NameHashNode nnode = new NameHashNode();
		String line;
		nnode.setFilename(name);
		nnode.setHash("");
		int start = 0,end = 0;
		try 
		{
			br = new BufferedReader(new FileReader(addr+TABLE));
			while((line = br.readLine())!=null) 
			{
				if(line.startsWith(name)) 
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
					
					nnode.setHash(line.substring(start+1,end));
			//		log.info("文件名-哈希表获取节点:"+name+"信息成功");
					return nnode;
				}
			}
			
			
		}catch(IOException e) 
		{
			e.printStackTrace();
		//	log.info("文件名-哈希表获取节点："+name+"的信息失败");
		}finally 
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return nnode;
	}
	
	
	public NameHashNode getNodeByHash(String hash) 
	{
		BufferedReader br = null;
		NameHashNode nnode = new NameHashNode();
		String line;
		nnode.setFilename("");
		nnode.setHash(hash);
		int start = 0,end = 0;
		try 
		{
			br = new BufferedReader(new FileReader(addr+TABLE));
			while((line = br.readLine())!=null) 
			{
	
				if(line.startsWith(hash)) 
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
					
					nnode.setFilename(line.substring(start+1,end));
				//	log.info("文件名-哈希表获取节点:"+hash+"信息成功");
					return nnode;
				}
			}
			
			
			
		}catch(IOException e) 
		{
			e.printStackTrace();
		//	log.info("文件名-哈希表获取节点："+hash+"的信息失败");
		}finally 
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return nnode;
		
	}
	
	public NamehashTable getTable() 
	{
		BufferedReader br = null;
		String line;
		NamehashTable ntable = new NamehashTable();
		List<NameHashNode> list = new ArrayList<NameHashNode>();
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
				
				NameHashNode nnode = new NameHashNode();
				nnode.setHash(line.substring(0, middle));
				nnode.setFilename(line.substring(middle+1, end));
				list.add(nnode);
				num++;
			}
			
			ntable.setTable(list);
			ntable.setNodes(num);
			//log.info("获取文件名-哈希表成功");
			return ntable;
			
		}catch(IOException e) 
		{
			e.printStackTrace();
			//log.info("获取文件名-哈希表失败，失败原因：读取目标文件失败");
		}finally 
		{
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return ntable;
		
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
