package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.ConnectionFactory;
import entity.Activity;

public class ActivityDaoOld {

    private Connection conn = null;
    private PreparedStatement ps;
    private ResultSet rs;

    /**
     * 获得发帖表信息
     */
    public List<Map<String, Object>> queryAllActivity() throws SQLException{

        List<Map<String, Object>> list = new ArrayList<>();
        conn = ConnectionFactory.getConnection();
        String sql = "SELECT activity.id, title, content, user_id, status, Activity.datetime, name FROM activity " +
                "INNER JOIN user ON activity.user_id = user.id " +
                "ORDER BY activity.id DESC";
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            for (int i = 0; rs.next(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt(1));
                map.put("title", rs.getString(2));
                map.put("content", rs.getString(3));
                map.put("userId", rs.getInt(4));
                map.put("status", rs.getInt(5));
                map.put("datetime", rs.getString(6));
                map.put("userName", rs.getString(7));
                list.add(i, map);
            }
        } finally {
            ps.close();
            rs.close();
            conn.close();
        }
        return list;
    }

    /**
     * 通过userId查询发帖表信息
     */
    public List<Map<String, Object>> queryActivityByUserId(int userId) throws SQLException{

        List<Map<String, Object>> list = new ArrayList<>();
        conn = ConnectionFactory.getConnection();
        String sql = "SELECT activity.id, title, content, user_id, status, activity.datetime, name FROM activity " +
                "INNER JOIN user ON activity.user_id = user.id " +
                "WHERE activity.user_id=? " +
                "ORDER BY activity.id DESC";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            for (int i = 0; rs.next(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", rs.getInt(1));
                map.put("title", rs.getString(2));
                map.put("content", rs.getString(3));
                map.put("userId", rs.getInt(4));
                map.put("status", rs.getInt(5));
                map.put("datetime", rs.getString(6));
                map.put("userName", rs.getString(7));
                list.add(i, map);
            }
        } finally {
            ps.close();
            rs.close();
            conn.close();
        }
        return list;
    }

    /**
     * 向发帖表添加信息
     */
    public void insertActivity(Activity activity) throws SQLException{
        try {
            conn = ConnectionFactory.getConnection();
            String sql = "INSERT INTO activity(user_id,title,content,status,datetime) VALUES(?,?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, activity.getUserId());
            ps.setString(2, activity.getTitle());
            ps.setString(3, activity.getContent());
            ps.setInt(4, activity.getStatus());
            ps.setTimestamp(5, new Timestamp(activity.getDatetime().getTime()));
            ps.executeUpdate();
        } finally {
            ps.close();
            conn.close();
        }
    }

    /**
     * 通过ActivityId修改信息
     */
    public void updateActivity(Activity activity) throws SQLException {
        conn = ConnectionFactory.getConnection();
        String sql = "UPDATE activity SET title='" + activity.getTitle()
                + "',content='" + activity.getContent()
                + "',status=" + activity.getStatus()
                + " where id=" + activity.getId();
        try {
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } finally {
            ps.close();
            conn.close();
        }
    }

    /**
     * 通过ActivityId修改信息
     */
    public void updateStatus(int status, int activityId) throws SQLException {
        conn = ConnectionFactory.getConnection();
        String sql = "UPDATE activity SET status=" + status
                + " where id=" + activityId;
        try {
            ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } finally {
            ps.close();
            conn.close();
        }
    }

    /**
     * 通过ActivityId删除发帖表信息
     */
    public void deleteByActivityId(int activityId) throws SQLException{
        conn = ConnectionFactory.getConnection();
        String sql = "DELETE FROM activity WHERE id=?";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, activityId);
            ps.executeUpdate();
        } finally {
            ps.close();
            conn.close();
        }
    }

    public Map<String, Object> queryByActivityId(int activityId) throws SQLException{

        Map<String, Object> map = new HashMap<>();
        conn = ConnectionFactory.getConnection();
        String sql = "SELECT activity.id, title, content, user_id, status, activity.datetime, name FROM activity " +
                "INNER JOIN user ON activity.user_id = user.id " +
                "WHERE activity.id=? " +
                "ORDER BY activity.id DESC";
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, activityId);
            rs = ps.executeQuery();
            while (rs.next()) {
                map.put("id", rs.getInt(1));
                map.put("title", rs.getString(2));
                map.put("content", rs.getString(3));
                map.put("userId", rs.getInt(4));
                map.put("status", rs.getString(5));
                map.put("datetime", rs.getString(6));
                map.put("userName", rs.getString(7));
            }
        } finally {
            rs.close();
            ps.close();
            conn.close();
        }
        return map;
    }
}
