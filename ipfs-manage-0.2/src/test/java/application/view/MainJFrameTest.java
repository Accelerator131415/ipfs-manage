package application.view;

import javax.swing.JFrame;

import org.junit.Test;

public class MainJFrameTest {

	@Test
	public void test1() 
	{
		MainGUI mj = new MainGUI();
		
		//mj.setVisible(true);
		
		//JFrame j = new JFrame("aaa");
		
		while(true) 
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
