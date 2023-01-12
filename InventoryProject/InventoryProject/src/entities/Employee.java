	package entities;

public class Employee
{
	private int ID;
	private int USER_TYPE;
    private String FIRST_NAME;
    private String LAST_NAME;
    private String USER_NAME;
    private String EMAIL;
    private String PASSWORD;
    private int AGE;
    private int GENDER;
    private String ADDRESS;
    private String PHONE_NUMBER;
    private String POSITION;
    private String SALARY;
    
    public Employee() {}
    
    public Employee
    (
    		int iD, 
    		int uSER_TYPE, 
    		String fIRST_NAME, 
    		String lAST_NAME, 
    		String uSER_NAME, 
    		String eMAIL,
			String pASSWORD, 
			String pHONE_NUMBER, 
			String aDDRESS, 
			int aGE, 
			int gENDER, 
			String pOSITION,
			String sALARY
	){
		ID = iD;
		USER_TYPE = uSER_TYPE;
		FIRST_NAME = fIRST_NAME;
		LAST_NAME = lAST_NAME;
		USER_NAME = uSER_NAME;
		EMAIL = eMAIL;
		PASSWORD = pASSWORD;
		AGE = aGE;
		GENDER = gENDER;
		ADDRESS = aDDRESS;
		PHONE_NUMBER = pHONE_NUMBER;
		POSITION = pOSITION;
		SALARY = sALARY;
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

	public int getAge() { return AGE; }
	public void setAge(int _AGE) { this.AGE = _AGE; }

	public int getGender() { return GENDER; }
	public void setGender(int _GENDER) { this.GENDER = _GENDER; }

	public String getAddress() { return ADDRESS; }
	public void setAddress(String _ADDRESS) { this.ADDRESS = _ADDRESS; }

	public String getPhoneNumber() { return PHONE_NUMBER; }
	public void setPhoneNumber(String _PHONE_NUMBER) { this.PHONE_NUMBER = _PHONE_NUMBER; }
	
	public String getPosition() { return POSITION; }
	public void setPosition(String _POSITION) { this.POSITION = _POSITION; }
	
	public String getSalary() { return SALARY; }
	public void setSalary(String _SALARY) { this.SALARY = _SALARY; }
}
