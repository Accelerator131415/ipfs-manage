package application.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlockChainDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BlockChainDialog dialog = new BlockChainDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BlockChainDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.LIGHT_GRAY);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JLabel titleLabel = new JLabel("警告");
			titleLabel.setBackground(Color.LIGHT_GRAY);
			titleLabel.setFont(new Font("宋体", Font.PLAIN, 40));
			titleLabel.setForeground(Color.YELLOW);
			titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(titleLabel, BorderLayout.NORTH);
		}
		{
			JLabel Label = new JLabel("区块链连接异常，请检查密钥文件路径和密码");
			Label.setForeground(Color.YELLOW);
			Label.setFont(new Font("宋体", Font.PLAIN, 20));
			Label.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(Label, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(Color.LIGHT_GRAY);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("我知道了");
				cancelButton.setFont(new Font("宋体", Font.PLAIN, 20));
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}

}
