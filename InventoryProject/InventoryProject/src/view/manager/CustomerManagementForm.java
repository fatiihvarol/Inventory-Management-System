package view.manager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dbHelper.DB;
import entities.Customer;
import view.home.EmployeeHomeForm;

import java.awt.Panel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class CustomerManagementForm extends JFrame 
{
	static Connection connection;
	static Statement stmt;
	
	static ArrayList<Customer> customers = new ArrayList<>();
	private JTable jtCustomers;
	
	static DefaultTableModel myModel = new DefaultTableModel();
	Object[] columns = { "Id", "Name", "Surname", "Phone Number", "Email", "Address", "Username", "Password", "Age", "Gender", "User Type"};
	Object[] rows = new Object[columns.length];
	
	private JPanel contentPane;
	private JTextField tfAge_customerMgmt;
	private JTextField tfAddress_customerMgmt;
	private JTextField tfPhoneNum_customerMgmt;
	private JPasswordField jpPassword_customerMgmt;
	private JTextField tfEmail_customerMgmt;
	private JTextField tfusername_customerMgmt;
	private JTextField tfSurname_customerMgmt;
	private JTextField tfName_customerMgmt;
	
	@SuppressWarnings("rawtypes")
	private JComboBox cbGender_customerMgmt;
	private String tempUserName;
	
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

	private void bringCustomers() 
	{
		myModel.getDataVector().removeAllElements();
		readCustomers();
		
		if (customers.size() == 0) 
		{
			rows[0] = null;
			rows[1] = null;
			rows[2] = null;
			rows[3] = null;
			rows[4] = null;
			rows[5] = null;
			rows[6] = null;
			rows[7] = null;
			rows[8] = null;
			rows[9] = null;
			rows[10] = null;

			myModel.addRow(rows);
		}
		
		for (int i = 0; i < customers.size(); i++) 
		{
			rows[0] = customers.get(i).getID();
			rows[1] = customers.get(i).getFirstName();
			rows[2] = customers.get(i).getLastName();
			rows[3] = customers.get(i).getPhoneNumber();
			rows[4] = customers.get(i).getEmail();
			rows[5] = customers.get(i).getAddress();
			rows[6] = customers.get(i).getUserName();
			rows[7] = customers.get(i).getPassword();
			rows[8] = customers.get(i).getAge();
			//rows[10] = customers.get(i).getUserType();
			
			if (customers.get(i).getGender() == 1) rows[9] = "Male";
			else if(customers.get(i).getGender() == 2) rows[9] = "Female";
			else rows[9] = "Other";
			
			if (customers.get(i).getUserType() == 3) rows[10] = "Customer";

			myModel.addRow(rows);
		}
		
		jtCustomers.setModel(myModel);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomerManagementForm() 
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(CustomerManagementForm.class.getResource("/assets/Icon_User.png")));
		setTitle("Customer Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1199, 689);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel panel = new Panel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 1183, 650);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(436, 39, 723, 561);
		panel.add(scrollPane);
		
		jtCustomers = new JTable();
		myModel.setColumnIdentifiers(columns);
		jtCustomers.setBackground(SystemColor.controlHighlight);
		jtCustomers.setBounds(436, 39, 723, 561);
		scrollPane.setViewportView(jtCustomers);
		bringCustomers();
		
		JButton btnAddCustomer = new JButton("ADD");
		btnAddCustomer.addActionListener(new ActionListener() 
		{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) 
			{
				String name = null, surname = null, phone_number = null, email = null, address = null, user_name = null, password = null, age = null;
				int gender = 0;
				name = tfName_customerMgmt.getText();
				surname = tfSurname_customerMgmt.getText();
				phone_number = tfPhoneNum_customerMgmt.getText();
				email = tfEmail_customerMgmt.getText();
				address = tfAddress_customerMgmt.getText();
				user_name = tfusername_customerMgmt.getText();
				password = jpPassword_customerMgmt.getText();
				age = tfAge_customerMgmt.getText();
				gender = cbGender_customerMgmt.getSelectedIndex()+1;
				int userType = 3;

				int temp_id;
				
				if (customers.size() != 0) 
				{
					temp_id = customers.get(customers.size() - 1).getID() + 1;
				} 
				else temp_id = 1;
				
				String sql_query = "INSERT INTO customers(CustomerID,FirstName,LastName,PhoneNumber,Email,Address,UserName,Password,Age,Gender,UserType) VALUES ("
						+ temp_id + ",'" + name + "','" + surname + "','" + phone_number + "','" + email + "','"
						+ address + "','" + user_name + "','" + password + "','" + age + "'," + gender + "," + userType + ")";
				if (!name.equals("") && !surname.equals("") && !phone_number.equals("") && !email.equals("")
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
					
					bringCustomers();
					
					tfName_customerMgmt.setText("");
					tfSurname_customerMgmt.setText("");
					tfPhoneNum_customerMgmt.setText("");
					tfAddress_customerMgmt.setText("");
					tfAge_customerMgmt.setText("");
					cbGender_customerMgmt.setSelectedIndex(0);
					tfusername_customerMgmt.setText("");
					jpPassword_customerMgmt.setText("");
					tfEmail_customerMgmt.setText("");

				} 
				else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");
			}
		});
				
		btnAddCustomer.setForeground(Color.WHITE);
		btnAddCustomer.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnAddCustomer.setBackground(new Color(0, 102, 255));
		btnAddCustomer.setBounds(34, 502, 99, 37);
		panel.add(btnAddCustomer);
		
		
		
		JButton btnUpdateCustomer = new JButton("UPDATE");
		btnUpdateCustomer.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) 
			{
				String name = null, surname = null, phone_number = null, email = null, address = null, user_name = null, password = null, age = null;
				int gender = 0;
				int userType = 3;
				
				name = tfName_customerMgmt.getText();
				surname = tfSurname_customerMgmt.getText();
				phone_number = tfPhoneNum_customerMgmt.getText();
				email = tfEmail_customerMgmt.getText();
				address = tfAddress_customerMgmt.getText();
				user_name = tfusername_customerMgmt.getText();
				password = jpPassword_customerMgmt.getText();
				age = tfAge_customerMgmt.getText();
				gender = cbGender_customerMgmt.getSelectedIndex()+1;
				
				//UPDATE `inventorymanagementdb`.`customers` SET `PhoneNumber` = '12345678' WHERE (`CustomerID` = '2');

				String sql_query = "UPDATE customers SET FirstName='" + name + "'," + "LastName='"
						+ surname + "'," + "PhoneNumber='" + phone_number + "'," + "Email='" + email + "',"
						+ "Address='" + address + "'," + "UserName='" + user_name + "'," + "Password='" + password
						+ "'," + "Age='" + age + "'," + "Gender='" + gender + "'WHERE UserName='" + tempUserName + "'";

				if (!name.equals("") && !surname.equals("") && !phone_number.equals("") && !email.equals("") && !user_name.equals("") && !password.equals("") && !age.equals("")) 
				{
					for (int i = 0; i < customers.size(); i++) 
					{
						if (customers.get(i).getUserName().equals(tempUserName)) 
						{
							customers.set(i, new Customer(customers.get(i).getID(), name, surname, phone_number, email, address, user_name,
									password, age, gender, userType));
						}
					}

					bringCustomers();

					try 
					{
						stmt.execute(sql_query);
					} 
					catch (SQLException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Customer Updated !");
					
					tfName_customerMgmt.setText("");
					tfSurname_customerMgmt.setText("");
					tfPhoneNum_customerMgmt.setText("");
					tfAddress_customerMgmt.setText("");
					tfAge_customerMgmt.setText("");
					cbGender_customerMgmt.setSelectedIndex(0);
					tfusername_customerMgmt.setText("");
					jpPassword_customerMgmt.setText("");
					tfEmail_customerMgmt.setText("");

				} 
				else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");
			}
		});
		
		btnUpdateCustomer.setForeground(Color.WHITE);
		btnUpdateCustomer.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnUpdateCustomer.setBackground(new Color(0, 102, 255));
		btnUpdateCustomer.setBounds(135, 502, 99, 37);
		panel.add(btnUpdateCustomer);
		
		JButton btnDeleteCustomer = new JButton("DELETE");
		btnDeleteCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String tempUserName = tfusername_customerMgmt.getText();
				String query_delete = "delete from customers where UserName='" + tempUserName +"'";
				
				try 
				{
					stmt.execute(query_delete);
					JOptionPane.showMessageDialog(null, "Customer Deleted");
					
					for (int i = 0; i < customers.size(); i++) 
					{
						if (customers.get(i).getUserName().equals(tempUserName)) 
						{
							customers.remove(i);
						}
					}
					
					tfName_customerMgmt.setText("");
					tfSurname_customerMgmt.setText("");
					tfPhoneNum_customerMgmt.setText("");
					tfAddress_customerMgmt.setText("");
					tfAge_customerMgmt.setText("");
					cbGender_customerMgmt.setSelectedIndex(0);
					tfusername_customerMgmt.setText("");
					jpPassword_customerMgmt.setText("");
					tfEmail_customerMgmt.setText("");
					
					bringCustomers();
					
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		btnDeleteCustomer.setForeground(Color.WHITE);
		btnDeleteCustomer.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnDeleteCustomer.setBackground(new Color(0, 102, 255));
		btnDeleteCustomer.setBounds(235, 502, 99, 37);
		panel.add(btnDeleteCustomer);
		
		JButton btnGoBack_customerManagement = new JButton("");
		btnGoBack_customerManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EmployeeHomeForm empForm = new EmployeeHomeForm();
				empForm.setVisible(true);
				empForm.setLocationRelativeTo(null);
				setVisible(false);
			}
		});
		
		btnGoBack_customerManagement.setIcon(new ImageIcon(CustomerManagementForm.class.getResource("/assets/Icon_Goback.png")));
		btnGoBack_customerManagement.setForeground(Color.WHITE);
		btnGoBack_customerManagement.setBackground(new Color(0, 102, 255));
		btnGoBack_customerManagement.setBounds(344, 502, 58, 37);
		panel.add(btnGoBack_customerManagement);
		
		JLabel lblGender_customerMgmt = new JLabel("Gender:");
		lblGender_customerMgmt.setForeground(new Color(0, 102, 255));
		lblGender_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblGender_customerMgmt.setBounds(34, 433, 63, 30);
		panel.add(lblGender_customerMgmt);
		
		JLabel lblAge_customerMgmt = new JLabel("Age:");
		lblAge_customerMgmt.setForeground(new Color(0, 102, 255));
		lblAge_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblAge_customerMgmt.setBounds(34, 392, 37, 30);
		panel.add(lblAge_customerMgmt);
		
		tfAge_customerMgmt = new JTextField();
		tfAge_customerMgmt.setToolTipText("");
		tfAge_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfAge_customerMgmt.setColumns(10);
		tfAge_customerMgmt.setBounds(209, 397, 193, 25);
		panel.add(tfAge_customerMgmt);
		
		tfAddress_customerMgmt = new JTextField();
		tfAddress_customerMgmt.setToolTipText("");
		tfAddress_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfAddress_customerMgmt.setColumns(10);
		tfAddress_customerMgmt.setBounds(209, 356, 193, 25);
		panel.add(tfAddress_customerMgmt);
		
		JLabel lblAddress_customerMgmt = new JLabel("Address:");
		lblAddress_customerMgmt.setForeground(new Color(0, 102, 255));
		lblAddress_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblAddress_customerMgmt.setBounds(34, 347, 88, 30);
		panel.add(lblAddress_customerMgmt);
		
		JLabel lblPhoneNumber_customerMgmt = new JLabel("Phone Number:");
		lblPhoneNumber_customerMgmt.setForeground(new Color(0, 102, 255));
		lblPhoneNumber_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblPhoneNumber_customerMgmt.setBounds(34, 306, 121, 30);
		panel.add(lblPhoneNumber_customerMgmt);
		
		tfPhoneNum_customerMgmt = new JTextField();
		tfPhoneNum_customerMgmt.setToolTipText("");
		tfPhoneNum_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfPhoneNum_customerMgmt.setColumns(10);
		tfPhoneNum_customerMgmt.setBounds(209, 315, 193, 25);
		panel.add(tfPhoneNum_customerMgmt);
		
		JLabel lblPassword_customerMgmt = new JLabel("Password:");
		lblPassword_customerMgmt.setForeground(new Color(0, 102, 255));
		lblPassword_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblPassword_customerMgmt.setBounds(34, 265, 88, 30);
		panel.add(lblPassword_customerMgmt);
		
		jpPassword_customerMgmt = new JPasswordField();
		jpPassword_customerMgmt.setBounds(209, 273, 193, 25);
		panel.add(jpPassword_customerMgmt);
		
		tfEmail_customerMgmt = new JTextField();
		tfEmail_customerMgmt.setToolTipText("");
		tfEmail_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfEmail_customerMgmt.setColumns(10);
		tfEmail_customerMgmt.setBounds(209, 233, 193, 25);
		panel.add(tfEmail_customerMgmt);
		
		JLabel lblEmail_customerMgmt = new JLabel("Email:");
		lblEmail_customerMgmt.setForeground(new Color(0, 102, 255));
		lblEmail_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblEmail_customerMgmt.setBounds(34, 224, 47, 30);
		panel.add(lblEmail_customerMgmt);
		
		JLabel lblUserName_customerMgmt = new JLabel("User Name:");
		lblUserName_customerMgmt.setForeground(new Color(0, 102, 255));
		lblUserName_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblUserName_customerMgmt.setBounds(34, 183, 88, 30);
		panel.add(lblUserName_customerMgmt);
		
		tfusername_customerMgmt = new JTextField();
		tfusername_customerMgmt.setToolTipText("");
		tfusername_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfusername_customerMgmt.setColumns(10);
		tfusername_customerMgmt.setBounds(209, 192, 193, 25);
		panel.add(tfusername_customerMgmt);
		
		tfSurname_customerMgmt = new JTextField();
		tfSurname_customerMgmt.setToolTipText("");
		tfSurname_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfSurname_customerMgmt.setColumns(10);
		tfSurname_customerMgmt.setBounds(209, 151, 193, 25);
		panel.add(tfSurname_customerMgmt);
		
		JLabel lblSurname_customerMgmt = new JLabel("Surname:");
		lblSurname_customerMgmt.setForeground(new Color(0, 102, 255));
		lblSurname_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblSurname_customerMgmt.setBounds(34, 142, 74, 30);
		panel.add(lblSurname_customerMgmt);
		
		JLabel lblName_customerMgmt = new JLabel("Name:");
		lblName_customerMgmt.setForeground(new Color(0, 102, 255));
		lblName_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblName_customerMgmt.setBounds(34, 101, 53, 30);
		panel.add(lblName_customerMgmt);
		
		tfName_customerMgmt = new JTextField();
		tfName_customerMgmt.setToolTipText("");
		tfName_customerMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfName_customerMgmt.setColumns(10);
		tfName_customerMgmt.setBounds(209, 110, 193, 25);
		panel.add(tfName_customerMgmt);
		
		
		
		cbGender_customerMgmt = new JComboBox();
		cbGender_customerMgmt.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female", "Other"}));
		cbGender_customerMgmt.setBounds(209, 438, 193, 25);
		panel.add(cbGender_customerMgmt);
		
		
		
		jtCustomers.addMouseListener(new MouseAdapter() { // TableOnClick
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int gender = 0;

				if (myModel.getValueAt(jtCustomers.getSelectedRow(), 9).toString().equals("Male"))
				{
					gender = 1;
				} 
				else if (myModel.getValueAt(jtCustomers.getSelectedRow(), 9).toString().equals("Female"))
				{
					gender = 2;
				}
				else gender = 3;

				tfName_customerMgmt.setText(myModel.getValueAt(jtCustomers.getSelectedRow(), 1).toString());
				tfSurname_customerMgmt.setText(myModel.getValueAt(jtCustomers.getSelectedRow(), 2).toString());
				tfPhoneNum_customerMgmt.setText(myModel.getValueAt(jtCustomers.getSelectedRow(), 3).toString());
				tfEmail_customerMgmt.setText(myModel.getValueAt(jtCustomers.getSelectedRow(), 4).toString());
				tfAddress_customerMgmt.setText(myModel.getValueAt(jtCustomers.getSelectedRow(), 5).toString());
				tfusername_customerMgmt.setText(myModel.getValueAt(jtCustomers.getSelectedRow(), 6).toString());
				jpPassword_customerMgmt.setText(myModel.getValueAt(jtCustomers.getSelectedRow(), 7).toString());
				tfAge_customerMgmt.setText(myModel.getValueAt(jtCustomers.getSelectedRow(), 8).toString());
				cbGender_customerMgmt.setSelectedIndex(gender - 1);
				tempUserName = tfusername_customerMgmt.getText();
			}
		});
	}
}
