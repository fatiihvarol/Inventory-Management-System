package view.manager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dbHelper.DB;
import entities.Category;
import view.home.AdminHomeForm;
import view.home.EmployeeHomeForm;
import view.home.LoginForm;

import java.awt.Color;
import java.awt.Toolkit;
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
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class CategoryManagementForm extends JFrame
{
	static Connection connection;
	static Statement stmt;
	
	static ArrayList<Category> categories = new ArrayList<>();
	private JTable jtCategories;
	
	static DefaultTableModel myModel = new DefaultTableModel();
	Object[] columns = {"Id", "Category Name", "Description", "Date Added"};
	Object[] rows = new Object[columns.length];

	private JPanel contentPane;
	
	private JTextField tfCategoryName;
	private JTextField tfCategoryDescription;
	private JDateChooser datePickerCategory;
	
	private String tempCategoryName;
	
	private void readCategories()
	{
		boolean flag;
		
		try
		{
			connection = DB.getConnection();
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM categories");

			while (rs.next())
			{
				Category category = new Category
						(
								rs.getInt(1), 
								rs.getString(2),
								rs.getString(3),
								rs.getString(4)
						);
				
				flag = true;
				
				for (int i = 0; i < categories.size(); i++) 
				{
					if (category.getID()==categories.get(i).getID()) flag=false;
				}
				
				if (flag) categories.add(category);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void bringCategories()
	{
		myModel.getDataVector().removeAllElements();
		readCategories();
		
		if (categories.size() == 0) 
		{
			rows[0] = null;
			rows[1] = null;
			rows[2] = null;
			rows[3] = null;

			myModel.addRow(rows);
		}
		
		for (int i = 0; i < categories.size(); i++) 
		{
			rows[0] = categories.get(i).getID();
			rows[1] = categories.get(i).getCATEGORY_NAME();
			rows[2] = categories.get(i).getDESCRIPTION();
			rows[3] = categories.get(i).getDATE_ADDED();
			myModel.addRow(rows);
		}
		jtCategories.setModel(myModel);
	}

	public CategoryManagementForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(CategoryManagementForm.class.getResource("/assets/Icon_Category.png")));
		setTitle("Category Management");
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
		
		jtCategories = new JTable();
		myModel.setColumnIdentifiers(columns);
		jtCategories.setBackground(SystemColor.controlHighlight);
		jtCategories.setBounds(21, 11, 716, 301);
		scrollPane.setViewportView(jtCategories);
		
		bringCategories();
		
		JLabel lblCategoryName = new JLabel("Category Name:");
		lblCategoryName.setForeground(new Color(0, 102, 255));
		lblCategoryName.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblCategoryName.setBounds(168, 344, 129, 30);
		panel.add(lblCategoryName);
		
		JLabel lblCategoryDescription = new JLabel("Description:");
		lblCategoryDescription.setForeground(new Color(0, 102, 255));
		lblCategoryDescription.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblCategoryDescription.setBounds(168, 385, 129, 30);
		panel.add(lblCategoryDescription);
		
		tfCategoryName = new JTextField();
		tfCategoryName.setToolTipText("");
		tfCategoryName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfCategoryName.setColumns(10);
		tfCategoryName.setBounds(343, 353, 193, 25);
		panel.add(tfCategoryName);
		
		tfCategoryDescription = new JTextField();
		tfCategoryDescription.setToolTipText("");
		tfCategoryDescription.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfCategoryDescription.setColumns(10);
		tfCategoryDescription.setBounds(343, 394, 193, 25);
		panel.add(tfCategoryDescription);
		
		datePickerCategory = new JDateChooser();
	    datePickerCategory.setBounds(343, 430, 193, 25);
	    panel.add(datePickerCategory);
	    
	    JLabel lblCategoryDateAdded = new JLabel("Upload Date:");
	    lblCategoryDateAdded.setForeground(new Color(0, 102, 255));
	    lblCategoryDateAdded.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblCategoryDateAdded.setBounds(168, 430, 129, 30);
	    panel.add(lblCategoryDateAdded);
	    
	    JButton btnAddCategory = new JButton("ADD");
	    btnAddCategory.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		String name = null, description = null, uploadDate = null;
				name = tfCategoryName.getText();
				description = tfCategoryDescription.getText();
				uploadDate = datePickerCategory.getDate().toString();
				
				int temp_id;
				
				if (categories.size() != 0) 
				{
					temp_id = categories.get(categories.size() - 1).getID() + 1;
				} 
				else temp_id = 1;
				
				String sql_query = "INSERT INTO categories(CategoryID,CategoryName,CategoryDescription,DateAdded) VALUES ("
						+ temp_id + ",'" + name + "','" + description + "','" + uploadDate + "')";
				
				if (!name.equals("") && !uploadDate.equals("")) 
				{
					try 
					{
						stmt.execute(sql_query);
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, "Category Added");
					
					bringCategories();
					
					tfCategoryName.setText("");
					tfCategoryDescription.setText("");
					datePickerCategory.setDate(new Date());
				} 
				
				else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");
	    	}
	    });
	    btnAddCategory.setForeground(Color.WHITE);
	    btnAddCategory.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnAddCategory.setBackground(new Color(0, 102, 255));
	    btnAddCategory.setBounds(44, 497, 193, 37);
	    panel.add(btnAddCategory);
	    
	    JButton btnUpdateCategory = new JButton("UPDATE");
	    btnUpdateCategory.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		String name = null, description = null, uploadDate = null;
				name = tfCategoryName.getText();
				description = tfCategoryDescription.getText();
				uploadDate = datePickerCategory.getDate().toString();
				
				String sql_query = "UPDATE categories SET CategoryName='" + name + "'," + "CategoryDescription='"
						+ description + "'," + "DateAdded='" + uploadDate + "'WHERE CategoryName='" + tempCategoryName + "'";
				
				if (!name.equals("") && !uploadDate.equals("")) 
				{
					for (int i = 0; i < categories.size(); i++) 
					{
						if (categories.get(i).getCATEGORY_NAME().equals(tempCategoryName)) 
						{
							categories.set(i, new Category(categories.get(i).getID(), name, description, uploadDate));
						}
					}

					bringCategories();

					try 
					{
						stmt.execute(sql_query);
					} 
					catch (SQLException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Category Updated !");
					
					tfCategoryName.setText("");
					tfCategoryDescription.setText("");
					datePickerCategory.setDate(new Date());

				} 
				else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");
	    	}
	    });
	    btnUpdateCategory.setForeground(Color.WHITE);
	    btnUpdateCategory.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnUpdateCategory.setBackground(new Color(0, 102, 255));
	    btnUpdateCategory.setBounds(247, 497, 193, 37);
	    panel.add(btnUpdateCategory);
	    
	    JButton btnDeleteCategory = new JButton("DELETE");
	    btnDeleteCategory.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
				String query_delete = "delete from categories where CategoryName='" + tempCategoryName +"'";
				
				try 
				{
					stmt.execute(query_delete);
					JOptionPane.showMessageDialog(null, "Category Deleted");
					
					for (int i = 0; i < categories.size(); i++) 
					{
						if (categories.get(i).getCATEGORY_NAME().equals(tempCategoryName)) 
						{
							categories.remove(i);
						}
					}
					
					tfCategoryName.setText("");
					tfCategoryDescription.setText("");
					datePickerCategory.setDate(new Date());
					
					bringCategories();
					
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
	    	}
	    });
	    btnDeleteCategory.setForeground(Color.WHITE);
	    btnDeleteCategory.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnDeleteCategory.setBackground(new Color(0, 102, 255));
	    btnDeleteCategory.setBounds(450, 497, 193, 37);
	    panel.add(btnDeleteCategory);
	    
	    JButton btnGoBack_CategMgmt = new JButton("");
	    btnGoBack_CategMgmt.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
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
	    btnGoBack_CategMgmt.setIcon(new ImageIcon(CategoryManagementForm.class.getResource("/assets/Icon_Goback.png")));
	    btnGoBack_CategMgmt.setForeground(Color.WHITE);
	    btnGoBack_CategMgmt.setBackground(new Color(0, 102, 255));
	    btnGoBack_CategMgmt.setBounds(653, 497, 58, 37);
	    panel.add(btnGoBack_CategMgmt);
	    
	    jtCategories.addMouseListener(new MouseAdapter() { // TableOnClick
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				tfCategoryName.setText(myModel.getValueAt(jtCategories.getSelectedRow(), 1).toString());
				tfCategoryDescription.setText(myModel.getValueAt(jtCategories.getSelectedRow(), 2).toString());
				datePickerCategory.setDate(new Date());
				tempCategoryName = tfCategoryName.getText();
				//.setDate(new Date(myModel.getValueAt(jtCategories.getSelectedRow(), 3).toString()));
			}
		});
	}
}
