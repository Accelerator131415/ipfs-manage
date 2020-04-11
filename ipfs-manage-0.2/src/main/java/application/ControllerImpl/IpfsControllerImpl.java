package application.ControllerImpl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.stereotype.*;

import application.Controller.IpfsController;
import application.Controller.backupController;
import application.Controller.blockChainController;
import application.MODEL.NODE.*;
import application.MODEL.TABLE.*;
import application.Service.*;

@Controller("IpfsController")
public class IpfsControllerImpl implements IpfsController {

	@Resource(name = "IpfsServices")
	IpfsServices ipfs;
	@Resource(name = "blockChainController")
	blockChainController blockChain;
	@Resource(name = "backupController")
	backupController backup;
	@Resource(name = "NameHashTableService")
	NameHashTableService namehashtable;
	@Resource(name = "IPFSFileTableService")
	IPFSFileTableService ipfsfile;
	@Resource(name = "initService")
	initService initService;
	
	private Logger log = Logger.getLogger("ipfs-manage-Controller");
	
	
	//完成两个工作，通知备份
	//更新文件名-哈希表
	public String upload(String addr,String filename) throws Exception{
		// TODO Auto-generated method stub
		String hash = ipfs.UploadFile(addr+"\\"+filename);
		try {
			//InetAddress ip = InetAddress.getLocalHost();
			backup.noticebackup(hash);			
			//更新文件名-哈希备份表
			NameHashNode nnode = new NameHashNode(filename,hash);
			hashnode namehash = new hashnode();			
			do
			{
				blockChain.updateLocalOnlinenodeTable();
				hashnode name = blockChain.updateLocalNamehashTable();
				namehashtable.InsertNode(nnode);
				String newhash = ipfs.UploadFile(ipfs.getTableaddr()+namehashtable.getTABLE());
				namehash.setHash(newhash);
				namehash.setVersion(name.getVersion());
				
			}while(!blockChain.updateNamehashTable(namehash));		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//OnlineNodeTable online = onlinetable.getTable();	
		return hash;
	}

	public void download(String hash) throws Exception {
		// TODO Auto-generated method stub
		blockChain.updateLocalTable();
		NameHashNode nhnode =namehashtable.getNodeByHash(hash);
		ipfs.DownloadFile(hash, nhnode.getFilename());
		
		log.info("已下载文件:"+nhnode.getFilename()+";");
		
	}
	
	public boolean backup(String hash)
	{
		ipfs.backup(hash);
		IPFSNode inode = new IPFSNode();
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			
			inode.setIp(ip.getHostAddress());
			inode.setOnline(true);
			String filename = namehashtable.getNodeByHash(hash).getFilename();
			ipfsfile.InsertNode(inode, filename);
			
			log.info("备份文件:\""+hash+"\"成功");
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("备份文件\""+hash+"\"失败,原因:没有找到本机IP");
			return false;
		}
		
		
		
		
	}

	@Override
	public void startIpfs() throws IOException {
		// TODO Auto-generated method stub
		initNode info = initService.readInfo();
		ipfs.start(info.getIpfsIp());
	}

}
