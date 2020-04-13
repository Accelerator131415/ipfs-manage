import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;
import org.web3j.crypto.CipherException;

import application.MODEL.NODE.hashnode;
import application.MODEL.NODE.initNode;
import application.Service.*;
import application.ServiceImpl.IpfsServicesImpl;
import application.ServiceImpl.blockChainServiceImpl;
import application.ServiceImpl.initServiceImpl;
import application.blockChain.Table;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import jdk.internal.org.jline.utils.Log;

public class blockChainServiceTest {

	private String key="N:\\blockchain\\data\\keystore\\UTC--2020-03-25T11-33-08.192202800Z--2db370c14100919c6b8d14c5f71ff357d45fbdd3";
	private String password = "123";
	private String ip = "192.168.99.1";
	
	private Logger log = Logger.getLogger("AS");
	//@Test
	public void test1() throws IOException, CipherException 
	{
		blockChainService bcs = new blockChainServiceImpl();
		bcs.start("192.168.99.1", "N:\\blockchain\\data\\keystore\\UTC--2020-03-25T11-33-08.192202800Z--2db370c14100919c6b8d14c5f71ff357d45fbdd3", "123");
		//bcs.getFilehashTable();
		//bcs.getMainnodeTable();
		
		while(true) 
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//bcs.updateMainnodeTable("MainnodeTable");
//		String hash = bcs.getMainnodeTable();
//		System.out.println(hash);
//		bcs.updateFilehashTable("this is FilehashTable");
//		bcs.getFilehashTable();
//		bcs.updateOnlineTable(hash);
		//bcs.getLimit();
		//bcs.getYu_main();
		//bcs.getYu();
	}
	
	//@Test
	public void test2() throws IOException, CipherException 
	{
		blockChainService bcs = new blockChainServiceImpl();
		initService init = new initServiceImpl();
		initNode i = init.readInfo();
		bcs.start(i.getBlockChainIp(), i.getBlockChainFilepath(), i.getBlockChainPassword());
		Table one = bcs.buildWeb3j("192.168.99.1");
		one.updateNodesbackuptable("askdjask", "hahahahahhah", new BigInteger(0+""));
		Table two = bcs.buildWeb3j("192.168.99.1");
	}
	
	//@Test
	public void test() throws Exception 
	{
		blockChainService bcs = new blockChainServiceImpl();
		bcs.start(ip, key, password);
		IpfsServices ipfs = new IpfsServicesImpl();
		ipfs.start(ip);
		
		String hash = ipfs.UploadFile("C:\\Users\\Administrator\\.selfDispatch\\节点维护表\\MainNodeTable.table");
		hashnode ad = new hashnode();
		ad.setHash(hash);
		ad.setVersion(bcs.getNodebackupTable("ad").getVersion());
		bcs.updateNodebackupTable("ad", ad, ip);
		//QmRyRnvPx79Kwkvfu2yibfmHjD82T4rLRV8jKaZKQugSbh
		log.info(bcs.getNodebackupTable("ad").getHash());
		log.info("over");
		
	}
	//@Test
	public void test4() throws Exception 
	{
		blockChainService bcs = new blockChainServiceImpl();
		bcs.start(ip, key, password);
		IpfsServices ipfs = new IpfsServicesImpl();
		ipfs.start(ip);
		//QmWgxHat2Unkoc2QVjLPCCrg5BWLaAoDnyPtDewpxzFaUk
		//QmWgxHat2Unkoc2QVjLPCCrg5BWLaAoDnyPtDewpxzFaUk
		//ipfs.DownloadFile(bcs.getNodebackupTable("ad").getHash(), "thisistable");
		//QmWgxHat2Unkoc2QVjLPCCrg5BWLaAoDnyPtDewpxzFaUk
	
		log.info(ipfs.UploadFile("C:\\Users\\Administrator\\.selfDispatch\\节点维护表\\QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT.table"));
//		log.info(bcs.getNodebackupTable("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT").getHash());
//		log.info(bcs.getNodebackupTable("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT").getVersion()+"");
		
//		hashnode a = new hashnode();
//		a.setHash("bkssssssaaaaask");
//		a.setVersion(bcs.getNodebackupTable("ad").getVersion());
//		
//		bcs.updateNodebackupTable("ad", a, "192.168.99.1");
//		
//		log.info(bcs.getNodebackupTable("ad").getHash());
//		log.info(bcs.getNodebackupTable("ad").getVersion()+"");
		
		
	}
	//@Test
	public void test5() 
	{
		IpfsServices ipfs = new IpfsServicesImpl();
		ipfs.start(ip);
		ipfs.DownloadTable("QmPK3Y8gA4m3tHAdgRLj9AGxCv8r4sJ8RMnTxSS2Lrm21p","这是一个名字");
		
	}
	@Test
	public void test6() 
	{
		IPFS ipfs = new IPFS("/ip4/192.168.99.1/tcp/5001");
		NamedStreamable.FileWrapper savefile = new NamedStreamable.FileWrapper(new File("C:\\Users\\Administrator\\.selfDispatch\\节点维护表\\NameHashTable.table"));
		try 
		{
			List<MerkleNode> res = ipfs.add(savefile);			
			//System.out.println(res.get(0).toJSONString());
			
//			log.info("向IPFS上传了文件:"+addr+"\n"
//					+"返回哈希值为:"+res.get(0).hash.toString());
			log.info(res.get(0).hash.toString());
		}
		catch(IOException e) 
		{
			e.printStackTrace();
			//log.info("向IPFS上传文件:"+addr+"失败");
		}
	}
	
}
