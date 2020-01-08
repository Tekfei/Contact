package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/contact?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8";
	private static String user = "root";
	private static String password = "654321";

	public static Connection getConnection() {
		try {
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public static void main(String[] args){
		Connection c = ConnectionFactory.getConnection();
		System.out.println(c);
	}

}
