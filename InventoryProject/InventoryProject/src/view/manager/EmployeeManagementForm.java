package view.manager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dbHelper.DB;
import entities.Employee;
import view.home.AdminHomeForm;

import java.awt.Panel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class EmployeeManagementForm extends JFrame 
{
	static Connection connection;
	static Statement stmt;
	
	static ArrayList<Employee> employees = new ArrayList<>();
	private JTable jtEmployees;
	
	static DefaultTableModel myModel = new DefaultTableModel();
	Object[] columns = { "Id", "User Type", "Name", "Surname", "Username", "Email", "Password", "Phone Number", "Address", "Age", "Gender", "Position", "Salary"};
	Object[] rows = new Object[columns.length];

	private JPanel contentPane;
	
	private JTextField tfName_empMgmt;
	private JTextField tfSurname_empMgmt;
	private JTextField tfusername_empMgmt;
	private JTextField tfEmail_empMgmt;
	private JPasswordField jpPassword_empMgmt;
	private JPasswordField jpConfirmPassword_empMgmt;
	private JTextField tfPhoneNum_empMgmt;
	private JTextField tfAddress_empMgmt;
	private JTextField tfAge_empMgmt;
	private JTextField tfPosition;
	private JTextField tfSalary;
	
	private String tempUserName;
	
	private void readEmployees() {
		boolean flag;
		try {
			connection = DB.getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM employees");

			while (rs.next()) {
				Employee employee = new Employee(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getInt(10), rs.getInt(11), rs.getString(12), rs.getString(13));

				flag = true;
				for (int i = 0; i < employees.size(); i++) {
					if (employee.getID() == employees.get(i).getID())
						flag = false;
				}
				if (flag)
					employees.add(employee);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bringEmployees() 
	{
		myModel.getDataVector().removeAllElements();
		readEmployees();
		
		if (employees.size() == 0) 
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
			rows[11] = null;
			rows[12] = null;

			myModel.addRow(rows);
		}
		for (int i = 0; i < employees.size(); i++) 
		{
			rows[0] = employees.get(i).getID();
			if(employees.get(i).getUserType()==2) rows[1]="Employee";
			rows[2] = employees.get(i).getFirstName();
			rows[3] = employees.get(i).getLastName();
			rows[4] = employees.get(i).getUserName();
			rows[5] = employees.get(i).getEmail();
			rows[6] = employees.get(i).getPassword();
			rows[7] = employees.get(i).getPhoneNumber();
			rows[8] = employees.get(i).getAddress();
			rows[9] = employees.get(i).getAge();
			
			if (employees.get(i).getGender() == 1) rows[10] = "Male";
			else if(employees.get(i).getGender() == 2) rows[10] = "Female";
			else rows[10] = "Other";
			
			rows[11] = employees.get(i).getPosition();
			rows[12] = employees.get(i).getSalary();
			
			myModel.addRow(rows);
		}
		
		jtEmployees.setModel(myModel);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public EmployeeManagementForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(EmployeeManagementForm.class.getResource("/assets/Icon_User.png")));
		setTitle("Employee Management");
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
		
		jtEmployees = new JTable();
		myModel.setColumnIdentifiers(columns);
		jtEmployees.setBackground(SystemColor.controlHighlight);
		jtEmployees.setBounds(436, 39, 723, 561);
		scrollPane.setViewportView(jtEmployees);
		bringEmployees();
		
		JComboBox cbGender_empMgmt = new JComboBox();
		cbGender_empMgmt.setModel(new DefaultComboBoxModel(new String[] {"Male", "Female", "Other"}));
		cbGender_empMgmt.setBounds(209, 417, 193, 25);
		panel.add(cbGender_empMgmt);
		
		JButton btnAddEmployee = new JButton("ADD");
		btnAddEmployee.addActionListener(new ActionListener() 
		{
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) 
			{				
				String name = null, surname = null, phone_number = null, email = null, address = null, user_name = null, password = null, salary = null, position = null;
				int userType = 2, age = 0, gender = 0;

				name = tfName_empMgmt.getText();
				surname = tfSurname_empMgmt.getText();
				user_name = tfusername_empMgmt.getText();
				email = tfEmail_empMgmt.getText();
				password = jpPassword_empMgmt.getText();
				//jpConfirmPassword_empMgmt.getText();
				phone_number = tfPhoneNum_empMgmt.getText();
				address = tfAddress_empMgmt.getText();
				age = Integer.parseInt(tfAge_empMgmt.getText());
				gender = cbGender_empMgmt.getSelectedIndex()+1;
				position = tfPosition.getText();
				salary = tfSalary.getText();

				int temp_id;
				
				if (employees.size() != 0) 
				{
					temp_id = employees.get(employees.size() - 1).getID() + 1;
				} 
				else temp_id = 1;

				String sql_query = null;

				sql_query = "INSERT INTO employees(EmployeeID,UserType,FirstName,LastName,UserName,Email,Password,PhoneNumber,Address,Age,Gender,Position,Salary) VALUES ("
						+ temp_id + "," + userType + ",'" + name + "','" + surname + "','" + user_name + "','"
						+ email + "','" + password + "','" + phone_number + "','" + address + "'," + age + ","
						+ gender + ",'" + position + "','" + salary + "')";
				
				if (!name.equals("") && !surname.equals("") && !phone_number.equals("") && !email.equals("") && !user_name.equals("") && !password.equals("")) 
				{
					try 
					{
						stmt = connection.createStatement();
						stmt.execute(sql_query);
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, "Employee Added");
					bringEmployees();
					
					tfName_empMgmt.setText("");
					tfSurname_empMgmt.setText("");
					tfusername_empMgmt.setText("");
					tfEmail_empMgmt.setText("");
					jpPassword_empMgmt.setText("");
					jpConfirmPassword_empMgmt.setText("");
					tfPhoneNum_empMgmt.setText("");
					tfAddress_empMgmt.setText("");
					tfAge_empMgmt.setText("");
					cbGender_empMgmt.setSelectedIndex(0);
					tfPosition.setText("");
					tfSalary.setText("");

				} 
				else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");
			}
		});
		
		btnAddEmployee.setForeground(Color.WHITE);
		btnAddEmployee.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnAddEmployee.setBackground(new Color(0, 102, 255));
		btnAddEmployee.setBounds(34, 566, 99, 37);
		panel.add(btnAddEmployee);
		
		JButton btnUpdateEmployee = new JButton("UPDATE");
		btnUpdateEmployee.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) 
			{
				String name = null, surname = null, phone_number = null, email = null, address = null, user_name = null, password = null, salary = null, position = null;
				int userType = 2, age = 0, gender = 0;

				name = tfName_empMgmt.getText();
				surname = tfSurname_empMgmt.getText();
				user_name = tfusername_empMgmt.getText();
				email = tfEmail_empMgmt.getText();
				password = jpPassword_empMgmt.getText();
				//jpConfirmPassword_empMgmt.getText();
				phone_number = tfPhoneNum_empMgmt.getText();
				address = tfAddress_empMgmt.getText();
				age = Integer.parseInt(tfAge_empMgmt.getText());
				gender = cbGender_empMgmt.getSelectedIndex()+1;
				position = tfPosition.getText();
				salary = tfSalary.getText();

				String sql_query = null;
				
				sql_query = "UPDATE employees SET UserType=" + userType + "," + "FirstName='"
						+ name + "'," + "LastName='" + surname + "'," + "UserName='" + user_name + "'," + "Email='"
						+ email + "'," + "Password='" + password + "'," + "PhoneNumber='" + phone_number + "',"
						+ "Address='" + address + "'," + "Age='" + age + "'," + "Gender=" + gender + "," + "Position='"
						+ position + "'," + "Salary='" + salary + "'" + "WHERE UserName='" + tempUserName + "'";
				
				System.out.println(sql_query);

				if (!name.equals("") && !surname.equals("") && !phone_number.equals("") && !email.equals("")
						&& !user_name.equals("") && !password.equals("")) {

					for (int i = 0; i < employees.size(); i++) 
					{
						if (employees.get(i).getUserName().equals(tempUserName)) 
						{
							employees.set(i, new Employee(employees.get(i).getID(), userType, name, surname, user_name, email, password,
									phone_number, address, age, gender, position, salary));
						}
					}

					bringEmployees();

					try 
					{
						stmt.execute(sql_query);
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, "Employee Updated !");
					tfName_empMgmt.setText("");
					tfSurname_empMgmt.setText("");
					tfusername_empMgmt.setText("");
					tfEmail_empMgmt.setText("");
					jpPassword_empMgmt.setText("");
					jpConfirmPassword_empMgmt.setText("");
					tfPhoneNum_empMgmt.setText("");
					tfAddress_empMgmt.setText("");
					tfAge_empMgmt.setText("");
					cbGender_empMgmt.setSelectedIndex(0);
					tfPosition.setText("");
					tfSalary.setText("");
				} 
				else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");
			}
		});
		btnUpdateEmployee.setForeground(Color.WHITE);
		btnUpdateEmployee.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnUpdateEmployee.setBackground(new Color(0, 102, 255));
		btnUpdateEmployee.setBounds(135, 566, 99, 37);
		panel.add(btnUpdateEmployee);
		
		JButton btnDeleteEmployee = new JButton("DELETE");
		btnDeleteEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String tempUserName = tfusername_empMgmt.getText();
				String query_delete = "delete from employees where UserName='" + tempUserName +"'";
				
				try 
				{
					stmt.execute(query_delete);
					JOptionPane.showMessageDialog(null, "Employee Deleted");
					
					for (int i = 0; i < employees.size(); i++) 
					{
						if (employees.get(i).getUserName().equals(tempUserName)) 
						{
							employees.remove(i);
						}
					}
					
					tfName_empMgmt.setText("");
					tfSurname_empMgmt.setText("");
					tfusername_empMgmt.setText("");
					tfEmail_empMgmt.setText("");
					jpPassword_empMgmt.setText("");
					jpConfirmPassword_empMgmt.setText("");
					tfPhoneNum_empMgmt.setText("");
					tfAddress_empMgmt.setText("");
					tfAge_empMgmt.setText("");
					cbGender_empMgmt.setSelectedIndex(0);
					tfPosition.setText("");
					tfSalary.setText("");
					
					bringEmployees();
					
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		btnDeleteEmployee.setForeground(Color.WHITE);
		btnDeleteEmployee.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnDeleteEmployee.setBackground(new Color(0, 102, 255));
		btnDeleteEmployee.setBounds(235, 566, 99, 37);
		panel.add(btnDeleteEmployee);
		
		JButton btnGoBack_empMgmt = new JButton("");
		btnGoBack_empMgmt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminHomeForm empForm = new AdminHomeForm();
				empForm.setVisible(true);
				empForm.setLocationRelativeTo(null);
				setVisible(false);
			}
		});
		btnGoBack_empMgmt.setIcon(new ImageIcon(EmployeeManagementForm.class.getResource("/assets/Icon_Goback.png")));
		btnGoBack_empMgmt.setForeground(Color.WHITE);
		btnGoBack_empMgmt.setBackground(new Color(0, 102, 255));
		btnGoBack_empMgmt.setBounds(344, 566, 58, 37);
		panel.add(btnGoBack_empMgmt);
		
		JLabel lblPosition_empMgmt = new JLabel("Position:");
		lblPosition_empMgmt.setForeground(new Color(0, 102, 255));
		lblPosition_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblPosition_empMgmt.setBounds(34, 453, 63, 30);
		panel.add(lblPosition_empMgmt);
		
		tfPosition = new JTextField();
		tfPosition.setToolTipText("");
		tfPosition.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfPosition.setColumns(10);
		tfPosition.setBounds(209, 458, 193, 25);
		panel.add(tfPosition);
		
		JLabel lblGender_empMgmt = new JLabel("Gender:");
		lblGender_empMgmt.setForeground(new Color(0, 102, 255));
		lblGender_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblGender_empMgmt.setBounds(34, 412, 63, 30);
		panel.add(lblGender_empMgmt);
		
		JLabel lblAge_empMgmt = new JLabel("Age:");
		lblAge_empMgmt.setForeground(new Color(0, 102, 255));
		lblAge_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblAge_empMgmt.setBounds(34, 371, 37, 30);
		panel.add(lblAge_empMgmt);
		
		tfAge_empMgmt = new JTextField();
		tfAge_empMgmt.setToolTipText("");
		tfAge_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfAge_empMgmt.setColumns(10);
		tfAge_empMgmt.setBounds(209, 376, 193, 25);
		panel.add(tfAge_empMgmt);
		
		tfAddress_empMgmt = new JTextField();
		tfAddress_empMgmt.setToolTipText("");
		tfAddress_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfAddress_empMgmt.setColumns(10);
		tfAddress_empMgmt.setBounds(209, 335, 193, 25);
		panel.add(tfAddress_empMgmt);
		
		JLabel lblAddress_empMgmt = new JLabel("Address:");
		lblAddress_empMgmt.setForeground(new Color(0, 102, 255));
		lblAddress_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblAddress_empMgmt.setBounds(34, 326, 88, 30);
		panel.add(lblAddress_empMgmt);
		
		JLabel lblPhoneNumber_empMgmt = new JLabel("Phone Number:");
		lblPhoneNumber_empMgmt.setForeground(new Color(0, 102, 255));
		lblPhoneNumber_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblPhoneNumber_empMgmt.setBounds(34, 285, 121, 30);
		panel.add(lblPhoneNumber_empMgmt);
		
		tfPhoneNum_empMgmt = new JTextField();
		tfPhoneNum_empMgmt.setToolTipText("");
		tfPhoneNum_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfPhoneNum_empMgmt.setColumns(10);
		tfPhoneNum_empMgmt.setBounds(209, 294, 193, 25);
		panel.add(tfPhoneNum_empMgmt);
		
		jpConfirmPassword_empMgmt = new JPasswordField();
		jpConfirmPassword_empMgmt.setBounds(209, 250, 193, 25);
		panel.add(jpConfirmPassword_empMgmt);
		
		JLabel lblConfirmPassword_empMgmt = new JLabel("Confirm Password:");
		lblConfirmPassword_empMgmt.setForeground(new Color(0, 102, 255));
		lblConfirmPassword_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblConfirmPassword_empMgmt.setBounds(34, 244, 145, 30);
		panel.add(lblConfirmPassword_empMgmt);
		
		JLabel lblPassword_empMgmt = new JLabel("Password:");
		lblPassword_empMgmt.setForeground(new Color(0, 102, 255));
		lblPassword_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblPassword_empMgmt.setBounds(34, 203, 88, 30);
		panel.add(lblPassword_empMgmt);
		
		jpPassword_empMgmt = new JPasswordField();
		jpPassword_empMgmt.setBounds(209, 211, 193, 25);
		panel.add(jpPassword_empMgmt);
		
		tfEmail_empMgmt = new JTextField();
		tfEmail_empMgmt.setToolTipText("");
		tfEmail_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfEmail_empMgmt.setColumns(10);
		tfEmail_empMgmt.setBounds(209, 171, 193, 25);
		panel.add(tfEmail_empMgmt);
		
		JLabel lblEmail_empMgmt = new JLabel("Email:");
		lblEmail_empMgmt.setForeground(new Color(0, 102, 255));
		lblEmail_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblEmail_empMgmt.setBounds(34, 162, 47, 30);
		panel.add(lblEmail_empMgmt);
		
		JLabel lblUserName_empMgmt = new JLabel("User Name:");
		lblUserName_empMgmt.setForeground(new Color(0, 102, 255));
		lblUserName_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblUserName_empMgmt.setBounds(34, 121, 88, 30);
		panel.add(lblUserName_empMgmt);
		
		tfusername_empMgmt = new JTextField();
		tfusername_empMgmt.setToolTipText("");
		tfusername_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfusername_empMgmt.setColumns(10);
		tfusername_empMgmt.setBounds(209, 130, 193, 25);
		panel.add(tfusername_empMgmt);
		
		tfSurname_empMgmt = new JTextField();
		tfSurname_empMgmt.setToolTipText("");
		tfSurname_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfSurname_empMgmt.setColumns(10);
		tfSurname_empMgmt.setBounds(209, 89, 193, 25);
		panel.add(tfSurname_empMgmt);
		
		JLabel lblSurname_empMgmt = new JLabel("Surname:");
		lblSurname_empMgmt.setForeground(new Color(0, 102, 255));
		lblSurname_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblSurname_empMgmt.setBounds(34, 80, 74, 30);
		panel.add(lblSurname_empMgmt);
		
		JLabel lblName_empMgmt = new JLabel("Name:");
		lblName_empMgmt.setForeground(new Color(0, 102, 255));
		lblName_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblName_empMgmt.setBounds(34, 39, 53, 30);
		panel.add(lblName_empMgmt);
		
		tfName_empMgmt = new JTextField();
		tfName_empMgmt.setToolTipText("");
		tfName_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfName_empMgmt.setColumns(10);
		tfName_empMgmt.setBounds(209, 48, 193, 25);
		panel.add(tfName_empMgmt);
		
		JLabel lblSalary_empMgmt = new JLabel("Salary:");
		lblSalary_empMgmt.setForeground(new Color(0, 102, 255));
		lblSalary_empMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblSalary_empMgmt.setBounds(34, 494, 63, 30);
		panel.add(lblSalary_empMgmt);
		
		tfSalary = new JTextField();
		tfSalary.setToolTipText("");
		tfSalary.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfSalary.setColumns(10);
		tfSalary.setBounds(209, 499, 193, 25);
		panel.add(tfSalary);
		
		jtEmployees.addMouseListener(new MouseAdapter() { // TableOnClick
			@Override
			public void mouseClicked(MouseEvent e) {
				int gender = -1;

				if (myModel.getValueAt(jtEmployees.getSelectedRow(), 10).toString().equals("Male"))
				{
					gender = 1;
				} 
				else if (myModel.getValueAt(jtEmployees.getSelectedRow(), 10).toString().equals("Female"))
				{
					gender = 2;
				}
				else gender = 3;

				tfName_empMgmt.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 2).toString());
				tfSurname_empMgmt.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 3).toString());
				tfusername_empMgmt.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 4).toString());
				tfEmail_empMgmt.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 5).toString());
				jpPassword_empMgmt.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 6).toString());
				jpConfirmPassword_empMgmt.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 6).toString());
				tfPhoneNum_empMgmt.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 7).toString());
				tfAddress_empMgmt.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 8).toString());
				tfAge_empMgmt.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 9).toString());
				cbGender_empMgmt.setSelectedIndex(gender - 1);
				tfPosition.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 11).toString());
				tfSalary.setText(myModel.getValueAt(jtEmployees.getSelectedRow(), 12).toString());
				tempUserName = tfusername_empMgmt.getText();
			}
		});
	}
}
