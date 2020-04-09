package application.Service;

import java.io.IOException;

import org.web3j.crypto.CipherException;

import application.MODEL.NODE.hashnode;
import application.blockChain.Table;

public interface blockChainService {

	//public int getBalance();

	
	public void start(String blockChainIp,String filepath,String password) throws IOException, CipherException;
	
	//更新指定文件的在线文件表的hash值，表示节点的在线状态已经改变了
	public boolean updateNodebackupTable(String filename,hashnode hash,String blockIp) throws Exception ;
	
	//获取指定文件的在线文件表的hash值，返回的是一个IPFS哈希值
	public hashnode getNodebackupTable(String filename) throws Exception;
	
	//更新主节点表的哈希
	public boolean updateMainnodeTable(hashnode hash,String blockIp) throws Exception;
	
	//获取主节点表的哈希
	public hashnode getMainnodeTable() throws Exception;
	
	//更新文件哈希表的哈希
	public boolean updateFilehashTable(hashnode hash,String blockIp) throws Exception;
	
	//获取文件哈希表的哈希
	public hashnode getFilehashTable() throws Exception;
	
	public boolean updateOnlineTable(hashnode hash,String blockIp) throws Exception;
	
	public hashnode getOnlineTable() throws Exception;
	
	public boolean updateNodeFiletable(String ip,hashnode hash,String blockIp) throws Exception;
	
	public hashnode getNodeFiletable(String ip) throws Exception;
	
	
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
	
	public Table buildWeb3j(String ip);
}
