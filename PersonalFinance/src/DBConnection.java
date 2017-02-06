
/**
 * This is a utility class to connect to the backend
 * system, in this case mysql server
 * 
 * @version: v.1.0 - 13 dic 2015 19:18:01 
 * @author:  Marco Canavese
 */
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DBConnection
{
	private Connection conn = null;

	public static Connection getConnection() throws Exception
	{
		try
		{
			// load the mysql database driver
			Class.forName("com.mysql.jdbc.Driver");

			// establish the connection with the db
			Connection conn = DriverManager
					.getConnection("jdbc:mysql://localhost/spese", "root", "*yourmysqlpassword");
			// JOptionPane.showMessageDialog(null, "Connection established");
			return conn;

		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, e,
					"Error establishing connection", 0);
			return null;
		}
	}
}
