package view.home;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dbHelper.DB;

import java.awt.Panel;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("serial")
public class LoginForm extends JFrame 
{
	static Connection connection;
	static PreparedStatement stmt;
	
	private JPanel contentPane;
	private JTextField tfUserName;
	private JPasswordField tfPassword;
	
	public static int userType = 0;
	
	public int loginStatus(String user, String password) 
	{
        String query = "Select * From administrators where username = ? and password = ?";
        String query2 = "Select * From employees where username = ? and password = ?";
        String query3 = "Select * From customers where username = ? and password = ?";


        connection = DB.getConnection();
        try {
            stmt = connection.prepareStatement(query);
            stmt.setString(1, user);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return 1;
        } 
        catch (SQLException e) 
        {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, e);
        }
        try 
        {
            stmt = connection.prepareStatement(query2);
            stmt.setString(1, user);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()) return 2;
        } 
        catch (SQLException e) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, e);
        }
        try 
        {
            stmt = connection.prepareStatement(query3);
            stmt.setString(1, user);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return 3;
        } catch (SQLException e) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, e);
            return 0;
        }
        return 0;

    }

	public LoginForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/assets/Icon_User.png")));
		setTitle("Login");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 519);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel panel = new Panel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 433, 480);
		contentPane.add(panel);
		panel.setLayout(null);
		
		Panel panel2 = new Panel();
		panel2.setBackground(new Color(0, 102, 255));
		panel2.setBounds(0, 0, 433, 66);
		panel.add(panel2);
		panel2.setLayout(null);
		
		JLabel lblHeader = new JLabel("Inventory Management System");
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Century Gothic", Font.BOLD, 24));
		lblHeader.setBounds(39, 11, 382, 44);
		panel2.add(lblHeader);
		
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setForeground(new Color(0, 102, 255));
		lblUserName.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblUserName.setBounds(77, 210, 88, 55);
		panel.add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(new Color(0, 102, 255));
		lblPassword.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblPassword.setBounds(77, 262, 75, 55);
		panel.add(lblPassword);
		
		tfUserName = new JTextField();
		tfUserName.setToolTipText("UserName");
		tfUserName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfUserName.setBounds(175, 226, 193, 25);
		panel.add(tfUserName);
		tfUserName.setColumns(10);
		
		tfPassword = new JPasswordField();
		tfPassword.setToolTipText("Password");
		tfPassword.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfPassword.setBounds(175, 278, 193, 25);
		panel.add(tfPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String userName = tfUserName.getText();
				String password = new String(tfPassword.getPassword());
				
				int login_achieved = loginStatus(userName, password);
				
				if (login_achieved!=0) {
		            switch (login_achieved) {
		            case 1:
		                AdminHomeForm newfrm = new AdminHomeForm();
		                newfrm.setLocationRelativeTo(null);
		                newfrm.setVisible(true);
		                userType = 1;
		                break;
		            case 2:
		                EmployeeHomeForm newfrm2 = new EmployeeHomeForm();
		                newfrm2.setLocationRelativeTo(null);
		                newfrm2.setVisible(true);
		                userType = 2;
		                break;
		            case 3:
		                CustomerHomeForm newfrm3 = new CustomerHomeForm();
		                newfrm3.setLocationRelativeTo(null);
		                newfrm3.setVisible(true);
		                userType = 3;
		                break;
		            default:
		                System.out.println("Wrong Data");

		            }
		            setVisible(false);
		        } 
				else 
		        {
					JOptionPane.showMessageDialog(null, "Wrong Username or Password!");
		        }
			}
		});
		
		btnLogin.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnLogin.setForeground(Color.WHITE);
		btnLogin.setBackground(Color.LIGHT_GRAY);
		btnLogin.setBounds(77, 336, 291, 37);
		panel.add(btnLogin);
		
		tfUserName.getDocument().addDocumentListener(new DocumentListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void insertUpdate(DocumentEvent e) 
			{
				if(!tfPassword.getText().equals(""))btnLogin.setBackground(new Color(0, 102, 255));
			}

			@Override
			public void removeUpdate(DocumentEvent e) 
			{
				if(tfUserName.getText().equals(""))btnLogin.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		
		tfPassword.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) 
			{
				if(!tfUserName.getText().equals(""))btnLogin.setBackground(new Color(0, 102, 255));
				else btnLogin.setBackground(Color.LIGHT_GRAY);
			}

			@SuppressWarnings("deprecation")
			@Override
			public void removeUpdate(DocumentEvent e) 
			{
				if(tfPassword.getText().equals(""))btnLogin.setBackground(Color.LIGHT_GRAY);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {}
		});
		
		JLabel lblSignUp = new JLabel("Create Account");
		lblSignUp.setForeground(new Color(0, 102, 255));
		lblSignUp.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblSignUp.setForeground(new Color(0, 204, 255));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lblSignUp.setForeground(new Color(0, 102, 255));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				SignUpForm signUp = new SignUpForm();
				signUp.setVisible(true);
				signUp.setLocationRelativeTo(null);
				setVisible(false);
			}
		});
		lblSignUp.setToolTipText("Create New Account");
		lblSignUp.setBounds(163, 438, 113, 31);
		panel.add(lblSignUp);
		
		JLabel lblLoginIcon = new JLabel("");
		lblLoginIcon.setIcon(new ImageIcon(LoginForm.class.getResource("/assets/Icon_User.png")));
		lblLoginIcon.setToolTipText("Icon");
		lblLoginIcon.setBounds(174, 98, 80, 80);
		panel.add(lblLoginIcon);
	}
}
