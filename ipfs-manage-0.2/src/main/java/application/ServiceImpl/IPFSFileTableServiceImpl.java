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

import application.MODEL.NODE.IPFSNode;
import application.MODEL.TABLE.IPFSFileTable;
import application.Service.IPFSFileTableService;
import application.Service.initService;


@Service("IPFSFileTableService")
public class IPFSFileTableServiceImpl implements IPFSFileTableService {
	
	
	private String addr = initService.defaultAddr+"节点维护表\\";	
	private double yu_no = 0.5;
	private double yu_on = 0.3;
 	private String TABLE;
	
	
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	private int limit = 10;
	
	public IPFSFileTableServiceImpl()
	{
		File path = new File(addr);
		if(!path.exists()) 
		{
			path.mkdir();
		}
	}
	
	public boolean createTable(String filename) 
	{
		try 
		{
			File table = new File(addr + filename+".table");
			if(!table.exists()) 
			{
				table.createNewFile();
			}
			
			//log.info("建立新表："+filename);
			return true;
		}catch(IOException e) 
		{
			e.printStackTrace();
			//log.info("建表\""+filename+"\"失败");
		}
		
		
		return false;
		
	}
	
	public boolean InsertNode(IPFSNode inode,String filename)
	{
		try 
		{
			
			File table = new File(addr + filename+".table");
			String content = inode.getIp()+","+inode.isOnline()+";"+System.getProperty("line.separator");
			if(!table.exists()) 
			{
				//System.out.println("have no the table");
				
				//table.createNewFile();
				//log.info("插入新节点失败，原因：目标表"+filename+"不存在");
				return false;
			}
			if(isExistNode(inode,filename)) 
			{
				//log.info("插入节点("+inode.getIp()+")已存在与表："+filename+"中");
				return updateNode(inode,filename);

			}
			FileWriter fw = new FileWriter(table,true);
			fw.write(content);
			//log.info("已于"+filename+"表中插入新节点\""+inode.getIp()+"\"");
			fw.flush();
			fw.close();
			return true;
			
		}catch(IOException e) 
		{
			e.printStackTrace();
			//log.info("向"+filename+"表中插入新节点\""+inode.getIp()+"\"失败");
		}
		
		
		return false;
	
	}

	public boolean updateNode(IPFSNode inode,String filename)
	{
		BufferedReader br = null;
		String line;
		StringBuffer bufall = new StringBuffer();
		int start = 0 ,end = 0;
		try 
		{
			br = new BufferedReader(new FileReader(addr + filename+".table"));
			while((line = br.readLine())!=null) 
			{
				StringBuffer buf = new StringBuffer();
				buf.append(line);
				if(line.startsWith(inode.getIp())) 
				{
					//buf.append(line);
					//boolean first = true;
					for(int i=0;i<line.length();i++) 
					{
						if(line.substring(i, i+1).trim().equalsIgnoreCase(",") ) 
						{
							start = i;
							
						}
						else if(line.substring(i, i+1).trim().equalsIgnoreCase(";"))
						{
							end = i;
						}
					}
					buf.replace(start+1, end, ""+inode.isOnline());
					
				}
				buf.append(System.getProperty("line.separator"));
				bufall.append(buf);
			}
			FileWriter fw = new FileWriter(new File(filename+".table"),false);
			fw.write(bufall.toString());
			//log.info("更新表:"+filename+"中,节点"+inode.getIp()+"信息成功");
			fw.flush();
			fw.close();
			br.close();
			return true;
		}catch(IOException e) 
		{
			e.printStackTrace();
			//log.info("更新表:"+filename+"中,节点"+inode.getIp()+"信息失败");
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

	public boolean isExistNode(IPFSNode inode,String filename)
	{
		BufferedReader br = null;
		String line;
		try 
		{
			br = new BufferedReader(new FileReader(addr + filename+".table"));
		
			while((line = br.readLine())!=null)
			{
				if(line.startsWith(inode.getIp())) 
				{
					
					//log.info("节点"+inode.getIp()+"已存在于表"+filename+"中");
					return true;
				}
			}
			
			
		}catch(IOException e) 
		{
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
		//log.info("节点"+inode.getIp()+"不存在与表"+filename+"中");
		return false;
	}

	public List<IPFSNode> getNodeInfo(String filename)
	{
		List<IPFSNode> list = new ArrayList<IPFSNode>();
		BufferedReader br = null;
		String line;
		int middle = 0,end =0;
		IPFSNode inode;
		//log.info("开始获取文件"+filename+"的节点信息");
		try 
		{
			br = new BufferedReader(new FileReader(addr + filename+".table"));
			while((line = br.readLine())!=null) 
			{
				for(int i=0;i<line.length();i++) 
				{
					if(line.substring(i, i+1).equalsIgnoreCase(",")) 
					{
						middle = i;
					}
					else if(line.substring(i, i+1).equalsIgnoreCase(";")) 
					{
						end = i;
					}
				}
				inode = new IPFSNode();
				inode.setIp(line.substring(0, middle));
				if(line.substring(middle+1, end).equalsIgnoreCase("true")) 
				{
					inode.setOnline(true);
				}
				else 
				{
					inode.setOnline(false);
				}
				list.add(inode);
				//log.info("已获取节点：\""+inode.getIp()+"\"的信息");
			}
			
		}catch(IOException e) 
		{
			e.printStackTrace();
			//log.info("获取表："+filename+"中的节点信息失败");
		}
		
		
		
		
		return list;
	}
	
	public int getNodesNum(String filename)
	{
		BufferedReader br;
		int num = 0;
		try 
		{
			br = new BufferedReader(new FileReader(addr + filename+".table"));
			while(br.readLine()!=null) 
			{
				num++;
			}	
			br.close();
		}catch(IOException e) 
		{
			e.printStackTrace();
		}
			
		//log.info("获取表"+filename+"中的节点的总数为:"+num);
		return num;
	}
	
	public int getNodesOnlineNum(String filename)
	{
		BufferedReader br = null;
		String line;
		int start = 0 ,end = 0,nums = 0;
		
		try 
		{
			br = new BufferedReader(new FileReader(addr + filename+".table"));
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
				
				if(line.substring(start+1, end).equalsIgnoreCase("true")) 
				{
					nums++;
				}
			}
			
			
		}catch(IOException e) 
		{
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
		
		//log.info("获取表"+filename+"中的在线可用的节点总数为:"+nums);
		return nums;
	}

	public IPFSFileTable getIPFSFileTablebyhash(String filename) 
	{
		IPFSFileTable itable = new IPFSFileTable();
		
		itable.setNodes(getNodeInfo(filename));
		itable.setLimit(limit);
		itable.setNodes_had(getNodesNum(filename));
		itable.setYu_main(yu_on);
		itable.setYu_no_main(yu_no);
		itable.setFilehash(filename);
		return itable;
		
	}

	public void setAddr(String addr) 
	{
		this.addr = addr;
	}
	
	public String getAddr() 
	{
		return addr;
	}

	public String getTABLE(String filehash) {
		return filehash+".table";
	}


}
