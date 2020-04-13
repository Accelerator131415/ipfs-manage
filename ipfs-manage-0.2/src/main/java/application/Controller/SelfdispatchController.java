package application.Controller;

public interface SelfdispatchController extends Runnable {

	public static double yu = 0.5;
	public static double yu_main = 0.3;
	
	
	public void close();
	
	public boolean isNeeddispatch(String filehash) throws Exception;
	
	
	//检查一个节点所备份的所有文件是否需要重新备份，并且备份需要备份的文件
	public void selfdiapatch(String ip);
	
	
}
