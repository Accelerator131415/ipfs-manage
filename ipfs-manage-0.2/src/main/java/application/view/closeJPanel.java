package application.view;

import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.annotation.Resource;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import application.Controller.CenterController;

public class closeJPanel extends threadJPanel {

	private JLabel info; 
	/**
	 * Create the panel.
	 */
	public closeJPanel() {
		setLayout(new BorderLayout(0, 0));
		
		info = new JLabel("正在关闭IPFS自我调度服务");
		info.setHorizontalAlignment(SwingConstants.CENTER);
		add(info, BorderLayout.CENTER);

		setVisible(true);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while(MainGUI.center.isService()) 
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		info.setText("已关闭IPFS自我调度服务");
		repaint();
		
	}

}
