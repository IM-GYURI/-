package DB;

import java.sql.*;

public class DBConnection
{
	public static Connection connectToDB() {
		String dbPath = "C:/Users/USER/Desktop/제로베이스/Mission1/WiFiInfo.sqlite";
		String url = "jdbc:sqlite:" + dbPath;
		
		Connection connection = null;
	
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		try {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		try {
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
            if (connection != null && ! connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
}
