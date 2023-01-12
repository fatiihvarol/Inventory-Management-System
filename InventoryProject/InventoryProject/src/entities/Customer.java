package entities;

public class Customer
{
	private int ID;
    private String FIRST_NAME;
    private String LAST_NAME;
    private String PHONE_NUMBER;
    private String EMAIL;
    private String ADDRESS;
    private String USER_NAME;
    private String PASSWORD;
    private String AGE;
    private int GENDER;
	private int USER_TYPE;
    
    public Customer() {}
    
    public Customer(int iD, String fIRST_NAME, String lAST_NAME, String pHONE_NUMBER, String eMAIL, String aDDRESS,
			String uSER_NAME, String pASSWORD, String aGE, int gENDER, int uSER_TYPE) {
		super();
		ID = iD;
		FIRST_NAME = fIRST_NAME;
		LAST_NAME = lAST_NAME;
		PHONE_NUMBER = pHONE_NUMBER;
		EMAIL = eMAIL;
		ADDRESS = aDDRESS;
		USER_NAME = uSER_NAME;
		PASSWORD = pASSWORD;
		AGE = aGE;
		GENDER = gENDER;
		USER_TYPE = uSER_TYPE;
	}
    
	public int getID() { return ID; }
    public void setID (int _ID) { this.ID = _ID; }
    
    public int getUserType() { return USER_TYPE; }
	public void setUserType(int _USER_TYPE) { this.USER_TYPE = _USER_TYPE; }

	public String getFirstName() { return FIRST_NAME; }
    public void setFirstaname (String _firstName) { this.FIRST_NAME = _firstName; }
    
    public String getLastName() { return LAST_NAME; }
    public void setLastaname (String _lastName) { this.LAST_NAME = _lastName; }

    public String getUserName() { return USER_NAME; }
    public void setUserName(String userName) { this.USER_NAME = userName; }

    public String getPassword() { return PASSWORD; }
    public void setPassword(String password) { this.PASSWORD = password; }

	public String getEmail() { return EMAIL; }
	public void setEmail(String _EMAIL) { this.EMAIL = _EMAIL; }

	public String getAge() { return AGE; }
	public void setAge(String _AGE) { this.AGE = _AGE; }

	public int getGender() { return GENDER; }
	public void setGender(int _GENDER) { this.GENDER = _GENDER; }

	public String getAddress() { return ADDRESS; }
	public void setAddress(String _ADDRESS) { this.ADDRESS = _ADDRESS; }

	public String getPhoneNumber() { return PHONE_NUMBER; }
	public void setPhoneNumber(String _PHONE_NUMBER) { this.PHONE_NUMBER = _PHONE_NUMBER; }
}
