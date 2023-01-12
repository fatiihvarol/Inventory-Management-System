package entities;

public class Product 
{
	private int id;
	private int categoryId;
	private int brandId;
	private String name;
	private String description;
	private String dateAdded;
	private float price;
	private float tax;
	private int quantity;
	
	public Product() {}

	public Product(int id, int categoryId, int brandId, String name, String description, String dateAdded, float price,
			float tax, int quantity) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.brandId = brandId;
		this.name = name;
		this.description = description;
		this.dateAdded = dateAdded;
		this.price = price;
		this.tax = tax;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getBrandId() {
		return brandId;
	}

	public void setBrandId(int brandId) {
		this.brandId = brandId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(String dateAdded) {
		this.dateAdded = dateAdded;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
