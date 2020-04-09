package application.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.logging.Logger;

import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import application.MODEL.TABLE.IPFSFileTable;
import application.MODEL.TABLE.NamehashTable;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;

public class startJPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	private Logger log = Logger.getLogger("ipfs-manage-view");
	
	private JLabel serviceInfo;
	private JTextField serchFile;
	private JTable table;
	private JTextField seachInfo;
	private JTable filehashTable;
	private JTextField path;
	private JTextField filename;
	private JTextField hash;
	public startJPanel() {
		super();

	}

	public void drawStart() 
	{
		removeAll();


		setLayout(new BorderLayout(0, 0));
		
		serviceInfo = new JLabel();
		serviceInfo.setText("正在启动备份服务");
		serviceInfo.setHorizontalAlignment(SwingConstants.CENTER);
		add(serviceInfo, BorderLayout.CENTER);

		new Thread() 
		{
			@Override
			public void run() 
			{
				while(!MainGUI.center.isService()) 
				{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				serviceInfo.setText("备份服务已启动");
				
			}
		}.start();
		
		setVisible(true);
		repaint();
	}
	
	public void drawUpload() 
	{
		removeAll();
		setLayout(new BorderLayout(0, 0));
		
		JLabel titleLable = new JLabel("请输入你要备份的文件");
		titleLable.setFont(new Font("华康娃娃体W5", Font.PLAIN, 32));
		titleLable.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLable, BorderLayout.NORTH);
		
		JButton uploadButton = new JButton("提交");
		add(uploadButton, BorderLayout.SOUTH);
		uploadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>()
				{

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						
						MainGUI.center.uploadFile(path.getText(), filename.getText());
						//infomation = new closeJPanel();
						//frmIpfs.getContentPane().add(infomation,BorderLayout.CENTER);
						//new Thread(infomation).start();
						return null;
					}
					
				};
				worker.execute();
				
			}
		});
		
		JPanel zonepanel = new JPanel();
		add(zonepanel, BorderLayout.CENTER);
		zonepanel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(30);
		flowLayout.setHgap(10);
		zonepanel.add(panel_1, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setVgap(30);
		zonepanel.add(panel_2, BorderLayout.SOUTH);
		
		JPanel panel_3 = new JPanel();
		zonepanel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		
		JLabel pathlable = new JLabel("文件路径");
		panel_4.add(pathlable);
		
		path = new JTextField();
		panel_4.add(path);
		path.setColumns(50);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
		
		JLabel filenamelable = new JLabel("文件名");
		panel_5.add(filenamelable);
		
		filename = new JTextField();
		panel_5.add(filename);
		filename.setColumns(50);
		setVisible(true);
		repaint();
	}
	
	public void drawDownload() 
	{
		removeAll();
		
		setLayout(new BorderLayout(0, 0));
		
		JLabel titleLabel = new JLabel("下载文件");
		titleLabel.setFont(new Font("华康娃娃体W5", Font.PLAIN, 32));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel, BorderLayout.NORTH);
		
		JButton downloadButton = new JButton("下载");
		downloadButton.setFont(new Font("华康娃娃体W5", Font.PLAIN, 32));
		add(downloadButton, BorderLayout.SOUTH);
		downloadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>()
				{

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						
						MainGUI.center.downloadFile(hash.getText());
						//infomation = new closeJPanel();
						//frmIpfs.getContentPane().add(infomation,BorderLayout.CENTER);
						//new Thread(infomation).start();
						return null;
					}
					
				};
				worker.execute();
				
				
			}
		});
		
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(40);
		panel.add(panel_1, BorderLayout.NORTH);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_2.getLayout();
		flowLayout_1.setVgap(40);
		panel.add(panel_2, BorderLayout.SOUTH);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lable = new JLabel("文件哈希");
		lable.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(lable);
		
		hash = new JTextField();
		panel_3.add(hash);
		hash.setColumns(40);

		setVisible(true);
		repaint();
	}
	
	public void drawSerch() 
	{
		removeAll();
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel searchPanel = new JPanel();
		add(searchPanel, BorderLayout.NORTH);
		
		serchFile = new JTextField();
		searchPanel.add(serchFile, "1, 2, 11, 1, fill, default");
		serchFile.setColumns(60);
		
		JButton serchButton = new JButton("搜索");
		serchButton.setFont(new Font("华康娃娃体W5", Font.PLAIN, 20));
		searchPanel.add(serchButton, "12, 2");
		serchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>()
				{

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						
						drawFileonline(serchFile.getText());
						//infomation = new closeJPanel();
						//frmIpfs.getContentPane().add(infomation,BorderLayout.CENTER);
						//new Thread(infomation).start();
						return null;
					}
					
				};
				worker.execute();
				
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		NamehashTable ntable = MainGUI.center.getFileHashInfo();
		
		String[] colname = new String[2];
		colname[0] = "文件哈希值";
		colname[1] = "文件名";
		
		String[][] rowdate = new String[ntable.getNodes()][2];
		for(int i=0;i<ntable.getNodes();i++) 
		{
			rowdate[i][0] = ntable.getTable().get(i).getHash();
			rowdate[i][1] = ntable.getTable().get(i).getFilename();
		}
		
		
		table = new JTable(rowdate,colname);
		scrollPane.setViewportView(table);
		setVisible(true);
		repaint();
	}
	
	public void drawFileonline(String filehash) 
	{
		removeAll();
		
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		seachInfo = new JTextField();
		panel.add(seachInfo, "1, 2, 5, 1, fill, default");
		seachInfo.setColumns(50);
		seachInfo.setText(filehash);
		
		JButton searchButton = new JButton("搜索");
		panel.add(searchButton, "6, 2");
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>()
				{

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						
						drawFileonline(filehash);
						//infomation = new closeJPanel();
						//frmIpfs.getContentPane().add(infomation,BorderLayout.CENTER);
						//new Thread(infomation).start();
						return null;
					}
					
				};
				worker.execute();
				
			}
		});
		
		JButton returnButton = new JButton("返回");
		panel.add(returnButton, "8, 2");
		returnButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>()
				{

					@Override
					protected Void doInBackground() throws Exception {
						// TODO Auto-generated method stub
						
						drawSerch();
						//infomation = new closeJPanel();
						//frmIpfs.getContentPane().add(infomation,BorderLayout.CENTER);
						//new Thread(infomation).start();
						return null;
					}
					
				};
				worker.execute();
				
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		IPFSFileTable itable= MainGUI.center.getFileonlinebackupInfo(filehash);
		
		String[] colname = new String[2];
		colname[0] = "节点IP";
		colname[1] = "在线情况";
		
		String[][] rowdate = new String[itable.getNodes_had()][2];
		for(int i=0;i<itable.getNodes_had();i++) 
		{
			rowdate[i][0] = itable.getNodes().get(i).getIp();
			rowdate[i][1] = itable.getNodes().get(i).isOnline()+"";
		}
		
		filehashTable = new JTable();
		scrollPane.setViewportView(filehashTable);
		
		setVisible(true);
		repaint();
	}
	
}