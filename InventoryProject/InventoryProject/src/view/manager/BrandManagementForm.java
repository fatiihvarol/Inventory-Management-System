package view.manager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import dbHelper.DB;
import entities.Brand;
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
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class BrandManagementForm extends JFrame 
{
	static Connection connection;
	static Statement stmt;
	
	static ArrayList<Brand> brands = new ArrayList<>();
	private JTable jtBrands;
	
	static DefaultTableModel myModel = new DefaultTableModel();
	Object[] columns = {"Id", "Brand Name", "Brand Description", "Date Added"};
	Object[] rows = new Object[columns.length];

	private JPanel contentPane;
	private JTextField tfBrandName;
	private JTextField tfBrandDescription;
	
	private String tempBrandName;
	
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
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private void bringBrands()
	{
		myModel.getDataVector().removeAllElements();
		readBrands();
		
		if (brands.size() == 0) 
		{
			rows[0] = null;
			rows[1] = null;
			rows[2] = null;
			rows[3] = null;

			myModel.addRow(rows);
		}
		
		for (int i = 0; i < brands.size(); i++) 
		{
			rows[0] = brands.get(i).getBrandId();
			rows[1] = brands.get(i).getBrandName();
			rows[2] = brands.get(i).getBrandDescription();
			rows[3] = brands.get(i).getUploadDate();
			myModel.addRow(rows);
		}
		jtBrands.setModel(myModel);
	}

	public BrandManagementForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(BrandManagementForm.class.getResource("/assets/Icon_Brand4.png")));
		setTitle("Brand Management");
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
		
		jtBrands = new JTable();
		myModel.setColumnIdentifiers(columns);
		jtBrands.setBackground(SystemColor.controlHighlight);
		jtBrands.setBounds(21, 11, 716, 301);
		scrollPane.setViewportView(jtBrands);
		
		bringBrands();
		
		JLabel lblBrandName = new JLabel("Brand Name:");
		lblBrandName.setForeground(new Color(0, 102, 255));
		lblBrandName.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblBrandName.setBounds(168, 344, 129, 30);
		panel.add(lblBrandName);
		
		JLabel lblBrandDescription = new JLabel("Description:");
		lblBrandDescription.setForeground(new Color(0, 102, 255));
		lblBrandDescription.setFont(new Font("Century Gothic", Font.BOLD, 16));
		lblBrandDescription.setBounds(168, 385, 129, 30);
		panel.add(lblBrandDescription);
		
		tfBrandName = new JTextField();
		tfBrandName.setToolTipText("");
		tfBrandName.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfBrandName.setColumns(10);
		tfBrandName.setBounds(343, 353, 193, 25);
		panel.add(tfBrandName);
		
		tfBrandDescription = new JTextField();
		tfBrandDescription.setToolTipText("");
		tfBrandDescription.setFont(new Font("Century Gothic", Font.BOLD, 14));
		tfBrandDescription.setColumns(10);
		tfBrandDescription.setBounds(343, 394, 193, 25);
		panel.add(tfBrandDescription);
		
		JDateChooser datePickerBrand = new JDateChooser();
	    datePickerBrand.setBounds(343, 430, 193, 25);
	    panel.add(datePickerBrand);
	    
	    JLabel lblBrandDateAdded = new JLabel("Upload Date:");
	    lblBrandDateAdded.setForeground(new Color(0, 102, 255));
	    lblBrandDateAdded.setFont(new Font("Century Gothic", Font.BOLD, 16));
	    lblBrandDateAdded.setBounds(168, 430, 129, 30);
	    panel.add(lblBrandDateAdded);
	    
	    JButton btnAddBrand = new JButton("ADD");
	    btnAddBrand.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		String name = null, description = null, uploadDate = null;
				name = tfBrandName.getText();
				description = tfBrandDescription.getText();
				uploadDate = datePickerBrand.getDate().toString();
				
				int temp_id;
				
				if (brands.size() != 0) 
				{
					temp_id = brands.get(brands.size() - 1).getBrandId() + 1;
				} 
				else temp_id = 1;
				
				String sql_query = "INSERT INTO brands(BrandID,BrandName,BrandDescription,DateAdded) VALUES ("
						+ temp_id + ",'" + name + "','" + description + "','" + uploadDate + "')";
				
				if (!name.equals("") && !description.equals("") && !uploadDate.equals("")) 
				{
					try 
					{
						stmt.execute(sql_query);
					} 
					catch (SQLException e1) 
					{
						e1.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, "Brand Added!");
					
					bringBrands();
					
					tfBrandName.setText("");
					tfBrandDescription.setText("");
					datePickerBrand.setDate(new Date());
				} 
				
				else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces!");
	    	}
	    });
	    btnAddBrand.setForeground(Color.WHITE);
	    btnAddBrand.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnAddBrand.setBackground(new Color(0, 102, 255));
	    btnAddBrand.setBounds(44, 497, 193, 37);
	    panel.add(btnAddBrand);
	    
	    JButton btnUpdateBrand = new JButton("UPDATE");
	    btnUpdateBrand.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		String name = null, description = null, uploadDate = null;
				name = tfBrandName.getText();
				description = tfBrandDescription.getText();
				uploadDate = datePickerBrand.getDate().toString();
				
				String sql_query = "UPDATE brands SET BrandName='" + name + "'," + "BrandDescription='"
						+ description + "'," + "DateAdded='" + uploadDate + "'WHERE BrandName='" + tempBrandName + "'";
				
				if (!name.equals("") && !uploadDate.equals("")) 
				{
					for (int i = 0; i < brands.size(); i++) 
					{
						if (brands.get(i).getBrandName().equals(tempBrandName)) 
						{
							brands.set(i, new Brand(brands.get(i).getBrandId(), name, description, uploadDate));
						}
					}

					bringBrands();

					try 
					{
						stmt.execute(sql_query);
					} 
					catch (SQLException e1) 
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Brand Updated!");
					
					tfBrandName.setText("");
					tfBrandDescription.setText("");
					datePickerBrand.setDate(new Date());

				} 
				else JOptionPane.showMessageDialog(null, "Please Fill Empty Spaces");
	    	}
	    });
	    btnUpdateBrand.setForeground(Color.WHITE);
	    btnUpdateBrand.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnUpdateBrand.setBackground(new Color(0, 102, 255));
	    btnUpdateBrand.setBounds(247, 497, 193, 37);
	    panel.add(btnUpdateBrand);
	    
	    JButton btnDeleteBrand = new JButton("DELETE");
	    btnDeleteBrand.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) 
	    	{
	    		String query_delete = "delete from brands where BrandName='" + tempBrandName +"'";
				
				try 
				{
					stmt.execute(query_delete);
					JOptionPane.showMessageDialog(null, "Category Deleted");
					
					for (int i = 0; i < brands.size(); i++) 
					{
						if (brands.get(i).getBrandName().equals(tempBrandName)) 
						{
							brands.remove(i);
						}
					}
					
					tfBrandName.setText("");
					tfBrandDescription.setText("");
					datePickerBrand.setDate(new Date());
					
					bringBrands();
					
				} 
				catch (SQLException e1) 
				{
					e1.printStackTrace();
				}
	    	}
	    });
	    btnDeleteBrand.setForeground(Color.WHITE);
	    btnDeleteBrand.setFont(new Font("Century Gothic", Font.BOLD, 14));
	    btnDeleteBrand.setBackground(new Color(0, 102, 255));
	    btnDeleteBrand.setBounds(450, 497, 193, 37);
	    panel.add(btnDeleteBrand);
	    
	    JButton btnGoBack_BrandMgmt = new JButton("");
	    btnGoBack_BrandMgmt.addMouseListener(new MouseAdapter() {
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
	    btnGoBack_BrandMgmt.setIcon(new ImageIcon(BrandManagementForm.class.getResource("/assets/Icon_Goback.png")));
	    btnGoBack_BrandMgmt.setForeground(Color.WHITE);
	    btnGoBack_BrandMgmt.setBackground(new Color(0, 102, 255));
	    btnGoBack_BrandMgmt.setBounds(653, 497, 58, 37);
	    panel.add(btnGoBack_BrandMgmt);
	    
	    jtBrands.addMouseListener(new MouseAdapter() { // TableOnClick
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				tfBrandName.setText(myModel.getValueAt(jtBrands.getSelectedRow(), 1).toString());
				tfBrandDescription.setText(myModel.getValueAt(jtBrands.getSelectedRow(), 2).toString());
				datePickerBrand.setDate(new Date());
				tempBrandName = tfBrandName.getText();
				//.setDate(new Date(myModel.getValueAt(jtCategories.getSelectedRow(), 3).toString()));
			}
		});
	}
}
