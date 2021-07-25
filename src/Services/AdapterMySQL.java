package Services;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class AdapterMySQL {

	public Connection getConnection() {
		Connection connection = null;
		String url = "jdbc:mysql://localhost:3306/cashflow?zeroDateTimeBehavior=convertToNull&serverTimezone=UTC";
		String user = "newuser";
		String password = "yael2712";
		
		try {
			
			connection = DriverManager.getConnection(url, user, password);
			System.out.println(connection);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
