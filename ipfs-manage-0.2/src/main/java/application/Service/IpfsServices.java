package application.Service;

import java.util.List;

public interface IpfsServices {
	
	
	//根据文件地址上传文件，返回文件的hash值
	public String UploadFile(String addr);
	
	public void DownloadFile(String hash,String filename);
	
	public void DownloadTable(String hash,String filename);
	
	//备份文件，给一个大文件的hash值，该方法自动下载该哈希值下面的小底层文件，而不是直接下载一个大文件
	//虽然同样是将一整个文件下载到单个主机中，但是少了拼接文件的功夫
	//即使直接给一个小文件，也是可以备份的
	public void backup(String hash);
			
	//获取一个指定文件的所有子文件（256kb大小）的哈希值，装在一个List链表中返回
	public List<String> getSonfile(String hash);
	
	public String getDownloadaddr();
	
	public String getBackupaddr();
	
	public String getTableaddr();
	//获取指定hash的默克尔有向无环图
	//public String getMerkleTree(String hash);
	
	public void start(String ip);
	
}
