package view.home;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dbHelper.DB;
import entities.Customer;

import java.awt.Panel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class SignUpForm extends JFrame {
	static Connection connection;
	static Statement stmt;
	
	static ArrayList<Customer> customers = new ArrayList<>();

	private JPanel contentPane;
	private JTextField tfName_SignUp;
	private JTextField tfSurname_SignUp;
	private JTextField tfEmail_SignUp;
	private JTextField tfPhoneNum_SignUp;
	private JTextField tfAddress_SignUp;
	private JTextField tfUsername_SignUp;
	private JTextField tfAge_SignUp;
	private JPasswordField pfPassword_SignUp;
	
	private void readCustomers() 
	{
		boolean flag;
		
		try 
		{
			connection = DB.getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM customers");

			while (rs.next()) 
			{
				Customer customer = new Customer
				(
					rs.getInt(1), 
					rs.getString(2), 
					rs.getString(3), 
					rs.getString(4),
					rs.getString(5), 
					rs.getString(6), 
					rs.getString(7), 
					rs.getString(8), 
					rs.getString(9),
					rs.getInt(10), 
					rs.getInt(11)
				);

				flag = true;
				
				for (int i = 0; i < customers.size(); i++) 
				{
					if (customer.getID() == customers.get(i).getID()) flag = false;
				}
				if (flag) customers.add(customer);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SignUpForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignUpForm.class.getResource("/assets/Icon_User.png")));
		setTitle("Sign Up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 530, 675);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel panel = new Panel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 514, 636);
		contentPane.add(panel);
		panel.setLayout(null);
		
		Panel panel2 = new Panel();
		panel2.setLayout(null);
		panel2.setBackground(new Color(0, 102, 255));
		panel2.setBounds(0, 0, 514, 66);
		panel.add(panel2);
		
		JLabel lblHeader = new JLabel("Inventory Management System");
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Century Gothic", Font.BOLD, 24));
		lblHeader.setBounds(69, 11, 382, 44);
		panel2.add(lblHeader);
		
		readCustomers();
		
		JLabel lblTurnBack = new JLabel("");
		lblTurnBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginForm login = new LoginForm();
				login.setVisible(true);
				login.setLocationRelativeTo(null);
				setVisible(false);
			}
		});
		lblTurnBack.setBounds(10, 15, 40, 40);
		panel2.add(lblTurnBack);
		lblTurnBack.setIcon(new ImageIcon(SignUpForm.class.getResource("/assets/Icon_Goback.png")));
		
		JLabel lblName = new JLabel("Name:");
		lblName.setForeground(new Color(0, 102, 255));
		lblName.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblName.setBounds(70, 133, 53, 30);
		panel.add(lblName);
		
		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setForeground(new Color(0, 102, 255));
		lblSurname.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblSurname.setBounds(70, 174, 74, 30);
		panel.add(lblSurname);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setForeground(new Color(0, 102, 255));
		lblPhoneNumber.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblPhoneNumber.setBounds(70, 256, 121, 30);
		panel.add(lblPhoneNumber);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setForeground(new Color(0, 102, 255));
		lblEmail.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblEmail.setBounds(70, 215, 47, 30);
		panel.add(lblEmail);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setForeground(new Color(0, 102, 255));
		lblAddress.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblAddress.setBounds(70, 297, 88, 30);
		panel.add(lblAddress);
		
		JLabel lblUserName_SignUp = new JLabel("User Name:");
		lblUserName_SignUp.setForeground(new Color(0, 102, 255));
		lblUserName_SignUp.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblUserName_SignUp.setBounds(70, 338, 88, 30);
		panel.add(lblUserName_SignUp);
		
		JLabel lblPassword_SignUp = new JLabel("Password:");
		lblPassword_SignUp.setForeground(new Color(0, 102, 255));
		lblPassword_SignUp.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblPassword_SignUp.setBounds(70, 379, 88, 30);
		panel.add(lblPassword_SignUp);
		
		JLabel lblAge = new JLabel("Age:");
		lblAge.setForeground(new Color(0, 102, 255));
		lblAge.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblAge.setBounds(70, 420, 37, 30);
		panel.add(lblAge);
		
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setForeground(new Color(0, 102, 255));
		lblGender.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblGender.setBounds(70, 461, 63, 30);
		panel.add(lblGender);
		
		tfName_SignUp = new JTextField();
		tfName_SignUp.setToolTipText("");
		tfName_SignUp.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfName_SignUp.setColumns(10);
		tfName_SignUp.setBounds(245, 142, 193, 25);
		panel.add(tfName_SignUp);
		
		tfSurname_SignUp = new JTextField();
		tfSurname_SignUp.setToolTipText("");
		tfSurname_SignUp.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfSurname_SignUp.setColumns(10);
		tfSurname_SignUp.setBounds(245, 183, 193, 25);
		panel.add(tfSurname_SignUp);
		
		tfEmail_SignUp = new JTextField();
		tfEmail_SignUp.setToolTipText("");
		tfEmail_SignUp.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfEmail_SignUp.setColumns(10);
		tfEmail_SignUp.setBounds(245, 224, 193, 25);
		panel.add(tfEmail_SignUp);
		
		tfPhoneNum_SignUp = new JTextField();
		tfPhoneNum_SignUp.setToolTipText("");
		tfPhoneNum_SignUp.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfPhoneNum_SignUp.setColumns(10);
		tfPhoneNum_SignUp.setBounds(245, 265, 193, 25);
		panel.add(tfPhoneNum_SignUp);
		
		tfAddress_SignUp = new JTextField();
		tfAddress_SignUp.setToolTipText("");
		tfAddress_SignUp.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfAddress_SignUp.setColumns(10);
		tfAddress_SignUp.setBounds(245, 306, 193, 25);
		panel.add(tfAddress_SignUp);
		
		tfUsername_SignUp = new JTextField();
		tfUsername_SignUp.setToolTipText("");
		tfUsername_SignUp.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfUsername_SignUp.setColumns(10);
		tfUsername_SignUp.setBounds(245, 347, 193, 25);
		panel.add(tfUsername_SignUp);
		
		tfAge_SignUp = new JTextField();
		tfAge_SignUp.setToolTipText("");
		tfAge_SignUp.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfAge_SignUp.setColumns(10);
		tfAge_SignUp.setBounds(245, 425, 193, 25);
		panel.add(tfAge_SignUp);
		
		JComboBox cbGender_signUp = new JComboBox();
		cbGender_signUp.setForeground(new Color(0, 102, 255));
		cbGender_signUp.setFont(new Font("Century Gothic", Font.BOLD, 14));
		cbGender_signUp.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female", "Other"}));
		cbGender_signUp.setBounds(245, 464, 193, 30);
		panel.add(cbGender_signUp);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String name = null, surname = null, phone_number = null, email = null, address = null, user_name = null, password = null, age = null;
				int gender = 0;
				name = tfName_SignUp.getText();
				surname = tfSurname_SignUp.getText();
				phone_number = tfPhoneNum_SignUp.getText();
				email = tfEmail_SignUp.getText();
				address = tfAddress_SignUp.getText();
				user_name = tfUsername_SignUp.getText();
				password = pfPassword_SignUp.getText();
				age = tfAge_SignUp.getText();
				gender = cbGender_signUp.getSelectedIndex()+1;
				
				int userType = 3;

				int temp_id;
				
				if (customers.size() != 0) 
				{
					temp_id = customers.get(customers.size() - 1).getID() + 1;
				} 
				else temp_id = 1;
				
				boolean userNameExists = false;
				boolean emailExists = false;
				
				for (int i = 0; i < customers.size(); i++) 
				{
					if (customers.get(i).getUserName().equals(user_name)) 
					{
						userNameExists = true;
					}
					else if (customers.get(i).getEmail().equals(email)) 
					{
						emailExists = true;
					}
				}
				
				String sql_query = "INSERT INTO customers(CustomerID,FirstName,LastName,PhoneNumber,Email,Address,UserName,Password,Age,Gender,UserType) VALUES ("
						+ temp_id + ",'" + name + "','" + surname + "','" + phone_number + "','" + email + "','"
						+ address + "','" + user_name + "','" + password + "','" + age + "'," + gender + "," + userType + ")";
				
				if (userNameExists==false && emailExists==false && !name.equals("") && !surname.equals("") && !phone_number.equals("") && !email.equals("")
						&& !user_name.equals("") && !password.equals("") && !age.equals("")) 
				{
					try 
					{
						stmt.execute(sql_query);
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, "Customer Added");
					
					tfName_SignUp.setText("");
					tfSurname_SignUp.setText("");
					tfPhoneNum_SignUp.setText("");
					tfAddress_SignUp.setText("");
					tfAge_SignUp.setText("");
					cbGender_signUp.setSelectedIndex(0);
					tfUsername_SignUp.setText("");
					pfPassword_SignUp.setText("");
					tfEmail_SignUp.setText("");

				} 
				else if (userNameExists==true) 
				{
					JOptionPane.showMessageDialog(null, "This username has taken, please enter a different user name!");
				}
				else if (emailExists==true) 
				{
					JOptionPane.showMessageDialog(null, "This email has taken, please enter a different email!");
				}
				else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");
			}
		});
		btnSignUp.setForeground(Color.WHITE);
		btnSignUp.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnSignUp.setBackground(new Color(0, 102, 255));
		btnSignUp.setBounds(70, 542, 368, 37);
		panel.add(btnSignUp);
		
		pfPassword_SignUp = new JPasswordField();
		pfPassword_SignUp.setBounds(245, 387, 193, 25);
		panel.add(pfPassword_SignUp);
	}
}
