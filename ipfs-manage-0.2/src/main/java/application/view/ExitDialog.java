package application.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

public class ExitDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel Label;
	private JButton cancelButton;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			ExitDialog dialog = new ExitDialog();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public ExitDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			Label = new JLabel("正在关闭相关服务，请稍等");
			Label.setHorizontalAlignment(SwingConstants.CENTER);
			Label.setFont(new Font("宋体", Font.PLAIN, 20));
			contentPanel.add(Label, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				cancelButton = new JButton("我知道了");
				cancelButton.setFont(new Font("宋体", Font.PLAIN, 20));
				cancelButton.setActionCommand("Cancel");
				cancelButton.setEnabled(false);
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						System.exit(0);						
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	
	public void closeSucess() 
	{
		Label.setText("已关闭，可以退出了");
		cancelButton.setEnabled(true);
	}

}
