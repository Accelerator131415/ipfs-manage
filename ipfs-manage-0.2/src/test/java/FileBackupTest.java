import java.util.logging.Logger;

import org.junit.*;

import application.Service.FileBackupTableService;
import application.ServiceImpl.FileBackupTableServiceImpl;
import jdk.internal.org.jline.utils.Log;
public class FileBackupTest {

	private Logger log = Logger.getLogger("asd");
	@Test
	public void test1() 
	{
		FileBackupTableService fb = new FileBackupTableServiceImpl();
		
		for(int i=0;i<fb.getTable("192.168.99.1").getNum();i++) 
		{
			log.info(fb.getTable("192.168.99.1").getFiles().get(i));
		}
	}
}
