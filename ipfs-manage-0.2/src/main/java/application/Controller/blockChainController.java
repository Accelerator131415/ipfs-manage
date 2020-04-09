package application.Controller;

import java.io.IOException;

import org.web3j.crypto.CipherException;

//负责所有表的更新，在ipfsController修改表之前都要调用这个类
public interface blockChainController {

	
	//从区块链获取最新的哈希值来更新本地的表
	public void updateLocalTable();
	
	public boolean updateMainnodeTable(String hash);

	public boolean updateNamehashTable(String hash);
	
	public boolean updateOnlinenodeTable(String hash);

	public void updateLocalNodebackTable(String filehash);
	
	public void updateLocalNodefileTable(String ip);
	
	public boolean updateNodebackTable(String filehash,String hash);
	
	public boolean updateNodefileTable(String ip,String hash);

	public void startBlockChain() throws IOException, CipherException;
}
