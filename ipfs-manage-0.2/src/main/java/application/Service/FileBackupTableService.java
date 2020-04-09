package application.Service;

import application.MODEL.TABLE.FileBackupTable;


//节点备份文件表，一个节点备份了哪些文件
public interface FileBackupTableService {

	
	public boolean createTable();
	
	public boolean InsertFile(String filehash);
	
	public boolean isExist(String filehash);
	
	public FileBackupTable getTable(String ip);

	public void setAddr(String addr);

	public String getAddr();

	public String getTABLE(String ip);
}
