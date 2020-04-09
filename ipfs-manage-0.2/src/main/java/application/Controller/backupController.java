package application.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.MODEL.NODE.backupNode;

public interface backupController extends Runnable {

	public static int limit = 10;
	
	public static Map<String,backupNode> backuplist  = new HashMap<>();
	
	//自己上传文件，通知其他节点备份
	public void noticebackup(String filehash);
	
	//接受到通知，自己备份这个文件
	public boolean selfbackup(String filehash);

	public void randomselectMain(String filehash);
	
	public void closeService();

	public backupNode removeBackupNode(String filehash);

}
