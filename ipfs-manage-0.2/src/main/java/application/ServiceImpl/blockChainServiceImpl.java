package application.ServiceImpl;

import java.awt.List;
import java.io.IOException;
import java.math.*;
import java.util.logging.Logger;


import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.*;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionResult;
import org.web3j.protocol.core.methods.response.EthCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.rx.Web3jRx;
import org.web3j.tuples.generated.Tuple2;
import org.web3j.tx.gas.DefaultGasProvider;

import application.MODEL.NODE.hashnode;
import application.Service.blockChainService;
import application.blockChain.Table;


@Service("blockChainService")
public class blockChainServiceImpl implements blockChainService {

	private static final String RPC_URL = "http://192.168.43.135:1314";
	private static final Web3j web3 = Web3j.build(new HttpService(RPC_URL));
	private String port = "1314";
	//private String blockAccount = "0x067dea8624407ccce9344684f65ea638d4d2cddc";
	private Credentials credential;
	private String contractAddr = "0xd71977967f73a513b65ae2012c5099e501cc77c0";
	private String password = "123";
	private String filepath = "N:\\blockchain\\data\\keystore\\UTC--2020-03-25T11-33-08.192202800Z--2db370c14100919c6b8d14c5f71ff357d45fbdd3";
	private Table table;
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	
	@Override
	public boolean updateNodebackupTable(String filename,hashnode hash,String blockIp) throws Exception 
	{
		Table one = buildWeb3j(blockIp);
		
		one.updateNodesbackuptable(filename, hash.getHash(),new BigInteger(hash.getVersion()+"")).send();
		if(getNodebackupTable(filename).getHash().equalsIgnoreCase(hash.getHash())) 
		{
			//log.info("更新 "+filename+" 在线表的哈希");
			return true;
		}
		else 
		{
			//log.info("更新"+filename+"在线表的哈希失败");
			return false;
		}
		
		
		
	}
	
	@Override
	public hashnode getNodebackupTable(String filename) throws Exception
	{
		
		hashnode hash = new hashnode();
		Tuple2<String,BigInteger> res = table.getNodesbackuptable(filename).send();
		hash.setHash(res.component1());
		hash.setVersion(res.component2().intValue());
		
		//log.info("获取 "+filename+"的在线表的哈希:"+hash);
			
		
		
		return hash;
			
	}

	@Override
	public boolean updateMainnodeTable(hashnode hash,String blockIp) throws Exception
	{
		Table one = buildWeb3j(blockIp);
		
		one.updateMainnodetable(hash.getHash(),new BigInteger(hash.getVersion()+"")).send();
		if(getMainnodeTable().getHash().equalsIgnoreCase(hash.getHash())) 
		{
			return true;
		}
		//String update = table.getMainnodetable().send();
//		if(update.equalsIgnoreCase(hash)) 
//		{
//			log.info("更新主节点表成功");
//			return true;
//		}
		//List<TransactionResult> r = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getTransactions();
		//log.info(t.getLogs().toString());
		else 
		{
			//log.info("更新主节点表失败");
			return false;
		}
		
		
		
	}

	public hashnode getMainnodeTable() throws Exception
	{
		hashnode hash = new hashnode();
	
		Tuple2<String,BigInteger> res = table.getMainnodetable().send();
		//log.info("获取主节点表成功："+hash);
	
		hash.setHash(res.component1());
		hash.setVersion(res.component2().intValue());
		
		return hash;
	}
	
	public boolean updateFilehashTable(hashnode hash,String blockIp) throws Exception
	{
		Table one = buildWeb3j(blockIp);
		one.updateNamehashtable(hash.getHash(),new BigInteger(hash.getVersion()+"")).send();
		
		if(getFilehashTable().getHash().equalsIgnoreCase(hash.getHash())) 
		{
			//log.info("更新文件名-哈希表成功");
			return true;
		}
		else 
		{
			//log.info("更新文件名-哈希表失败");
			return false;
		}
			//log.info(t.getStatus());
		
		
	}
	
	public hashnode getFilehashTable() throws Exception
	{
		hashnode hash = new hashnode();
		
		
		Tuple2<String,BigInteger> res = table.getNamehashtable().send();
		hash.setHash(res.component1());
		hash.setVersion(res.component2().intValue());
		//log.info("获取文件名-哈希表的哈希成功:"+hash);
	
		
		return hash;
	}
	
	
	public boolean updateOnlineTable(hashnode hash,String blockIp) throws Exception
	{
		Table one = buildWeb3j(blockIp);
		one.updateOnlinetable(hash.getHash(),new BigInteger(hash.getVersion()+"")).send();
		if(getOnlineTable().getHash().equalsIgnoreCase(hash.getHash())) 
		{
			//log.info("更新在线节点表成功");
			return true;
		}
		else 
		{
			//log.info("更新在线节点表失败");
			return true;
		}
		
		
	}
	
	public hashnode getOnlineTable() throws Exception {
		hashnode hash = new hashnode();
		
		Tuple2<String ,BigInteger> res= table.getOnlinetable().send();
		hash.setHash(res.component1());
		hash.setVersion(res.component2().intValue());
		
		return hash;
	}
	
	public boolean updateNodeFiletable(String ip,hashnode hash,String blockIp) throws Exception
	{
		
		Table one = buildWeb3j(blockIp);
		
		one.updateNodefiletable(ip, hash.getHash(),new BigInteger(hash.getVersion()+"")).send();
		if(getNodeFiletable(ip).getHash().equalsIgnoreCase(hash.getHash())) 
		{
			//log.info("更新节点备份文件表成功");
			return true;
		}
		else 
		{
			//log.info("更新节点备份表失败");
			return false;
		}
		
		
	}
	
	public hashnode getNodeFiletable(String ip) throws Exception
	{
		hashnode hash = new hashnode();
		Tuple2<String,BigInteger> res = table.getNodefiletable(ip).send();
		hash.setHash(res.component1());
		hash.setVersion(res.component2().intValue());
		
		//log.info("获取节点:\""+ip+"\"的节点备份表哈希值成功");
		
		return hash;
	}
	
	
	public boolean updateLimit(int limit)
	{
		try 
		{
			table.updateNodestableLimit(new BigInteger(limit+"")).send();
			log.info("更新备份最大节点数，其值为:"+limit);
			return true;
			
		}catch(Exception e) 
		{
			log.info("更新最大备份值失败，原因：区块链操作异常");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public int getLimit()
	{
		int limit = 0;
		
		try 
		{
			
			limit = table.getNodestableLimit().send().intValue();
			log.info("获取最大备份节点数:"+limit);
		}catch(Exception e) 
		{
			log.info("获取最大备份节点数失败，原因：区块链操作异常");
			e.printStackTrace();
			
		}
		return limit;
		
		
	}
	public boolean updateYu(double yuzhi)
	{
		try 
		{
			BigInteger yu = new BigInteger((int)(yuzhi*100)+"");
			table.updateNodestableYu(yu).send();
			log.info("更新了阈值："+yuzhi);
			return true;
		}catch(Exception e) 
		{
			log.info("更新非主节点阈值失败，原因：区块链操作异常");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public double getYu()
	{
		double yuzhi = 0;
		try 
		{
			int yu = table.getNodestableYu().send().intValue();
			yuzhi = (double)yu/100;
			log.info("获取非主节点阈值:"+yuzhi);
			
		}catch(Exception e) 
		{
			log.info("获取非主节点阈值失败，原因：区块链操作异常");
			e.printStackTrace();
		}
		return yuzhi;
		
	}

	
	public boolean updateYu_main(double yu_main) 
	{
		int yumain = (int)(yu_main*100);
		try {
			table.updateNodestableYu_main(new BigInteger(yumain+"")).send();
			log.info("更新主节点阈值成功:"+yu_main);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("更新主节点阈值失败，原因：区块链操作异常");
			//e.printStackTrace();
			return false;
		}
		
	}
	
	public double getYu_main() 
	{
		double yu_main = 0;
		
		try {
			int yu = table.getNodestableYu_main().send().intValue();
			yu_main = (double)yu/100;
			log.info("获取主节点阈值：\""+yu_main+"\"成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.info("获取主节点阈值失败，原因：区块链操作异常");
			e.printStackTrace();
		}
		
		return yu_main;
	}
	


	@Override
	public void start(String blockChainIp, String filepath, String password) throws IOException, CipherException {
		// TODO Auto-generated method stub
		
			//Web3ClientVersion web3clientversion = web3j.web3ClientVersion().send()			
			credential = WalletUtils.loadCredentials(password, filepath);
			String RPC_URL = "http://"+blockChainIp+":"+port;
			Web3j web3 = Web3j.build(new HttpService(RPC_URL));
			
			table = Table.load(contractAddr, web3, credential, new DefaultGasProvider());
			table.setGasProvider(new DefaultGasProvider() 
			{
				public BigInteger getGasPrice(String coutractFunc) 
				{
					return BigInteger.valueOf(10000L);
				}
				
				public BigInteger getGasLimit(String contractFunc) 
				{
					return BigInteger.valueOf(5000000000L);
				}
				
				
			});
			log.info("启动区块链服务");
		
		
	}

	@Override
	public Table buildWeb3j(String ip) {
		// TODO Auto-generated method stub
		String RPC_URL = "http://"+ip+":"+port;
		Web3j web3 = Web3j.build(new HttpService(RPC_URL));
		Table one = Table.load(contractAddr, web3, credential, new DefaultGasProvider());
		return one;
	}

	


	}

	

	
	

