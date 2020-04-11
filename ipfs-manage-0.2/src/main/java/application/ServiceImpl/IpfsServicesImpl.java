package application.ServiceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.*;

import application.Service.IpfsServices;
import application.Service.initService;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

@Service("IpfsServices")
public class IpfsServicesImpl implements IpfsServices {

	private IPFS ipfs;// = new IPFS("/ip4/192.168.43.135/tcp/5001");
	private String downloadAddr = initService.defaultAddr+"实验文件夹\\";
	private String backupAddr = initService.defaultAddr+"备份文件夹\\";
	private String tableAddr = initService.defaultAddr+"节点维护表\\";
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	
	public IpfsServicesImpl() 
	{
		File path1 = new File(downloadAddr);
		File path2 = new File(backupAddr);
		File path3 = new File(tableAddr);
		if(!path1.exists()) 
		{
			path1.mkdir();
		}
		if(!path2.exists()) 
		{
			path2.mkdir();
		}
		if(!path3.exists()) 
		{
			path3.mkdir();
		}
		
	}
	
	public String UploadFile(String addr) {
		// TODO Auto-generated method stub
		NamedStreamable.FileWrapper savefile = new NamedStreamable.FileWrapper(new File(addr));
		try 
		{
			List<MerkleNode> res = ipfs.add(savefile);			
			//System.out.println(res.get(0).toJSONString());
			
//			log.info("向IPFS上传了文件:"+addr+"\n"
//					+"返回哈希值为:"+res.get(0).hash.toString());
			return res.get(0).hash.toString();
		}
		catch(IOException e) 
		{
			e.printStackTrace();
			//log.info("向IPFS上传文件:"+addr+"失败");
		}
				
		
		return null;
		
	}

	public void DownloadFile(String hash,String filename) {
		// TODO Auto-generated method stub

		try 
		{
			Multihash filePointer = Multihash.fromBase58(hash);
			//List<MerkleNode> mns = ipfs.ls(filePointer);
			byte[] file = ipfs.cat(filePointer);			
			File downloadfile = new File(downloadAddr+filename);
			
			if(!downloadfile.exists()) 
			{
				downloadfile.createNewFile();
			}
			if(hash.equalsIgnoreCase("")) 
			{
				//log.info("原文件不存在");				
				return;
			}
			
			FileOutputStream fop = new FileOutputStream(downloadfile);
			fop.write(file);
			fop.flush();
			fop.close();
			//log.info("从IPFS中下载了文件："+filename+"\n"
			//		+"文件哈希值为:"+hash);
		}catch(IOException e) 
		{
			e.printStackTrace();
			//log.info("从IPFS中下载文件："+filename+"失败");
		}
		
		
	}

	public void DownloadTable(String hash,String filename) 
	{
		try 
		{
			File downloadTable = new File(tableAddr + filename);
			if(!downloadTable.exists()) 
			{
				downloadTable.createNewFile();
			}
			if(hash.equalsIgnoreCase("")) 
			{
				//log.info("原表不存在，已创建新表");
				return;
			}
			
			Multihash filePointer = Multihash.fromBase58(hash);
			//log.info("看看hash:"+hash);
			//log.info("看看filePointer:"+filePointer.toString());
			//List<MerkleNode> mns = ipfs.ls(filePointer);
			byte[] file = ipfs.cat(filePointer);			
						
			FileOutputStream fop = new FileOutputStream(downloadTable,false);
			
			//FileWriter fw = new FileWriter(downloadTable,false);
			fop.write(file);;
			fop.flush();
			fop.close();
//			log.info("从IPFS中下载了表："+filename+"\n"
//					+"文件哈希值为:"+hash);
		}catch(IOException e) 
		{
			e.printStackTrace();
			//log.info("从IPFS中下载文件："+filename+"失败");
		}		
	}
	
	public void backup(String hash) {		
		/*
		for(int i=0;i<filehashList.size();i++) 
		{
			System.out.println(filehashList.get(i).toString());
		}*/
		
		try 
		{
			Multihash file = Multihash.fromBase58(hash);
			List<String> filehashList = new ArrayList<String>();
			findfile(file,filehashList);
			FileOutputStream fop;
			for(int i=0;i<filehashList.size();i++) 
			{
				byte[] filebyte = ipfs.cat(Multihash.fromBase58(filehashList.get(i)));
				File backupfile = new File(backupAddr + filehashList.get(i).toString());
				if(!backupfile.exists()) 
				{
					backupfile.createNewFile();
				}
				fop = new FileOutputStream(backupfile);
				fop.write(filebyte);
				fop.flush();
				fop.close();
				//System.out.println("over:"+filebyte);
			//	log.info("已备份哈希文件："+hash);
			}
		}catch(IOException e) 
		{
			e.printStackTrace();
			//log.info("备份哈希文件："+hash+"失败");
		}
			
		
		
		
		
			
	}
	
	public List<String> getSonfile(String hash)
	{
		Multihash file = Multihash.fromBase58(hash);
		List<String> filehashList = new ArrayList<String>();
		findfile(file,filehashList);
		//log.info("调用函数获取目标hash值："+hash+"的子哈希");
		return filehashList;
		
		
	}
	
	//寻找小文件的hash值，并将其添加到filehashlist中
	private void findfile(Multihash hash,List<String> filehashlist) 
	{
		//Multihash filePointer = Multihash.fromBase58(hash);
		try {
			List<MerkleNode> mns = ipfs.ls(hash);
			for(int i=0;i<mns.size();i++) 
			{
				if(mns.get(i).links.isEmpty()) 
				{
					filehashlist.add(mns.get(i).hash.toString());
					//System.out.print("已经没有link了，应该是最小的文件:");
					//System.out.println(mns.get(0).toJSONString());
					//k++;
				}
				
				else 
				{
					for(int a=0;a<mns.get(i).links.size();a++) 
					{
						//System.out.println("这是验证哈希:" + mns.get(i).hash.toString());
						findfile(mns.get(i).links.get(a).hash,filehashlist);
					}
					
			}
			
			
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getDownloadaddr() 
	{
		return downloadAddr;
	}
	public String getBackupaddr() 
	{
		return backupAddr;
	}
	public String getTableaddr() 
	{
		return tableAddr;
	}

	@Override
	public void start(String ip) {
		// TODO Auto-generated method stub
		
		ipfs = new IPFS("/ip4/"+ip+"/tcp/5001");
		
	}



}
