package view.manager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dbHelper.DB;
import entities.Brand;
import entities.Category;
import entities.Product;
import view.home.*;

import java.awt.Color;
import java.awt.Toolkit;

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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class ProductManagementForm extends JFrame 
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
	private JTable jtProducts_ProdMgmt;
	private JTextField tfProdName_ProdMgmt;
	private JTextField tfProdDesc_ProdMgmt;
	private JTextField tfTax_ProdMgmt;
	private JTextField tfPrice_ProdMgmt;
	private JTextField tfQuantity_ProdMgmt;
	
	private String tempProductName;
	
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
		jtProducts_ProdMgmt.setModel(productsTableModel);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ProductManagementForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProductManagementForm.class.getResource("/assets/icon-product-1.png")));
		setTitle("Product Management");
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
		
		jtProducts_ProdMgmt = new JTable();
		productsTableModel.setColumnIdentifiers(columns);
		jtProducts_ProdMgmt.setBackground(SystemColor.controlHighlight);
		jtProducts_ProdMgmt.setBounds(436, 39, 723, 561);
		scrollPane.setViewportView(jtProducts_ProdMgmt);
		
		readCategories();
		readBrands();
		bringProducts();
		
		JDateChooser datePickerProdMgmt = new JDateChooser();
		//datePickerProdMgmt.setDateFormatString("YYYY-MM-DD");
	    datePickerProdMgmt.setBounds(536, 465, 201, 25);
	    panel.add(datePickerProdMgmt);
	    
	    JLabel lblProductDateAdded = new JLabel("Date of Upload:");
	    lblProductDateAdded.setForeground(new Color(0, 102, 255));
	    lblProductDateAdded.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblProductDateAdded.setBounds(397, 460, 129, 30);
	    panel.add(lblProductDateAdded);
	    
	    JButton btnAddProduct = new JButton("ADD");
	    btnAddProduct.setForeground(Color.WHITE);
	    btnAddProduct.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnAddProduct.setBackground(new Color(0, 102, 255));
	    btnAddProduct.setBounds(39, 527, 193, 37);
	    panel.add(btnAddProduct);
	    
	    JButton btnUpdateProduct = new JButton("UPDATE");
	    btnUpdateProduct.setForeground(Color.WHITE);
	    btnUpdateProduct.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnUpdateProduct.setBackground(new Color(0, 102, 255));
	    btnUpdateProduct.setBounds(242, 527, 193, 37);
	    panel.add(btnUpdateProduct);
	    
	    JButton btnDeleteProduct = new JButton("DELETE");
	    btnDeleteProduct.setForeground(Color.WHITE);
	    btnDeleteProduct.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnDeleteProduct.setBackground(new Color(0, 102, 255));
	    btnDeleteProduct.setBounds(445, 527, 193, 37);
	    panel.add(btnDeleteProduct);
	    
	    JComboBox cbCategories_ProdMgmt = new JComboBox(categoryNames.toArray());
	    cbCategories_ProdMgmt.setForeground(new Color(0, 102, 255));
	    cbCategories_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 12));
	    cbCategories_ProdMgmt.setBounds(160, 355, 201, 25);
	    panel.add(cbCategories_ProdMgmt);
	    
	    JLabel lblCategory_ProductMgmt = new JLabel("Category:");
	    lblCategory_ProductMgmt.setForeground(new Color(0, 102, 255));
	    lblCategory_ProductMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblCategory_ProductMgmt.setBounds(21, 350, 88, 30);
	    panel.add(lblCategory_ProductMgmt);
	    
	    JLabel lblBrand_ProductMgmt = new JLabel("Brand:");
	    lblBrand_ProductMgmt.setForeground(new Color(0, 102, 255));
	    lblBrand_ProductMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblBrand_ProductMgmt.setBounds(21, 387, 88, 30);
	    panel.add(lblBrand_ProductMgmt);
	    
	    JComboBox cbBrands_ProdMgmt = new JComboBox(brandNames.toArray());
	    cbBrands_ProdMgmt.setForeground(new Color(0, 102, 255));
	    cbBrands_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 12));
	    cbBrands_ProdMgmt.setBounds(160, 392, 201, 25);
	    panel.add(cbBrands_ProdMgmt);
	    
	    JLabel lblProductName_ProdMgmt = new JLabel("Product Name:");
	    lblProductName_ProdMgmt.setForeground(new Color(0, 102, 255));
	    lblProductName_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblProductName_ProdMgmt.setBounds(21, 428, 129, 30);
	    panel.add(lblProductName_ProdMgmt);
	    
	    JLabel lblProductDescription_ProdMgmt = new JLabel("Description:");
	    lblProductDescription_ProdMgmt.setForeground(new Color(0, 102, 255));
	    lblProductDescription_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblProductDescription_ProdMgmt.setBounds(21, 464, 129, 30);
	    panel.add(lblProductDescription_ProdMgmt);
	    
	    tfProdName_ProdMgmt = new JTextField();
	    tfProdName_ProdMgmt.setToolTipText("");
	    tfProdName_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    tfProdName_ProdMgmt.setColumns(10);
	    tfProdName_ProdMgmt.setBounds(160, 432, 201, 25);
	    panel.add(tfProdName_ProdMgmt);
	    
	    tfProdDesc_ProdMgmt = new JTextField();
	    tfProdDesc_ProdMgmt.setToolTipText("");
	    tfProdDesc_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    tfProdDesc_ProdMgmt.setColumns(10);
	    tfProdDesc_ProdMgmt.setBounds(160, 468, 201, 25);
	    panel.add(tfProdDesc_ProdMgmt);
	    
	    JLabel lblPrice_ProdMgmt = new JLabel("Price:");
	    lblPrice_ProdMgmt.setForeground(new Color(0, 102, 255));
	    lblPrice_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblPrice_ProdMgmt.setBounds(397, 350, 129, 30);
	    panel.add(lblPrice_ProdMgmt);
	    
	    JLabel lblTax_ProdMgmt = new JLabel("Tax(%):");
	    lblTax_ProdMgmt.setForeground(new Color(0, 102, 255));
	    lblTax_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblTax_ProdMgmt.setBounds(397, 386, 129, 30);
	    panel.add(lblTax_ProdMgmt);
	    
	    tfTax_ProdMgmt = new JTextField();
	    tfTax_ProdMgmt.setToolTipText("");
	    tfTax_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    tfTax_ProdMgmt.setColumns(10);
	    tfTax_ProdMgmt.setBounds(536, 390, 201, 25);
	    panel.add(tfTax_ProdMgmt);
	    
	    tfPrice_ProdMgmt = new JTextField();
	    tfPrice_ProdMgmt.setToolTipText("");
	    tfPrice_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    tfPrice_ProdMgmt.setColumns(10);
	    tfPrice_ProdMgmt.setBounds(536, 354, 201, 25);
	    panel.add(tfPrice_ProdMgmt);
	    
	    tfQuantity_ProdMgmt = new JTextField();
	    tfQuantity_ProdMgmt.setToolTipText("");
	    tfQuantity_ProdMgmt.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    tfQuantity_ProdMgmt.setColumns(10);
	    tfQuantity_ProdMgmt.setBounds(536, 427, 201, 25);
	    panel.add(tfQuantity_ProdMgmt);
	    
	    JLabel lblQuantity_prodMgmt = new JLabel("Quantity");
	    lblQuantity_prodMgmt.setForeground(new Color(0, 102, 255));
	    lblQuantity_prodMgmt.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblQuantity_prodMgmt.setBounds(397, 423, 129, 30);
	    panel.add(lblQuantity_prodMgmt);
	    
	    JButton btnGoBack_ProdMgmt = new JButton("");
	    btnGoBack_ProdMgmt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
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
	    btnGoBack_ProdMgmt.setIcon(new ImageIcon(ProductManagementForm.class.getResource("/assets/Icon_Goback.png")));
	    btnGoBack_ProdMgmt.setForeground(Color.WHITE);
	    btnGoBack_ProdMgmt.setBackground(new Color(0, 102, 255));
	    btnGoBack_ProdMgmt.setBounds(648, 527, 58, 37);
	    panel.add(btnGoBack_ProdMgmt);
	    
	    btnAddProduct.addActionListener(new ActionListener() 
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{	
	    		try {
					stmt = connection.createStatement();

					String name = null;
					int catagoryID = 0;
					int brandId = 0;
					
					String description = null;
					String uploadDate = null;
					float price = 0.00f;
					float tax = 0.00f;
					int quantity = 0;
					
					name = tfProdName_ProdMgmt.getText();
					catagoryID = cbCategories_ProdMgmt.getSelectedIndex() + 1;
					brandId = cbBrands_ProdMgmt.getSelectedIndex() + 1;
					description = tfProdDesc_ProdMgmt.getText();
					uploadDate = datePickerProdMgmt.getDate().toString();
					price = Float.parseFloat(tfPrice_ProdMgmt.getText());
					tax = Float.parseFloat(tfTax_ProdMgmt.getText());
					quantity = Integer.parseInt(tfQuantity_ProdMgmt.getText());
					
					int temp_id;
					
					if (products.size() != 0) 
					{
						temp_id = products.get(products.size() - 1).getId() + 1;
					} 
					else temp_id = 1;

					String sql_query = null;

					sql_query = "INSERT INTO products(ProductID,CategoryID,BrandID,ProductName,Description,DateAdded,Price,Tax,Quantity) VALUES ("
							+ temp_id + "," + catagoryID + "," + brandId + ",'" + name + "','" + description + "','"
							+ uploadDate + "'," + price + "," + tax + "," + quantity + ")";

					if (!name.equals("") && !uploadDate.equals("") && !tfPrice_ProdMgmt.getText().equals("") && !tfTax_ProdMgmt.getText().equals("") && !tfQuantity_ProdMgmt.getText().equals("")) 
					{
						stmt.execute(sql_query);
						JOptionPane.showMessageDialog(null, "Product Successfully Added");
						
						bringProducts();

						tfProdName_ProdMgmt.setText("");
						tfProdDesc_ProdMgmt.setText("");
						datePickerProdMgmt.setDate(new Date());
						tfPrice_ProdMgmt.setText("");
						tfTax_ProdMgmt.setText("");
						tfQuantity_ProdMgmt.setText("");
						

					} else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
	    	}
	    });
	    

	    btnUpdateProduct.addActionListener(new ActionListener()
	    {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		String name = null;
				int catagoryID = 0;
				int brandId = 0;
				
				String description = null;
				String uploadDate = null;
				float price = 0.00f;
				float tax = 0.00f;
				int quantity = 0;
				
				name = tfProdName_ProdMgmt.getText();
				catagoryID = cbCategories_ProdMgmt.getSelectedIndex() + 1;
				brandId = cbBrands_ProdMgmt.getSelectedIndex() + 1;
				description = tfProdDesc_ProdMgmt.getText();
				uploadDate = datePickerProdMgmt.getDate().toString();
				price = Float.parseFloat(tfPrice_ProdMgmt.getText());
				tax = Float.parseFloat(tfTax_ProdMgmt.getText());
				quantity = Integer.parseInt(tfQuantity_ProdMgmt.getText());
				
				String sql_query = null;

				sql_query = "UPDATE products SET CategoryID=" + catagoryID + "," + "BrandID='" + brandId + "'," + "ProductName='" + name + "',"
                        + "Description='" + description + "'," + "DateAdded='" + uploadDate + "'," + "Price=" + price + "," + "Tax=" + tax + ","+"Quantity=" + quantity + " WHERE ProductName='"+tempProductName+"'";

				if (!name.equals("") && !uploadDate.equals("") && !tfPrice_ProdMgmt.getText().equals("") && !tfTax_ProdMgmt.getText().equals("") && !tfQuantity_ProdMgmt.getText().equals("")) 
				{
					for (int i = 0; i < products.size(); i++) 
					{
						if (products.get(i).getName().equals(tempProductName)) {
							products.set(i, new Product(products.get(i).getId(), catagoryID, brandId, name,description,uploadDate, price, tax,quantity));
						}
					}
					try 
					{
						stmt.execute(sql_query);
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
					
					bringProducts();
					
					JOptionPane.showMessageDialog(null, "Product Successfully Updated!");
					
					tfProdName_ProdMgmt.setText("");
					tfProdDesc_ProdMgmt.setText("");
					datePickerProdMgmt.setDate(new Date());
					tfPrice_ProdMgmt.setText("");
					tfTax_ProdMgmt.setText("");
					tfQuantity_ProdMgmt.setText("");

				} else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");
	    	}
	    });

	    btnDeleteProduct.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		String query_delete = "delete from products where ProductName='" + tempProductName + "'";
				try {
					stmt.execute(query_delete);
					JOptionPane.showMessageDialog(null, "Product Deleted");
					for (int i = 0; i < products.size(); i++) {
						if (products.get(i).getName().equals(tempProductName)) {
							products.remove(i);
						}
					}
					
					tfProdName_ProdMgmt.setText("");
					tfProdDesc_ProdMgmt.setText("");
					datePickerProdMgmt.setDate(new Date());
					tfPrice_ProdMgmt.setText("");
					tfTax_ProdMgmt.setText("");
					tfQuantity_ProdMgmt.setText("");
					
					bringProducts();
					
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
	    	}
	    });
	    
	    jtProducts_ProdMgmt.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				int tempCategoryId = (int) productsTableModel.getValueAt(jtProducts_ProdMgmt.getSelectedRow(), 1);
				int tempBrandId = (int) productsTableModel.getValueAt(jtProducts_ProdMgmt.getSelectedRow(), 2);

				tfProdName_ProdMgmt.setText(productsTableModel.getValueAt(jtProducts_ProdMgmt.getSelectedRow(), 3).toString());
				tfProdDesc_ProdMgmt.setText(productsTableModel.getValueAt(jtProducts_ProdMgmt.getSelectedRow(), 4).toString());
				datePickerProdMgmt.setDate(new Date());
				cbCategories_ProdMgmt.setSelectedIndex(tempCategoryId - 1);
				cbBrands_ProdMgmt.setSelectedIndex(tempBrandId - 1);
				tfPrice_ProdMgmt.setText(productsTableModel.getValueAt(jtProducts_ProdMgmt.getSelectedRow(), 6).toString());
				tfTax_ProdMgmt.setText(productsTableModel.getValueAt(jtProducts_ProdMgmt.getSelectedRow(), 7).toString());
				tfQuantity_ProdMgmt.setText(productsTableModel.getValueAt(jtProducts_ProdMgmt.getSelectedRow(), 8).toString());
				tempProductName = productsTableModel.getValueAt(jtProducts_ProdMgmt.getSelectedRow(), 3).toString();
			}
		});
	}
}
