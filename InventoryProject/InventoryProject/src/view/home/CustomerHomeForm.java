package view.home;

import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dbHelper.DB;
import entities.Brand;
import entities.Category;
import entities.Product;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CustomerHomeForm extends JFrame 
{
	static Connection connection;
	static Statement stmt;

	static ArrayList<Product> products = new ArrayList<>();
	
	static ArrayList<Category> categories = new ArrayList<>();
	static ArrayList<String> categoryNames = new ArrayList<>();
	
	static ArrayList<Brand> brands = new ArrayList<>();
	static ArrayList<String> brandNames = new ArrayList<>();

	static DefaultTableModel productsTableModel = new DefaultTableModel();

	Object[] columns = { "Id", "Category Id", "Brand Id", "Name", "Description", "Upload Date", "Price", "Tax(%)","Quantity"};
	Object[] rows = new Object[columns.length];

	private JPanel contentPane;
	private JTable tblProducts_Customer;
	
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
	
	private void readBrands()
	{
		boolean flag;
		try
		{
			connection = DB.getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM brands");

			while (rs.next())
			{
				Brand brand = new Brand
						(
								rs.getInt(1), 
								rs.getString(2),
								rs.getString(3),
								rs.getString(4)
						);
				
				flag = true;
				
				for (int i = 0; i < brands.size(); i++) 
				{
					if (brand.getBrandId()==brands.get(i).getBrandId()) flag=false;
				}
				
				if (flag) brands.add(brand);
				
				for (int i = 0; i < brands.size(); i++) {
					if (!brandNames.contains(brands.get(i).getBrandName())) {
						brandNames.add(brands.get(i).getBrandName());
					}
				}
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void readProducts(String query)
	{
		boolean flag;
		try
		{
			connection = DB.getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

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
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
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
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void bringProducts() 
	{
		productsTableModel.getDataVector().removeAllElements();
		readProducts();

		if (products.size() == 0) 
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

			productsTableModel.addRow(rows);
		}
		for (int i = 0; i < products.size(); i++) {
			rows[0] = products.get(i).getId();
			rows[1] = products.get(i).getCategoryId();
			rows[2] = products.get(i).getBrandId();
			rows[3] = products.get(i).getName();
			rows[4] = products.get(i).getDescription();
			rows[5] = products.get(i).getDateAdded();
			rows[6] = products.get(i).getPrice();
			rows[7] = products.get(i).getTax();
			rows[8] = products.get(i).getQuantity();
			
			productsTableModel.addRow(rows);
		}
		tblProducts_Customer.setModel(productsTableModel);
	}
	
	private void bringProducts(String query) 
	{
		products.clear();
		productsTableModel.getDataVector().removeAllElements();
		readProducts(query);

		if (products.size() == 0) 
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

			productsTableModel.addRow(rows);
		}
		for (int i = 0; i < products.size(); i++) {
			rows[0] = products.get(i).getId();
			rows[1] = products.get(i).getCategoryId();
			rows[2] = products.get(i).getBrandId();
			rows[3] = products.get(i).getName();
			rows[4] = products.get(i).getDescription();
			rows[5] = products.get(i).getDateAdded();
			rows[6] = products.get(i).getPrice();
			rows[7] = products.get(i).getTax();
			rows[8] = products.get(i).getQuantity();
			
			productsTableModel.addRow(rows);
		}
		tblProducts_Customer.setModel(productsTableModel);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CustomerHomeForm() 
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(CustomerHomeForm.class.getResource("/assets/Icon_Account.png")));
		setTitle("Customer");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel panel = new Panel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 1184, 611);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(488, 92, 665, 481);
		panel.add(scrollPane);
		
		tblProducts_Customer = new JTable();
		productsTableModel.setColumnIdentifiers(columns);
		tblProducts_Customer.setBackground(SystemColor.controlHighlight);
		tblProducts_Customer.setBounds(488, 92, 665, 481);
		scrollPane.setViewportView(tblProducts_Customer);
		
		readCategories();
		readBrands();
		bringProducts();
		
		Panel pnlHeader = new Panel();
		pnlHeader.setBackground(new Color(0, 102, 255));
		pnlHeader.setBounds(0, 0, 1184, 66);
		panel.add(pnlHeader);
		pnlHeader.setLayout(null);
		
		JLabel lblHeader = new JLabel("Inventory Management System");
		lblHeader.setBounds(393, 11, 382, 44);
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Century Gothic", Font.BOLD, 24));
		pnlHeader.add(lblHeader);
		
		JLabel lblLogOut = new JLabel("Log Out");
		lblLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginForm login = new LoginForm();
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				setVisible(false);
			}
		});
		lblLogOut.setBounds(10, 18, 150, 41);
		pnlHeader.add(lblLogOut);
		lblLogOut.setIcon(new ImageIcon(CustomerHomeForm.class.getResource("/assets/Icon_Logout.png")));
		lblLogOut.setForeground(Color.WHITE);
		lblLogOut.setFont(new Font("Century Gothic", Font.BOLD, 12));
		
		JComboBox cbCategories = new JComboBox(categoryNames.toArray());
		cbCategories.setFont(new Font("Century Gothic", Font.BOLD, 12));
		cbCategories.setForeground(new Color(0, 102, 255));
		cbCategories.setBounds(191, 242, 201, 45);
		panel.add(cbCategories);
		
		JComboBox cbBrands = new JComboBox(brandNames.toArray());
		cbBrands.setForeground(new Color(0, 102, 255));
		cbBrands.setFont(new Font("Century Gothic", Font.BOLD, 12));
		cbBrands.setBounds(191, 314, 201, 45);
		panel.add(cbBrands);
		
		JButton btnBringProducts = new JButton("BRING PRODUCTS");
		btnBringProducts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				int categoryId = cbCategories.getSelectedIndex() + 1;
				int brandId = cbBrands.getSelectedIndex() + 1;
				String query = "SELECT * FROM products WHERE CategoryId=" + categoryId + " and " + "BrandId=" + brandId;
				bringProducts(query);
			}
		});
		btnBringProducts.setBackground(new Color(0, 102, 255));
		btnBringProducts.setForeground(Color.WHITE);
		btnBringProducts.setFont(new Font("Century Gothic", Font.BOLD, 12));
		btnBringProducts.setBounds(191, 398, 201, 45);
		panel.add(btnBringProducts);
		
		JLabel lblCategory_Customer = new JLabel("CATEGORY:");
		lblCategory_Customer.setForeground(new Color(0, 102, 255));
		lblCategory_Customer.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblCategory_Customer.setBounds(58, 240, 88, 45);
		panel.add(lblCategory_Customer);
		
		JLabel lblBrand_Customer = new JLabel("BRAND:");
		lblBrand_Customer.setForeground(new Color(0, 102, 255));
		lblBrand_Customer.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblBrand_Customer.setBounds(58, 314, 88, 45);
		panel.add(lblBrand_Customer);
	}
}
