package view.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Splash extends JFrame 
{
	private JPanel contentPane;

	public Splash() throws InterruptedException 
	{
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 713, 411);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel splashPanel = new Panel();
		splashPanel.setBackground(Color.WHITE);
		splashPanel.setBounds(0, 0, 713, 411);
		contentPane.add(splashPanel);
		splashPanel.setLayout(null);
		
		JLabel lblHeader = new JLabel("Inventory Management System");
		lblHeader.setForeground(new Color(0, 102, 255));
		lblHeader.setFont(new Font("Century Gothic", Font.BOLD, 27));
		lblHeader.setBounds(178, 171, 451, 44);
		splashPanel.add(lblHeader);
		
		JProgressBar pbInitialStatus = new JProgressBar();
		pbInitialStatus.setBounds(0, 403, 713, 8);
		splashPanel.add(pbInitialStatus);
		
		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(Splash.class.getResource("/assets/Icon_DB.png")));
		lblIcon.setBounds(111, 170, 50, 50);
		splashPanel.add(lblIcon);
	}
}
