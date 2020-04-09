package application.Service;

import java.io.IOException;

import org.web3j.crypto.CipherException;

public interface blockChainService {

	//public int getBalance();

	
	public void start(String blockChainIp,String filepath,String password) throws IOException, CipherException;
	
	//更新指定文件的在线文件表的hash值，表示节点的在线状态已经改变了
	public boolean updateNodebackupTable(String filename,String hash) ;
	
	//获取指定文件的在线文件表的hash值，返回的是一个IPFS哈希值
	public String getNodebackupTable(String filename);
	
	//更新主节点表的哈希
	public boolean updateMainnodeTable(String hash);
	
	//获取主节点表的哈希
	public String getMainnodeTable();
	
	//更新文件哈希表的哈希
	public boolean updateFilehashTable(String hash);
	
	//获取文件哈希表的哈希
	public String getFilehashTable();
	
	public boolean updateOnlineTable(String hash);
	
	public String getOnlineTable();
	
	public boolean updateNodeFiletable(String ip,String hash);
	
	public String getNodeFiletable(String ip);
	
	
	//更新IPFSFileTable的最大备份节点数的设置值
	public boolean updateLimit(int limit);
	
	//获取IPFSFileTable的最大备份节点数的设置值
	public int getLimit();
	
	//更新主节点阈值
	public boolean updateYu_main(double yu_main);
	
	//获取主节点阈值
	public double getYu_main();
	
	//更新阈值的限制
	public boolean updateYu(double yuzhi);
	
	//获取阈值的大小
	public double getYu();
}
