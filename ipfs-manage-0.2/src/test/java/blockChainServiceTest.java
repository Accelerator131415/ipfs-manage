import java.io.IOException;
import java.math.BigInteger;

import org.junit.Test;
import org.web3j.crypto.CipherException;

import application.MODEL.NODE.initNode;
import application.Service.*;
import application.ServiceImpl.blockChainServiceImpl;
import application.ServiceImpl.initServiceImpl;
import application.blockChain.Table;

public class blockChainServiceTest {

	//@Test
	public void test1() 
	{
		blockChainService bcs = new blockChainServiceImpl();
		
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
	
	@Test
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
	
}
