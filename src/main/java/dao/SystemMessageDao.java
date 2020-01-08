package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import util.ConnectionFactory;
import entity.SystemMessage;


public class SystemMessageDao {
	
	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public void insertSystemMessage(String content)
			throws SQLException {
		conn = ConnectionFactory.getConnection();
		String sql = "INSERT INTO system_message(content,datetime) VALUES(?,?)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, content);
			ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			ps.executeUpdate();
		} finally {
			ps.close();
			conn.close();
		}
	}

	public List<SystemMessage> getSystemMessage() throws SQLException {
		conn = ConnectionFactory.getConnection();
		String sql = "SELECT content,datetime FROM system_message";
		List<SystemMessage> systemMessages = new ArrayList<>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				systemMessages.add(new SystemMessage(rs.getString(1),rs.getTimestamp(2)));
			}
		} finally {
			ps.close();
			rs.close();
			conn.close();
		}
		return systemMessages;
	}

}
