package dbHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB 
{
	Statement stmt = null;
	
    private final static String className = "com.mysql.jdbc.Driver";
    private final static String url = "jdbc:mysql://localhost/inventorymanagementdb?useSSL=false&allowPublicKeyRetrieval=true";
    private final static String user = "root";
    private final static String password = "root";
    private static Connection connection;

    public static Connection getConnection() 
    {
        if (connection == null) 
        {
            try 
            {
                Class.forName(className);
                connection = DriverManager.getConnection(url, user, password);
            } 
            catch (ClassNotFoundException ex) 
            {
                ex.printStackTrace();
            } 
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }
}
