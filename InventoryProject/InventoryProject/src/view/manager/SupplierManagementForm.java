package view.manager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dbHelper.DB;
import entities.Category;
import entities.Supplier;
import view.home.AdminHomeForm;
import view.home.EmployeeHomeForm;
import view.home.LoginForm;

import java.awt.Panel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class SupplierManagementForm extends JFrame {
	static Connection connection;
	static Statement stmt;

	static ArrayList<Supplier> suppliers = new ArrayList<>();
	static ArrayList<Category> categories = new ArrayList<>();
	static ArrayList<String> categoryNames = new ArrayList<>();

	static DefaultTableModel myModel = new DefaultTableModel();

	Object[] columns = { "Id", "First Name", "Last Name", "Company", "Category Id", "Phone Number", "Email",
			"Address" };
	Object[] rows = new Object[columns.length];

	private JPanel contentPane;

	private JTextField tfAddress_suppMgmt;
	private JTextField tfPhoneNum_suppMgmt;
	private JTextField tfEmail_suppMgmt;
	private JTextField tfSurname_suppMgmt;
	private JTextField tfName_suppMgmt;
	private JTable jtSuppliers;
	private JTextField tfCompany_suppMgmt;
	private String temp_email;

	private void readCategories() {
		boolean flag;
		try {
			connection = DB.getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM categories");

			while (rs.next()) {
				Category category = new Category(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));

				flag = true;

				for (int i = 0; i < categories.size(); i++) {
					if (category.getID() == categories.get(i).getID())
						flag = false;
				}

				if (flag)
					categories.add(category);

				for (int i = 0; i < categories.size(); i++) {
					if (!categoryNames.contains(categories.get(i).getCATEGORY_NAME())) {
						categoryNames.add(categories.get(i).getCATEGORY_NAME());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readSuppliers() {
		boolean flag;
		try {
			connection = DB.getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM suppliers");

			while (rs.next()) {
				Supplier supplier = new Supplier(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getInt(5), rs.getString(6), rs.getString(7), rs.getString(8));

				flag = true;
				for (int i = 0; i < suppliers.size(); i++) {
					if (supplier.getID() == suppliers.get(i).getID())
						flag = false;
				}
				if (flag)
					suppliers.add(supplier);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void bringSuppliers() {
		myModel.getDataVector().removeAllElements();
		readSuppliers();

		if (suppliers.size() == 0) {
			rows[0] = null;
			rows[1] = null;
			rows[2] = null;
			rows[3] = null;
			rows[4] = null;
			rows[5] = null;
			rows[6] = null;
			rows[7] = null;

			myModel.addRow(rows);
		}
		for (int i = 0; i < suppliers.size(); i++) {
			rows[0] = suppliers.get(i).getID();
			rows[1] = suppliers.get(i).getFirstName();
			rows[2] = suppliers.get(i).getLastName();
			rows[3] = suppliers.get(i).getCompany();
			rows[4] = suppliers.get(i).getCategoryID();
			rows[5] = suppliers.get(i).getPhoneNumber();
			rows[6] = suppliers.get(i).getEmail();
			rows[7] = suppliers.get(i).getAddress();
			myModel.addRow(rows);
		}
		jtSuppliers.setModel(myModel);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SupplierManagementForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SupplierManagementForm.class.getResource("/assets/Icon_Suppliers.png")));
		setTitle("Supplier Management");
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

		jtSuppliers = new JTable();
		myModel.setColumnIdentifiers(columns);
		jtSuppliers.setBackground(SystemColor.controlHighlight);
		jtSuppliers.setBounds(436, 39, 723, 561);
		scrollPane.setViewportView(jtSuppliers);

		readCategories();
		bringSuppliers();

		JButton btnAddSupplier = new JButton("ADD");
		btnAddSupplier.setForeground(Color.WHITE);
		btnAddSupplier.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnAddSupplier.setBackground(new Color(0, 102, 255));
		btnAddSupplier.setBounds(34, 479, 99, 37);
		panel.add(btnAddSupplier);

		JButton btnUpdateSupplier = new JButton("UPDATE");
		btnUpdateSupplier.setForeground(Color.WHITE);
		btnUpdateSupplier.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnUpdateSupplier.setBackground(new Color(0, 102, 255));
		btnUpdateSupplier.setBounds(135, 479, 99, 37);
		panel.add(btnUpdateSupplier);

		JButton btnDeleteSupplier = new JButton("DELETE");
		btnDeleteSupplier.setForeground(Color.WHITE);
		btnDeleteSupplier.setFont(new Font("Century Gothic", Font.BOLD, 14));
		btnDeleteSupplier.setBackground(new Color(0, 102, 255));
		btnDeleteSupplier.setBounds(235, 479, 99, 37);
		panel.add(btnDeleteSupplier);

		JButton btnGoBack_suppMgmt = new JButton("");
		btnGoBack_suppMgmt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(LoginForm.userType==1)
	    		{
	    			AdminHomeForm adminHome = new AdminHomeForm();
	    			adminHome.setVisible(true);
	    			adminHome.setLocationRelativeTo(null);
					setVisible(false);
	    		}
	    		else 
	    		{
	    			EmployeeHomeForm employeeHome = new EmployeeHomeForm();
	    			employeeHome.setVisible(true);
	    			employeeHome.setLocationRelativeTo(null);
					setVisible(false);
	    		}
			}
		});
		btnGoBack_suppMgmt.setIcon(new ImageIcon(SupplierManagementForm.class.getResource("/assets/Icon_Goback.png")));
		btnGoBack_suppMgmt.setForeground(Color.WHITE);
		btnGoBack_suppMgmt.setBackground(new Color(0, 102, 255));
		btnGoBack_suppMgmt.setBounds(344, 479, 58, 37);
		panel.add(btnGoBack_suppMgmt);

		tfAddress_suppMgmt = new JTextField();
		tfAddress_suppMgmt.setToolTipText("");
		tfAddress_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfAddress_suppMgmt.setColumns(10);
		tfAddress_suppMgmt.setBounds(209, 361, 193, 25);
		panel.add(tfAddress_suppMgmt);

		JLabel lblAddress_suppMgmt = new JLabel("Address:");
		lblAddress_suppMgmt.setForeground(new Color(0, 102, 255));
		lblAddress_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblAddress_suppMgmt.setBounds(34, 357, 88, 30);
		panel.add(lblAddress_suppMgmt);

		JLabel lblPhoneNumber_suppMgmt = new JLabel("Phone Number:");
		lblPhoneNumber_suppMgmt.setForeground(new Color(0, 102, 255));
		lblPhoneNumber_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblPhoneNumber_suppMgmt.setBounds(34, 280, 121, 30);
		panel.add(lblPhoneNumber_suppMgmt);

		tfPhoneNum_suppMgmt = new JTextField();
		tfPhoneNum_suppMgmt.setToolTipText("");
		tfPhoneNum_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfPhoneNum_suppMgmt.setColumns(10);
		tfPhoneNum_suppMgmt.setBounds(209, 284, 193, 25);
		panel.add(tfPhoneNum_suppMgmt);

		tfEmail_suppMgmt = new JTextField();
		tfEmail_suppMgmt.setToolTipText("");
		tfEmail_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfEmail_suppMgmt.setColumns(10);
		tfEmail_suppMgmt.setBounds(209, 321, 193, 25);
		panel.add(tfEmail_suppMgmt);

		JLabel lblEmail_suppMgmt = new JLabel("Email:");
		lblEmail_suppMgmt.setForeground(new Color(0, 102, 255));
		lblEmail_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblEmail_suppMgmt.setBounds(34, 321, 47, 30);
		panel.add(lblEmail_suppMgmt);

		tfSurname_suppMgmt = new JTextField();
		tfSurname_suppMgmt.setToolTipText("");
		tfSurname_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfSurname_suppMgmt.setColumns(10);
		tfSurname_suppMgmt.setBounds(209, 172, 193, 25);
		panel.add(tfSurname_suppMgmt);

		JLabel lblSurname_suppMgmt = new JLabel("Surname:");
		lblSurname_suppMgmt.setForeground(new Color(0, 102, 255));
		lblSurname_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblSurname_suppMgmt.setBounds(34, 168, 74, 30);
		panel.add(lblSurname_suppMgmt);

		JLabel lblName_suppMgmt = new JLabel("Name:");
		lblName_suppMgmt.setForeground(new Color(0, 102, 255));
		lblName_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblName_suppMgmt.setBounds(34, 127, 53, 30);
		panel.add(lblName_suppMgmt);

		tfName_suppMgmt = new JTextField();
		tfName_suppMgmt.setToolTipText("");
		tfName_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfName_suppMgmt.setColumns(10);
		tfName_suppMgmt.setBounds(209, 136, 193, 25);
		panel.add(tfName_suppMgmt);

		JComboBox cbCategory_suppMgmt = new JComboBox(categoryNames.toArray());
		cbCategory_suppMgmt.setBounds(209, 244, 193, 25);
		panel.add(cbCategory_suppMgmt);
		cbCategory_suppMgmt.setSelectedIndex(0);

		JLabel lblCategory_suppMgmt = new JLabel("Category:");
		lblCategory_suppMgmt.setForeground(new Color(0, 102, 255));
		lblCategory_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblCategory_suppMgmt.setBounds(34, 245, 99, 30);
		panel.add(lblCategory_suppMgmt);

		tfCompany_suppMgmt = new JTextField();
		tfCompany_suppMgmt.setToolTipText("");
		tfCompany_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfCompany_suppMgmt.setColumns(10);
		tfCompany_suppMgmt.setBounds(209, 208, 193, 25);
		panel.add(tfCompany_suppMgmt);

		JLabel lblCompany_suppMgmt = new JLabel("Company:");
		lblCompany_suppMgmt.setForeground(new Color(0, 102, 255));
		lblCompany_suppMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblCompany_suppMgmt.setBounds(34, 208, 88, 30);
		panel.add(lblCompany_suppMgmt);
		btnDeleteSupplier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) 
			{
				String query_delete = "delete from suppliers where Email='" + temp_email+"'";
				try {
					stmt.execute(query_delete);
					JOptionPane.showMessageDialog(null, "Supplier Deleted");
					for (int i = 0; i < suppliers.size(); i++) {
						if (suppliers.get(i).getEmail().equals(temp_email)) {
							suppliers.remove(i);
						}
					}
					
					tfName_suppMgmt.setText("");
					tfSurname_suppMgmt.setText("");
					tfPhoneNum_suppMgmt.setText("");
					tfEmail_suppMgmt.setText("");
					tfCompany_suppMgmt.setText("");
					tfAddress_suppMgmt.setText("");
					
					bringSuppliers();
					
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
			}
		});
   
		btnUpdateSupplier.addActionListener(new ActionListener() {  //update
			
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) 
			{				
				String name = null, surname = null, phone_number = null, email = null, address = null, company_name = null;
				int catagoryID=0,supplierID=0;
				name = tfName_suppMgmt.getText();
				surname = tfSurname_suppMgmt.getText();
				phone_number = tfPhoneNum_suppMgmt.getText();
				email = tfEmail_suppMgmt.getText();
				address = tfAddress_suppMgmt.getText();
				company_name = tfCompany_suppMgmt.getText();
				
				catagoryID = cbCategory_suppMgmt.getSelectedIndex() + 1;
				
				String sql_query = null;

				sql_query = "UPDATE suppliers SET FirstName='"  + name + "'," + "LastName='"
						+ surname + "'," + "Company='" + company_name + "'," + "CategoryID=" + catagoryID + ","
						+ "PhoneNumber='" + phone_number + "'," + "Email='" + email + "'," + "Address='" + address
						+ "' WHERE Email='"+temp_email+"'";

				if (!name.equals("") && !surname.equals("") && !phone_number.equals("") && !email.equals("")
						&&!company_name.equals("") ) {

					for (int i = 0; i < suppliers.size(); i++) {
						if (suppliers.get(i).getEmail().equals(temp_email)) {
							suppliers.set(i, new Supplier(suppliers.get(i).getID(), name, surname, company_name,catagoryID,phone_number, email, address));
						}
					}
					try {
						stmt.execute(sql_query);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					bringSuppliers();
					JOptionPane.showMessageDialog(null, "Supplier Updated !");
					tfName_suppMgmt.setText("");
					tfSurname_suppMgmt.setText("");
					tfPhoneNum_suppMgmt.setText("");
					tfEmail_suppMgmt.setText("");
					tfCompany_suppMgmt.setText("");
					tfAddress_suppMgmt.setText("");

				} else
					JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");

			}
		});
		
		btnAddSupplier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					stmt = connection.createStatement();

					String name = null, surname = null, phone_number = null, email = null, address = null,
							company_name = null;
					int catagoryID = 0;
					name = tfName_suppMgmt.getText();
					surname = tfSurname_suppMgmt.getText();
					phone_number = tfPhoneNum_suppMgmt.getText();
					email = tfEmail_suppMgmt.getText();
					address = tfAddress_suppMgmt.getText();
					company_name = tfCompany_suppMgmt.getText();
					catagoryID = cbCategory_suppMgmt.getSelectedIndex() + 1;

					int temp_id;
					if (suppliers.size() != 0) {
						temp_id = suppliers.get(suppliers.size() - 1).getID() + 1;
					} else
						temp_id = 1;

					String sql_query = null;

					sql_query = "INSERT INTO suppliers(SupplierID,FirstName,LastName,Company,CategoryID,PhoneNumber,Email,Address) VALUES ("
							+ temp_id + ",'" + name + "','" + surname + "','" + company_name + "'," + catagoryID + ",'"
							+ phone_number + "','" + email + "','" + address + "'" + ")";

					if (!name.equals("") && !surname.equals("") && !phone_number.equals("") && !email.equals("")
							&& !address.equals("")) {

						stmt.execute(sql_query);
						JOptionPane.showMessageDialog(null, "Supplier Added");
						bringSuppliers();

						tfName_suppMgmt.setText("");
						tfSurname_suppMgmt.setText("");
						tfPhoneNum_suppMgmt.setText("");
						tfAddress_suppMgmt.setText("");

						tfCompany_suppMgmt.setText("");

						tfEmail_suppMgmt.setText("");

					} else
						JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		jtSuppliers.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int temp = (int) myModel.getValueAt(jtSuppliers.getSelectedRow(), 4);

				tfName_suppMgmt.setText(myModel.getValueAt(jtSuppliers.getSelectedRow(), 1).toString());
				tfSurname_suppMgmt.setText(myModel.getValueAt(jtSuppliers.getSelectedRow(), 2).toString());
				tfCompany_suppMgmt.setText(myModel.getValueAt(jtSuppliers.getSelectedRow(), 3).toString());
				tfPhoneNum_suppMgmt.setText(myModel.getValueAt(jtSuppliers.getSelectedRow(), 5).toString());
				temp_email =  myModel.getValueAt(jtSuppliers.getSelectedRow(), 6).toString();
				cbCategory_suppMgmt.setSelectedIndex(temp - 1);
				tfEmail_suppMgmt.setText(myModel.getValueAt(jtSuppliers.getSelectedRow(), 6).toString());
				tfAddress_suppMgmt.setText(myModel.getValueAt(jtSuppliers.getSelectedRow(), 7).toString());
			}
		});
	}
}