package dao;

import entity.Message;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDao {
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public void insertMessage(int activeId, int passiveId, int type, int eventId)
			throws SQLException {
		conn = ConnectionFactory.getConnection();
		String sql = "INSERT INTO message(active_id,passive_id,event_id,`type`,datetime) VALUES(?,?,?,?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, activeId);
			ps.setInt(2, passiveId);
			ps.setInt(3, eventId);
			ps.setInt(4, type);
			ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} finally {
			ps.close();
			conn.close();
		}
	}

	public List<Message> queryMessage(int userId) throws SQLException {
		conn = ConnectionFactory.getConnection();
		String sql = "SELECT `type`,event_id,`name`,datetime FROM message "
				+ "INNER JOIN `user` ON message.active_id = `user`.id "
				+ "WHERE passive_id = ?";
		List<Message> messages = new ArrayList<>();
		PreparedStatement tempPS = null;
		ResultSet tempRS = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			rs = ps.executeQuery();
			while (rs.next()) {
				int type = rs.getInt(1);
				switch (type) {
				case 0:
					messages.add(new Message("", type,
							rs.getString(3), rs.getTimestamp(4)));
					break;
				case 1:
					sql = "SELECT title FROM topic WHERE id = ?";
					tempPS = conn.prepareStatement(sql);
					tempPS.setInt(1, rs.getInt(2));
					tempRS = tempPS.executeQuery();
					if (tempRS.next()) {
						messages.add(new Message(tempRS.getString(2), type,
								rs.getString(3), rs.getTimestamp(4)));
					}
					break;
				case 2:
					sql = "SELECT title FROM topic WHERE id = ?";
					tempPS = conn.prepareStatement(sql);
					tempPS.setInt(1, rs.getInt(2));
					tempRS = tempPS.executeQuery();
					if (tempRS.next()) {
						messages.add(new Message(tempRS.getString(2), type,
								rs.getString(3), rs.getTimestamp(4)));
					}
					break;
				default:
					break;
				}
			}

		} finally {
			rs.close();
			if (tempPS != null)
				tempPS.close();
			if (tempRS != null)
				tempRS.close();
			ps.close();
			conn.close();
		}
		return messages;
	}

}
