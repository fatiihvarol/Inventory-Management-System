package view.manager;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dbHelper.DB;
import entities.*;
import view.home.AdminHomeForm;
import view.home.EmployeeHomeForm;
import view.home.LoginForm;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class OrderManagementForm extends JFrame
{
	
	static Connection connection;
	static Statement stmt;
	
	static ArrayList<Order> orders = new ArrayList<>();

	static ArrayList<Product> products = new ArrayList<>();
	static ArrayList<String> productNames = new ArrayList<>();
	
	static ArrayList<Employee> employees = new ArrayList<>();
	static ArrayList<Integer> employeeIDs = new ArrayList<>();
	
	static ArrayList<Customer> customers = new ArrayList<>();
	static ArrayList<String> customerUserNames = new ArrayList<>();

	static DefaultTableModel ordersTableModel = new DefaultTableModel();

	Object[] columns = { "Id", "Employee Id", "Customer Id", "Product Id", "Track Number", "Quantity Request", "Order Date" };
	Object[] rows = new Object[columns.length];

	private JPanel contentPane;
	private JTable jtOrders;
	private JTextField tfOrderTrackNum;
	private JTextField tfOrderQuantity;
	
	private String tempOrderTrackNumber;
	
	private void readOrders()
	{
		boolean flag;
		try
		{
			connection = DB.getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM orders");

			while (rs.next())
			{
				Order order = new Order
						(
								rs.getInt(1), 
								rs.getInt(2),
								rs.getInt(3),
								rs.getInt(4),
								rs.getString(5),
								rs.getInt(6),
								rs.getString(7)
						);
				
				flag = true;
				
				for (int i = 0; i < orders.size(); i++) 
				{
					if (order.getOrderId()==orders.get(i).getOrderId()) flag=false;
				}
				
				if (flag) orders.add(order);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void bringOrders()
	{
		ordersTableModel.getDataVector().removeAllElements();
		readOrders();

		if (orders.size() == 0) 
		{
			rows[0] = null;
			rows[1] = null;
			rows[2] = null;
			rows[3] = null;
			rows[4] = null;
			rows[5] = null;
			rows[6] = null;

			ordersTableModel.addRow(rows);
		}
		for (int i = 0; i < orders.size(); i++) {
			rows[0] = orders.get(i).getOrderId();
			rows[1] = orders.get(i).getEmployeeId();
			rows[2] = orders.get(i).getProductId();
			rows[3] = orders.get(i).getCustomerId();
			rows[4] = orders.get(i).getOrderNumber();
			rows[5] = orders.get(i).getOrderQuantity();
			rows[6] = orders.get(i).getOrderDate();
			
			
			ordersTableModel.addRow(rows);
		}
		jtOrders.setModel(ordersTableModel);
	}
	
	private void readProducts()
	{
		boolean flag;
		try
		{
			connection = DB.getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM products");

			while (rs.next())
			{
				Product product = new Product
						(
								rs.getInt(1), 
								rs.getInt(2),
								rs.getInt(3),
								rs.getString(4),
								rs.getString(5),
								rs.getString(6),
								rs.getFloat(7),
								rs.getFloat(8),
								rs.getInt(9)
						);
				
				flag = true;
				
				for (int i = 0; i < products.size(); i++) 
				{
					if (product.getId()==products.get(i).getId()) flag=false;
				}
				
				if (flag) products.add(product);
				
				for (int i = 0; i < products.size(); i++)
				{
					if (!productNames.contains(products.get(i).getName())) 
					{
						productNames.add(products.get(i).getName());
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
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
				if (flag) employees.add(employee);
				
				for (int i = 0; i < employees.size(); i++)
				{
					if (!employeeIDs.contains(employees.get(i).getID()))
					{
						employeeIDs.add(employees.get(i).getID());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
				
				for (int i = 0; i < customers.size(); i++)
				{
					if (!customerUserNames.contains(customers.get(i).getUserName())) 
					{
						customerUserNames.add(customers.get(i).getUserName());
					}
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public OrderManagementForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(OrderManagementForm.class.getResource("/assets/Icon_Order.png")));
		setTitle("Order Management");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 760, 611);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 11, 716, 301);
		panel.add(scrollPane);
		
		jtOrders = new JTable();
		ordersTableModel.setColumnIdentifiers(columns);
		jtOrders.setBackground(SystemColor.controlHighlight);
		jtOrders.setBounds(21, 11, 716, 301);
		scrollPane.setViewportView(jtOrders);
		
		readEmployees();
		readCustomers();
		readProducts();
		bringOrders();
		
		JComboBox cbEmployee = new JComboBox(employeeIDs.toArray());
	    cbEmployee.setForeground(new Color(0, 102, 255));
	    cbEmployee.setFont(new Font("Century Gothic", Font.BOLD, 12));
	    cbEmployee.setBounds(160, 355, 201, 25);
	    panel.add(cbEmployee);
	    
	    JComboBox cbCustomer = new JComboBox(customerUserNames.toArray());
	    cbCustomer.setForeground(new Color(0, 102, 255));
	    cbCustomer.setFont(new Font("Century Gothic", Font.BOLD, 12));
	    cbCustomer.setBounds(160, 392, 201, 25);
	    panel.add(cbCustomer);
	    
	    JComboBox cbProduct = new JComboBox(productNames.toArray());
	    cbProduct.setForeground(new Color(0, 102, 255));
	    cbProduct.setFont(new Font("Century Gothic", Font.BOLD, 12));
	    cbProduct.setBounds(160, 433, 201, 25);
	    panel.add(cbProduct);
	    
	    JDateChooser datePickerOrderMgmt = new JDateChooser();
	    datePickerOrderMgmt.setBounds(536, 433, 201, 25);
	    panel.add(datePickerOrderMgmt);
	    
	    JButton btnAddOrder = new JButton("ADD");
	    btnAddOrder.setForeground(Color.WHITE);
	    btnAddOrder.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnAddOrder.setBackground(new Color(0, 102, 255));
	    btnAddOrder.setBounds(120, 518, 193, 37);
	    panel.add(btnAddOrder);
	    
	    JButton btnDeleteOrder = new JButton("DELETE");
	    btnDeleteOrder.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		String query_delete = "delete from orders where OrderTrackNumber='" + tempOrderTrackNumber + "'";
				try {
					stmt.execute(query_delete);
					JOptionPane.showMessageDialog(null, "Order Deleted");
					for (int i = 0; i < orders.size(); i++) {
						if (orders.get(i).getOrderNumber().equals(tempOrderTrackNumber)) {
							orders.remove(i);
						}
					}
					
					tfOrderTrackNum.setText("");
					tfOrderQuantity.setText("");
					datePickerOrderMgmt.setDate(new Date());
					cbEmployee.setSelectedIndex(0);
					cbCustomer.setSelectedIndex(0);
					cbProduct.setSelectedIndex(0);
					
					bringOrders();
					
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
	    	}
	    });
	    
	    btnDeleteOrder.setForeground(Color.WHITE);
	    btnDeleteOrder.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnDeleteOrder.setBackground(new Color(0, 102, 255));
	    btnDeleteOrder.setBounds(444, 518, 193, 37);
	    panel.add(btnDeleteOrder);
	    
	    JLabel lblEmployee = new JLabel("Employee:");
	    lblEmployee.setForeground(new Color(0, 102, 255));
	    lblEmployee.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblEmployee.setBounds(21, 350, 88, 30);
	    panel.add(lblEmployee);
	    
	    JLabel lblCustomer = new JLabel("Customer:");
	    lblCustomer.setForeground(new Color(0, 102, 255));
	    lblCustomer.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblCustomer.setBounds(21, 387, 88, 30);
	    panel.add(lblCustomer);
	    	    
	    JButton btnGoBack_orderMgmt = new JButton("");
	    btnGoBack_orderMgmt.addMouseListener(new MouseAdapter() {
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
	    btnGoBack_orderMgmt.setIcon(new ImageIcon(ProductManagementForm.class.getResource("/assets/Icon_Goback.png")));
	    btnGoBack_orderMgmt.setForeground(Color.WHITE);
	    btnGoBack_orderMgmt.setBackground(new Color(0, 102, 255));
	    btnGoBack_orderMgmt.setBounds(350, 518, 58, 37);
	    panel.add(btnGoBack_orderMgmt);
	    
	    JLabel lblProduct_orderMgmt = new JLabel("Product:");
	    lblProduct_orderMgmt.setForeground(new Color(0, 102, 255));
	    lblProduct_orderMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblProduct_orderMgmt.setBounds(21, 428, 88, 30);
	    panel.add(lblProduct_orderMgmt);
	    
	    JLabel lblOrderNumber = new JLabel("Order Number:");
	    lblOrderNumber.setForeground(new Color(0, 102, 255));
	    lblOrderNumber.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblOrderNumber.setBounds(397, 355, 129, 30);
	    panel.add(lblOrderNumber);
	    
	    tfOrderTrackNum = new JTextField();
	    tfOrderTrackNum.setToolTipText("");
	    tfOrderTrackNum.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    tfOrderTrackNum.setColumns(10);
	    tfOrderTrackNum.setBounds(536, 359, 201, 25);
	    panel.add(tfOrderTrackNum);
	    
	    JLabel lblQuantity_orderMgmt = new JLabel("Quantity");
	    lblQuantity_orderMgmt.setForeground(new Color(0, 102, 255));
	    lblQuantity_orderMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblQuantity_orderMgmt.setBounds(397, 391, 129, 30);
	    panel.add(lblQuantity_orderMgmt);
	    
	    tfOrderQuantity = new JTextField();
	    tfOrderQuantity.setToolTipText("");
	    tfOrderQuantity.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    tfOrderQuantity.setColumns(10);
	    tfOrderQuantity.setBounds(536, 395, 201, 25);
	    panel.add(tfOrderQuantity);

	    JLabel lblOrderDateAdded = new JLabel("Upload Date:");
	    lblOrderDateAdded.setForeground(new Color(0, 102, 255));
	    lblOrderDateAdded.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblOrderDateAdded.setBounds(397, 428, 129, 30);
	    panel.add(lblOrderDateAdded);
	    
	    btnAddOrder.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		try {
					stmt = connection.createStatement();

					String trackNumber = tfOrderTrackNum.getText();
					String orderDate = datePickerOrderMgmt.getDate().toString();
					int employeeId = cbEmployee.getSelectedIndex() + 1;
					int customerId = cbCustomer.getSelectedIndex() + 1;
					int productId = cbProduct.getSelectedIndex() + 1;
					int quantity = 0;
					quantity = Integer.parseInt(tfOrderQuantity.getText());
					
					int temp_id;
					
					if (orders.size() != 0) 
					{
						temp_id = orders.get(orders.size() - 1).getOrderId() + 1;
					} 
					else temp_id = 1;

					String sql_query = null;

					sql_query = "INSERT INTO orders(OrderID,EmployeeID,CustomerID,ProductID,OrderTrackNumber,OrderQuantity,OrderDate) VALUES ("
							+ temp_id + "," + employeeId + "," + customerId + "," + productId + ",'" + trackNumber + "',"
							+ quantity + ",'" + orderDate + "')";

					if (!trackNumber.equals("") && !tfOrderQuantity.getText().equals("") && !orderDate.equals("")) 
					{
						stmt.execute(sql_query);
						JOptionPane.showMessageDialog(null, "Product Successfully Added");
						
						bringOrders();
						
						tfOrderTrackNum.setText("");
						tfOrderQuantity.setText("");
						datePickerOrderMgmt.setDate(new Date());
						cbEmployee.setSelectedIndex(0);
						cbCustomer.setSelectedIndex(0);
						cbProduct.setSelectedIndex(0);
						

					} else
						JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	    	}
	    });
	    
	    
	    jtOrders.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int tempEmployeeId = (int) ordersTableModel.getValueAt(jtOrders.getSelectedRow(), 1);
				int tempCustomerId = (int) ordersTableModel.getValueAt(jtOrders.getSelectedRow(), 2);
				int tempProductId = (int) ordersTableModel.getValueAt(jtOrders.getSelectedRow(), 3);

				tfOrderTrackNum.setText(ordersTableModel.getValueAt(jtOrders.getSelectedRow(), 4).toString());
				tfOrderQuantity.setText(ordersTableModel.getValueAt(jtOrders.getSelectedRow(), 5).toString());
				datePickerOrderMgmt.setDate(new Date());
				cbEmployee.setSelectedIndex(tempEmployeeId - 1);
				cbCustomer.setSelectedIndex(tempCustomerId - 1);
				cbProduct.setSelectedIndex(tempProductId - 1);
				tempOrderTrackNumber = ordersTableModel.getValueAt(jtOrders.getSelectedRow(), 4).toString();
			}
		});
	}
}
