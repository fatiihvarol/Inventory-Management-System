package entities;

public class Order 
{
	private int orderId;
	private int employeeId;
	private int productId;
	private int customerId;
	private String orderNumber;
	private int orderQuantity;
	private String orderDate;
	
	public Order() {}

	public Order(int orderId, int employeeId, int productId, int customerId, String orderNumber, int orderQuantity, String orderDate) {
		super();
		this.orderId = orderId;
		this.employeeId = employeeId;
		this.productId = productId;
		this.customerId = customerId;
		this.orderNumber = orderNumber;
		this.orderQuantity = orderQuantity;
		this.orderDate = orderDate;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public int getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
}
