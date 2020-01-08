package dao;

import java.sql.*;

import util.ConnectionFactory;

public class FeedBackDao {
	private Connection conn;
	private PreparedStatement ps;
	
	public boolean insertIssue(int userId, String content) throws SQLException {
		conn = ConnectionFactory.getConnection();

		String sql = "INSERT INTO feed_back(user_id, content, datetime) VALUES(?, ?, ?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setString(2, content);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} finally {
			ps.close();
			conn.close();
		}
		return true;
	}
}
