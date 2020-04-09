package application.ControllerImpl;

import java.io.IOException;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.web3j.crypto.CipherException;

import application.Controller.blockChainController;
import application.MODEL.NODE.initNode;
import application.Service.*;

@Controller("blockChainController")
public class blockChainControllerImpl implements blockChainController {

	private Logger log = Logger.getLogger("ipfs-manage-Controller");
	
	@Resource(name = "IpfsServices")
	IpfsServices ipfs;
	@Resource(name = "blockChainService")
	blockChainService blockChain;
	@Resource(name = "MainNodeTableService")
	MainNodeTableService mainnodetable;
	@Resource(name = "NameHashTableService")
	NameHashTableService namehashtable;
	@Resource(name = "OnlineNodeTableService")
	OnlineNodeTableService onlinetable;
	@Resource(name = "IPFSFileTableService")
	IPFSFileTableService nodebackuptable;
	@Resource(name = "FileBackupTableService")
	FileBackupTableService nodefiletable;
	@Resource(name = "initService")
	initService initService;
	
	public void updateLocalTable() 
	{
		String MainnodeTable = blockChain.getMainnodeTable();
		String NamehashTable = blockChain.getFilehashTable();
		String OnlinenodeTable = blockChain.getOnlineTable();
		
		ipfs.DownloadTable(MainnodeTable, mainnodetable.getTABLE());
		ipfs.DownloadTable(NamehashTable, namehashtable.getTABLE());
		ipfs.DownloadTable(OnlinenodeTable, onlinetable.getTABLE());
		
		log.info("更新本地表完成");
		
	}
	
	public void updatLocalNodefileTable(String ip) 
	{
		String hash = blockChain.getNodeFiletable(ip);
		ipfs.DownloadTable(hash, nodefiletable.getTABLE(ip));
		
		log.info("更新:\""+ip+"\"节点备份文件表完成");
	}
	
	public void updateLocalNodebackupTable(String filehash) 
	{
		String hash = blockChain.getNodebackupTable(filehash);
		ipfs.DownloadTable(hash, nodebackuptable.getTABLE(filehash));
		
		log.info("更新:\""+filehash+"\"的节点在线表成功");
	}
	
	
	public boolean updateMainnodeTable(String hash) 
	{
		return blockChain.updateMainnodeTable(hash);
	}

	public boolean updateNamehashTable(String hash) 
	{
		return blockChain.updateFilehashTable(hash);
	}
	
	public boolean updateOnlinenodeTable(String hash) 
	{
		return blockChain.updateOnlineTable(hash);
	}

	public void updateLocalNodebackTable(String filehash) 
	{
		String hash = blockChain.getNodebackupTable(filehash);
		
		ipfs.DownloadTable(hash, nodebackuptable.getTABLE(filehash));
		
		log.info("更新本地节点备份表成功");
	}
	
	public void updateLocalNodefileTable(String ip) 
	{
		String hash = blockChain.getNodeFiletable(ip);
		ipfs.DownloadTable(hash, nodefiletable.getTABLE(ip));
	}
		
	public boolean updateNodebackTable(String filehash,String hash) 
	{
		return blockChain.updateNodebackupTable(filehash, hash);
	}
	
	public boolean updateNodefileTable(String ip,String hash) 
	{
		return blockChain.updateNodeFiletable(ip, hash);
	}

	@Override
	public void startBlockChain() throws IOException, CipherException {
		// TODO Auto-generated method stub
		initNode info = initService.readInfo();
		blockChain.start(info.getBlockChainIp(), info.getBlockChainFilepath(), info.getBlockChainPassword());
	}
}
