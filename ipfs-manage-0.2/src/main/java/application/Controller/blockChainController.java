package application.Controller;

import java.io.IOException;
import org.web3j.crypto.CipherException;

import application.MODEL.NODE.hashnode;


//负责所有表的更新，在ipfsController修改表之前都要调用这个类
public interface blockChainController {

	
	//从区块链获取最新的哈希值来更新本地的表
	public void updateLocalTable() throws Exception;
	
	public hashnode updateLocalMainnodeTable() throws Exception;
	
	public hashnode updateLocalNamehashTable() throws Exception;
	
	public hashnode updateLocalOnlinenodeTable() throws Exception;
	
	public hashnode updateLocalNodebackTable(String filehash) throws Exception;
	
	public hashnode updateLocalNodefileTable(String ip) throws Exception;
	
	public boolean updateMainnodeTable(hashnode hash) throws InterruptedException, Exception;

	public boolean updateNamehashTable(hashnode hash) throws InterruptedException, Exception;
	
	public boolean updateOnlinenodeTable(hashnode hash) throws InterruptedException;

	public boolean updateNodebackTable(String filehash,hashnode hash) throws Exception;
	
	public boolean updateNodefileTable(String ip,hashnode hash) throws Exception;
	
	public void startBlockChain() throws IOException, CipherException;
}
