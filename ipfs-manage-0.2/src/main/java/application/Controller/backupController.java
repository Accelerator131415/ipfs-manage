package application.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.MODEL.NODE.backupNode;

public interface backupController extends Runnable {

	public static int limit = 10;
	
	public static Map<String,backupNode> backuplist  = new HashMap<String,backupNode>();
	
	//自己上传文件，通知其他节点备份
	public void noticebackup(String filehash) throws Exception;
	
	//接受到通知，自己备份这个文件
	public boolean selfbackup(String filehash) throws Exception;

	public void randomselectMain(String filehash) throws InterruptedException, Exception;
	
	public void closeService();
	
	//public backupNode removeBackupNode(String filehash);

	public static void addBackuplist(String filehash,backupNode backup) 
	{
		synchronized(backupController.class) 
		{
			backuplist.put(filehash,backup);
		}
		
	}
	
	public static backupNode removeBackupNode(String filehash) 
	{
		synchronized(backupController.class) 
		{
			return backuplist.remove(filehash);
		}
	}
	
	
}
