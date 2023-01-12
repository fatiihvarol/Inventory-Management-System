package view.home;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.manager.BrandManagementForm;
import view.manager.CategoryManagementForm;
import view.manager.EmployeeManagementForm;
import view.manager.OrderManagementForm;
import view.manager.ProductManagementForm;
import view.manager.SupplierManagementForm;

import java.awt.Panel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class AdminHomeForm extends JFrame {

	private JPanel contentPane;

	public AdminHomeForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(AdminHomeForm.class.getResource("/assets/Icon_Admin.png")));
		setTitle("Administrator");
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
		
		Panel panel2 = new Panel();
		panel2.setBackground(new Color(0, 102, 255));
		panel2.setBounds(0, 0, 1184, 66);
		panel.add(panel2);
		panel2.setLayout(null);
		
		JLabel lblHeader = new JLabel("Inventory Management System");
		lblHeader.setBounds(393, 11, 382, 44);
		lblHeader.setForeground(Color.WHITE);
		lblHeader.setFont(new Font("Century Gothic", Font.BOLD, 24));
		panel2.add(lblHeader);
		
		JLabel lblLogOut_Admin = new JLabel("Log Out");
		lblLogOut_Admin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				LoginForm login = new LoginForm();
				login.setLocationRelativeTo(null);
				login.setVisible(true);
				setVisible(false);
			}
		});
		lblLogOut_Admin.setIcon(new ImageIcon(AdminHomeForm.class.getResource("/assets/Icon_Logout.png")));
		lblLogOut_Admin.setForeground(Color.WHITE);
		lblLogOut_Admin.setFont(new Font("Century Gothic", Font.BOLD, 12));
		lblLogOut_Admin.setBounds(10, 11, 150, 41);
		panel2.add(lblLogOut_Admin);
		
		JLabel lblNewLabel = new JLabel("Administrator Home Page");
		lblNewLabel.setForeground(new Color(0, 102, 255));
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 18));
		lblNewLabel.setBounds(448, 72, 246, 32);
		panel.add(lblNewLabel);
		
		JLabel lblManageProducts = new JLabel("");
		lblManageProducts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				ProductManagementForm pMgmt = new ProductManagementForm();
				pMgmt.setVisible(true);
				pMgmt.setLocationRelativeTo(null);
				setVisible(false);
			}
		});
		lblManageProducts.setIcon(new ImageIcon(AdminHomeForm.class.getResource("/assets/icon-product-1.png")));
		lblManageProducts.setBounds(180, 144, 80, 80);
		panel.add(lblManageProducts);
		
		JLabel lblProducts = new JLabel("Products");
		lblProducts.setForeground(new Color(0, 102, 255));
		lblProducts.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblProducts.setBounds(190, 222, 57, 27);
		panel.add(lblProducts);
		
		JLabel lblManageOrders = new JLabel("");
		lblManageOrders.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				OrderManagementForm oMgmt = new OrderManagementForm();
				oMgmt.setVisible(true);
				oMgmt.setLocationRelativeTo(null);
				setVisible(false);
			}
		});
		lblManageOrders.setIcon(new ImageIcon(AdminHomeForm.class.getResource("/assets/Icon_Order.png")));
		lblManageOrders.setBounds(537, 144, 80, 80);
		panel.add(lblManageOrders);
		
		JLabel lblOrders = new JLabel("Orders");
		lblOrders.setForeground(new Color(0, 102, 255));
		lblOrders.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblOrders.setBounds(554, 222, 57, 27);
		panel.add(lblOrders);
		
		JLabel lblManageCategories = new JLabel("");
		lblManageCategories.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				CategoryManagementForm cMgmt = new CategoryManagementForm();
				cMgmt.setVisible(true);
				cMgmt.setLocationRelativeTo(null);
				setVisible(false);
			}
		});
		lblManageCategories.setIcon(new ImageIcon(AdminHomeForm.class.getResource("/assets/Icon_Category.png")));
		lblManageCategories.setBounds(180, 383, 80, 80);
		panel.add(lblManageCategories);
		
		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setForeground(new Color(0, 102, 255));
		lblCategories.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblCategories.setBounds(180, 463, 80, 27);
		panel.add(lblCategories);
		
		JLabel lblManageBrands = new JLabel("");
		lblManageBrands.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				BrandManagementForm bMgmt = new BrandManagementForm();
				bMgmt.setVisible(true);
				bMgmt.setLocationRelativeTo(null);
				setVisible(false);
			}
		});
		lblManageBrands.setIcon(new ImageIcon(AdminHomeForm.class.getResource("/assets/Icon_Brand4.png")));
		lblManageBrands.setBounds(502, 378, 150, 85);
		panel.add(lblManageBrands);
		
		JLabel lblBrands = new JLabel("Brands");
		lblBrands.setForeground(new Color(0, 102, 255));
		lblBrands.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblBrands.setBounds(556, 463, 80, 27);
		panel.add(lblBrands);
		
		JLabel lblManageEmployees = new JLabel("");
		lblManageEmployees.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				EmployeeManagementForm eMgmt = new EmployeeManagementForm();
				eMgmt.setVisible(true);
				eMgmt.setLocationRelativeTo(null);
				setVisible(false);
			}
		});
		lblManageEmployees.setIcon(new ImageIcon(AdminHomeForm.class.getResource("/assets/Icon_User.png")));
		lblManageEmployees.setBounds(848, 144, 80, 80);
		panel.add(lblManageEmployees);
		
		JLabel lblUsers = new JLabel(" Employees");
		lblUsers.setForeground(new Color(0, 102, 255));
		lblUsers.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblUsers.setBounds(848, 222, 115, 27);
		panel.add(lblUsers);
		
		JLabel lblManageSuppliers = new JLabel("");
		lblManageSuppliers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SupplierManagementForm sMgmt = new SupplierManagementForm();
				sMgmt.setVisible(true);
				sMgmt.setLocationRelativeTo(null);
				setVisible(false);
			}
		});
		lblManageSuppliers.setIcon(new ImageIcon(AdminHomeForm.class.getResource("/assets/Icon_Suppliers.png")));
		lblManageSuppliers.setBounds(848, 383, 80, 80);
		panel.add(lblManageSuppliers);
		
		JLabel lblSuppliers = new JLabel("  Suppliers");
		lblSuppliers.setForeground(new Color(0, 102, 255));
		lblSuppliers.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblSuppliers.setBounds(854, 463, 74, 27);
		panel.add(lblSuppliers);
	}
}
