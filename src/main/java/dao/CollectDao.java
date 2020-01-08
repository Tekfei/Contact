package dao;

import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollectDao {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public void insertCollection(int userId, int topicId) throws SQLException {
		conn = ConnectionFactory.getConnection();
		String sql = "INSERT INTO collect(user_id, topic_id, datetime) VALUES(?, ?, ?)";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, topicId);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
	}

	public void cancelCollection(int userId, int topicId) throws SQLException {
		conn = ConnectionFactory.getConnection();
		String sql = "DELETE FROM collect WHERE user_id = ? AND topic_id = ?";

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.setInt(2, topicId);
			ps.executeUpdate();
		}finally {
			ps.close();
			conn.close();
		}
	}

	public List<Integer> queryCollection(int userId) throws SQLException {
		conn = ConnectionFactory.getConnection();

		String sql = "SELECT * FROM collect WHERE user_id = ? order by topic_id desc";
		List<Integer> integers = new ArrayList<>();

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);

			rs = ps.executeQuery();
			while (rs.next()) {
				integers.add(rs.getInt(3));
			}
		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
		return integers;
	}

}
