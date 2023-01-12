package entities;

public class Supplier {
	private int ID;
	private String FIRST_NAME;
	private String LAST_NAME;

	private String COMPANY;
	private int CATEGORY_ID;
	private String PHONE_NUMBER;
	private String EMAIL;
	private String ADDRESS;

	public Supplier(int iD, String fIRST_NAME, String lAST_NAME, String cOMPANY, int cATEGORY_ID, String pHONE_NUMBER,String eMAIL, String aDDRESS) {
		ID = iD;
		FIRST_NAME = fIRST_NAME;
		LAST_NAME = lAST_NAME;
		COMPANY = cOMPANY;
		CATEGORY_ID = cATEGORY_ID;
		PHONE_NUMBER = pHONE_NUMBER;
		EMAIL = eMAIL;
		ADDRESS = aDDRESS;
	}

	public int getID() {
		return ID;
	}

	public void setID(int _ID) {
		this.ID = _ID;
	}

	public int getCategoryID() {
		return CATEGORY_ID;
	}

	public void setCategoryID(int _CATEGORY_ID) {
		this.CATEGORY_ID = _CATEGORY_ID;
	}

	public String getCompany() {
		return COMPANY;
	}

	public void setCompany(String _COMPANY) {
		this.COMPANY = _COMPANY;
	}

	public String getFirstName() {
		return FIRST_NAME;
	}

	public void setFirstaname(String _firstName) {
		this.FIRST_NAME = _firstName;
	}

	public String getLastName() {
		return LAST_NAME;
	}

	public void setLastaname(String _lastName) {
		this.LAST_NAME = _lastName;
	}

	public String getEmail() {
		return EMAIL;
	}

	public void setEmail(String _EMAIL) {
		this.EMAIL = _EMAIL;
	}

	public String getAddress() {
		return ADDRESS;
	}

	public void setAddress(String _ADDRESS) {
		this.ADDRESS = _ADDRESS;
	}

	public String getPhoneNumber() {
		return PHONE_NUMBER;
	}

	public void setPhoneNumber(String _PHONE_NUMBER) {
		this.PHONE_NUMBER = _PHONE_NUMBER;
	}
}
