import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

import org.junit.*;
import application.Service.*;
import application.ServiceImpl.IpfsServicesImpl;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.multihash.Multihash;

public class IpfsServicesTest {

	int k =0;
	//IPFS ipfs = new IPFS("/ip4/192.168.43.135/tcp/5001");
	
	private Logger log = Logger.getLogger("aaa");
	//@Test
	public void test1() throws URISyntaxException 
	{
		IpfsServices is = new IpfsServicesImpl();
		//String mns = is.UploadFile("D:\\mylife\\PPT大赛\\初赛\\little fox.pptx");
		log.info(is.getBackupaddr());
		log.info(IpfsServicesTest.class.getClassLoader().getResource("").getPath().substring(1)+"");
	
		File one = new File(IpfsServicesTest.class.getClassLoader().getResource("").getPath()+"abx/");
		if(!one.exists()) 
		{
	
			one.mkdir();
		
		}
		//is.DownloadFile("QmUCvpaDziLzgADYiBFeQg5NCH2sC1DHxf346Rj56PYrM8", "little fox.pptx");
		
	
		
	}
	
	
//	public void test2() 
//	{
//		//System.out.println("???");
//		//IpfsServices is = new IpfsServicesImpl();
//		//is.DownloadFile("QmUCvpaDziLzgADYiBFeQg5NCH2sC1DHxf346Rj56PYrM8");
//		//is.DownloadFile("QmTpU1aLXzyew4ord9s1YPCyMask6vCfC7WVko2Fuw51Jw");
//		Multihash haha = Multihash.fromBase58("QmUCvpaDziLzgADYiBFeQg5NCH2sC1DHxf346Rj56PYrM8");
//		taowa(haha);
//		List<MerkleNode> mns;
//		try {
//			mns = ipfs.ls(haha);
//			for(int i=0;i<mns.size();i++) 
//			{	
//				System.out.println(mns.get(i).toJSONString());
//			}
//			
//			System.out.println("OK" + "总共文件数为:" +k);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
	
	public void test3() 
	{
		IpfsServices is = new IpfsServicesImpl();
		//is.backup("");
		List<String> ls = is.getSonfile("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT");
		for(int i=0;i<ls.size();i++) 
		{
			System.out.println(ls.get(i));
		}
		//is.backup("QmUCvpaDziLzgADYiBFeQg5NCH2sC1DHxf346Rj56PYrM8");
	}
	
	@Test
	public void test5() 
	{
		IpfsServices ipfsd = new  IpfsServicesImpl();
		ipfsd.start("192.168.99.1");
		log.info("文件的哈希值:"+ipfsd.UploadFile("D:\\我的大学\\毕业设计\\实验文件夹\\ceshis"));
		log.info("文件目录的哈希值:"+ipfsd.UploadFile("D:\\我的大学\\毕业设计\\实验文件夹\\"));
	
		ipfsd.DownloadFile("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT", "fileDir");
	
		
	}
	
	
	public void test4() 
	{
		IpfsServices is = new IpfsServicesImpl();
		is.DownloadFile("QmRr6pJHrQBxhBgzkVZXbdN58sM8UC3PX3EQk8MhYkjsRT", "ceshis");
	
	}
//	public void taowa(Multihash hash) 
//	{
//		//Multihash filePointer = Multihash.fromBase58(hash);
//		try {
//			List<MerkleNode> mns = ipfs.ls(hash);
//			for(int i=0;i<mns.size();i++) 
//			{
//				if(mns.get(i).links.isEmpty()) 
//				{
//					System.out.print("已经没有link了，应该是最小的文件:");
//					System.out.println(mns.get(0).toJSONString());
//					k++;
//				}
//				
//				else 
//				{
//					for(int a=0;a<mns.get(i).links.size();a++) 
//					{
//						System.out.println("这是验证哈希:" + mns.get(i).hash.toString());
//						taowa(mns.get(i).links.get(a).hash);
//					}
//					
//			}
//			
//			
//			}
//			
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
