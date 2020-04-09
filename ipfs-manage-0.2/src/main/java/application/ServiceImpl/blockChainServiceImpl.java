package application.ServiceImpl;

import java.awt.List;
import java.math.*;
import java.util.logging.Logger;


import org.springframework.stereotype.Service;
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
import org.web3j.tx.gas.DefaultGasProvider;

import application.Service.blockChainService;
import application.blockChain.Table;


@Service("blockChainService")
public class blockChainServiceImpl implements blockChainService {

	private static final String RPC_URL = "http://192.168.43.135:1314";
	private static final Web3j web3 = Web3j.build(new HttpService(RPC_URL));
	//private String blockAccount = "0x067dea8624407ccce9344684f65ea638d4d2cddc";
	private Credentials credential;
	private String contractAddr = "0xd71977967f73a513b65ae2012c5099e501cc77c0";
	private String password = "123";
	private String filepath = "N:\\blockchain\\data\\keystore\\UTC--2020-03-25T11-33-08.192202800Z--2db370c14100919c6b8d14c5f71ff357d45fbdd3";
	private Table table;
	private Logger log = Logger.getLogger("ipfs-manage-Service");
	
	public blockChainServiceImpl() 
	{
		try 
		{
			//Web3ClientVersion web3clientversion = web3j.web3ClientVersion().send()			
			credential = WalletUtils.loadCredentials(password, filepath);
			
			
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
		}catch(Exception e) 
		{
			e.printStackTrace();
			log.info("启动区块链服务失败");
		}
		
	}
	
	public boolean updateNodebackupTable(String filename,String hash) 
	{
		
		try {
			table.updateNodesbackuptable(filename, hash).send();
			if(table.getNodesbackuptable(filename).send().equalsIgnoreCase(hash)) 
			{
				log.info("更新 "+filename+" 在线表的哈希");
				return true;
			}
			else 
			{
				log.info("更新"+filename+"在线表的哈希失败");
				return false;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("更新"+filename+"在线表的哈希失败，原因：区块链读取异常");
			return false;
		}
		
	}
	
	public String getNodebackupTable(String filename)
	{
		String hash = "";
		
		try 
		{	
			hash = table.getNodesbackuptable(filename).send();
			log.info("获取 "+filename+"的在线表的哈希:"+hash);
			
		}catch(Exception e) 
		{
			log.info("获取"+filename+"的在线表的哈希失败，原因：区块链读取异常");
			e.printStackTrace();
		}
		
		return hash;
			
	}

	public boolean updateMainnodeTable(String hash) 
	{
		try 
		{
			table.updateMainnodetable(hash).send();
			String update = table.getMainnodetable().send();
			if(update.equalsIgnoreCase(hash)) 
			{
				log.info("更新主节点表成功");
				return true;
			}
			//List<TransactionResult> r = web3j.ethGetBlockByNumber(DefaultBlockParameterName.LATEST, true).send().getBlock().getTransactions();
			//log.info(t.getLogs().toString());
			else 
			{
				log.info("更新主节点表失败");
				return false;
			}
			
		}
		catch(Exception e) 
		{
			log.info("更新主节点表失败，原因：区块链操作异常 ");
			//e.printStackTrace();
			return false;
		}
		
	
		
	}

	public String getMainnodeTable() 
	{
		String hash = "";
		try 
		{
			hash = table.getMainnodetable().send();
			log.info("获取主节点表成功："+hash);
		}catch(Exception e) 
		{
			log.info("获取主节点表失败，原因：区块链操作异常");
			e.printStackTrace();
		}
		
		return hash;
	}
	
	public boolean updateFilehashTable(String hash) 
	{
		try 
		{
			table.updateNamehashtable(hash).send();
			if(table.getNamehashtable().send().equalsIgnoreCase(hash)) 
			{
				log.info("更新文件名-哈希表成功");
				return true;
			}
			else 
			{
				log.info("更新文件名-哈希表失败");
				return false;
			}
			//log.info(t.getStatus());
		}catch(Exception e) 
		{
			log.info("更新文件名-哈希表失败，原因：区块链操作异常");
			e.printStackTrace();
			return false;
		}
		
	}
	
	public String getFilehashTable() 
	{
		String hash = "";
		
		try 
		{
			hash = table.getNamehashtable().send();
			log.info("获取文件名-哈希表的哈希成功:"+hash);
		}catch(Exception e) 
		{
			log.info("获取文件名-哈希表的哈希失败，原因：区块链操作异常");
			e.printStackTrace();
		}
		
		return hash;
	}
	
	
	public boolean updateOnlineTable(String hash) 
	{
		try
		{
			table.updateOnlinetable(hash).send();
			if(table.getOnlinetable().send().equalsIgnoreCase(hash)) 
			{
				log.info("更新在线节点表成功");
				return true;
			}
			else 
			{
				log.info("更新在线节点表失败");
				return true;
			}
			
		}catch(Exception e) 
		{
			e.printStackTrace();
			log.info("更新在线节点表失败,原因：区块链操作异常");
			return false;
		}
	}
	
	public String getOnlineTable() {
		String hash = "";
		try 
		{
			hash = table.getOnlinetable().send();
			log.info("获取在线节点表");
			
		}catch(Exception e) 
		{
			log.info("获取在线节点失败，原因：区块链操作异常");
			e.printStackTrace();
		}
		return hash;
	}
	
	public boolean updateNodeFiletable(String ip,String hash) 
	{
		try 
		{
			table.updateNodefiletable(ip, hash).send();
			if(table.getNodefiletable(ip).send().equalsIgnoreCase(hash)) 
			{
				log.info("更新节点备份文件表成功");
				return true;
			}
			else 
			{
				log.info("更新节点备份表失败");
				return false;
			}
			
		}catch(Exception e) 
		{
			log.info("更新节点备份文件表失败，原因：区块链操作异常");
			e.printStackTrace();
			return false;
		}
	}
	
	public String getNodeFiletable(String ip) 
	{
		String hash = "";
		
		try 
		{
			hash = table.getNodefiletable(ip).send();
			
			log.info("获取节点:\""+ip+"\"的节点备份表哈希值成功");
		}catch(Exception e) 
		{
			log.info("获取节点:\""+ip+"\"的节点备份表哈希值失败，原因：区块链操作异常");
			e.printStackTrace();
		}
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


	
	
}
