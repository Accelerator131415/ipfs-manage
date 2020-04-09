package application.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

import application.Controller.CenterController;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;

import org.springframework.cglib.transform.impl.AddDelegateTransformer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.awt.GridLayout;
import java.awt.Color;


public class MainGUI implements Runnable {

	private JFrame frmIpfs;
	private startJPanel infomation;
	public static CenterController center;
	private Logger log = Logger.getLogger("ipfs-manage-view");
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		String xmlpath = "classpath:applicationContext.xml";
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(xmlpath);
		
		center = (CenterController)applicationContext.getBean("CenterController");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					
					window.frmIpfs.setVisible(true);
					new Thread(window).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmIpfs = new JFrame();
		frmIpfs.setTitle("IPFS自我调度系统");
		frmIpfs.setBounds(100, 100, 450, 300);
		frmIpfs.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel Title = new JPanel();
		Title.setBackground(new Color(0, 255, 255));
		frmIpfs.getContentPane().add(Title, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("ipfs自我调度程序");
		Title.add(lblNewLabel);
		
		JPanel Buttons = new JPanel();
		Buttons.setForeground(new Color(0, 255, 255));
		Buttons.setBackground(new Color(127, 255, 212));
		frmIpfs.getContentPane().add(Buttons, BorderLayout.WEST);
		Buttons.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton startButton = new JButton("启动");
		startButton.setForeground(new Color(0, 0, 0));
		startButton.setBackground(new Color(0, 255, 255));
		Buttons.add(startButton);
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>()
				{
					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						
					
						//frmIpfs.getContentPane().add(infomation,BorderLayout.CENTER);
						//new Thread(infomation).start();
						infomation.drawStart();
						center.start();
						
						return null;
					}
					
				};
				worker.execute();
			}
		});
		
		
		
		JButton closeButton = new JButton("关闭");
		closeButton.setBackground(new Color(0, 255, 255));
		Buttons.add(closeButton);
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>()
				{

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						
						center.exit();
						//infomation = new closeJPanel();
						//frmIpfs.getContentPane().add(infomation,BorderLayout.CENTER);
						//new Thread(infomation).start();
						return null;
					}
					
				};
				worker.execute();
				
				
			}
		});
		
		
		
		JButton uploadButton = new JButton("上传文件");
		uploadButton.setBackground(new Color(0, 255, 255));
		Buttons.add(uploadButton);
		uploadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				log.info("画上传界面");
				
				infomation.drawUpload();
				//frmIpfs.add(infomation);
				log.info("画完了");
			}
		});
		
		JButton downloadButton = new JButton("下载文件");
		downloadButton.setBackground(new Color(0, 255, 255));
		Buttons.add(downloadButton);
		downloadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				infomation.drawDownload();
			}
		});
		
		JButton infoButton = new JButton("文件信息");
		infoButton.setBackground(new Color(0, 255, 255));
		Buttons.add(infoButton);
		infoButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				infomation.drawSerch();
			}
		});
		
		
		
		infomation = new startJPanel();
		frmIpfs.getContentPane().add(infomation,BorderLayout.CENTER);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) 
		{
			frmIpfs.repaint();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}