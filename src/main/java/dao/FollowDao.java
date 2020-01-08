package dao;

import entity.Follow;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FollowDao {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public List<Integer> getAllFollowedUserId(int userId) throws SQLException {
		conn = ConnectionFactory.getConnection();
		String sql = "SELECT passive_id FROM follow WHERE active_id = ? ORDER BY id DESC";
		List<Integer> integers = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				integers.add(rs.getInt(1));
			}
		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
		return integers;
	}
	
	public List<Follow> getFollow(int userId) throws SQLException {
		conn = ConnectionFactory.getConnection();
		String sql = "SELECT passive_id,`name`,photo_id,datetime FROM follow " +
				"INNER JOIN `user` ON follow.passive_id = `user`.id " +
				"WhERE active_id = ? " +
				"ORDER BY follow.id DESC";
		List<Follow> follows = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Follow follow = new Follow(rs.getInt(1),rs.getString(2),
						rs.getInt(3),rs.getTimestamp(4));
				follows.add(follow);
			}
		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
		return follows;
	}
	
	public List<Follow> getPassiveFollow(int userId) throws SQLException {
		conn = ConnectionFactory.getConnection();
		String sql = "SELECT active_id,`name`,photo_id,datetime FROM follow " +
				"INNER JOIN `user` ON follow.active_id = `user`.id " +
				"WhERE passive_id = ? " +
				"ORDER BY follow.Id DESC";
		List<Follow> follows = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				Follow follow = new Follow(rs.getInt(1),rs.getString(2),
						rs.getInt(3),rs.getTimestamp(4));
				follows.add(follow);
			}
		} finally {
			rs.close();
			ps.close();
			conn.close();
		}
		return follows;
	}
	
	public void insertFollow(int activeId, int passiveId) throws SQLException{
		conn = ConnectionFactory.getConnection();
		String sql = "INSERT INTO follow(active_id,passive_id,datetime) VALUES(?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,activeId);
			ps.setInt(2, passiveId);
			ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} finally {
			ps.close();
			conn.close();
		}
	}
	
	public void cancelAttention(int activeId,int passiveId) throws SQLException{
		conn = ConnectionFactory.getConnection();
		String sql = "DELETE FROM follow WHERE active_id = ? AND passive_id = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1,activeId);
			ps.setInt(2, passiveId);
			ps.executeUpdate();
		} finally {
			ps.close();
			conn.close();
		}
	}

}
