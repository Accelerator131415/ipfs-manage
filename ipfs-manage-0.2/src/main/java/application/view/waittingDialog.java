package application.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class waittingDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JLabel mainLabel;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			waittingDialog dialog = new waittingDialog();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public waittingDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel titleLabel = new JLabel("正在启动服务");
			titleLabel.setFont(new Font("宋体", Font.PLAIN, 30));
			titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(titleLabel, BorderLayout.NORTH);
		}
		{
			mainLabel = new JLabel("正在启动服务，请稍等···");
			mainLabel.setFont(new Font("宋体", Font.PLAIN, 30));
			mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(mainLabel, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("启动完成");
				okButton.setFont(new Font("宋体", Font.PLAIN, 20));
				okButton.setActionCommand("OK");
				okButton.setEnabled(false);
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						dispose();		
					}
				});
				getRootPane().setDefaultButton(okButton);
			}
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void startSucess() 
	{
		mainLabel.setText("服务已启动");
		okButton.setEnabled(true);
		//cancelButton.setEnabled(false);
	}

}
