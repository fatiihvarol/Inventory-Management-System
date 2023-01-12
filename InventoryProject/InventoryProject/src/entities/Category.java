package entities;

public class Category 
{
	private int ID;
    private String CATEGORY_NAME;
    private String DESCRIPTION;
    private String DATE_ADDED;
    
    public Category() {}

	public Category(int iD, String cATEGORY_NAME, String dESCRIPTION, String dATE_ADDED) 
	{
		ID = iD;
		CATEGORY_NAME = cATEGORY_NAME;
		DESCRIPTION = dESCRIPTION;
		DATE_ADDED = dATE_ADDED;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getCATEGORY_NAME() {
		return CATEGORY_NAME;
	}

	public void setCATEGORY_NAME(String cATEGORY_NAME) {
		CATEGORY_NAME = cATEGORY_NAME;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getDATE_ADDED() {
		return DATE_ADDED;
	}

	public void setDATE_ADDED(String dATE_ADDED) {
		DATE_ADDED = dATE_ADDED;
	}
}
