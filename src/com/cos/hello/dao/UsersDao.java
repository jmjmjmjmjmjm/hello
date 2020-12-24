package com.cos.hello.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cos.hello.config.DBConn;
import com.cos.hello.model.Users;

public class UsersDao {
	
	public int delete(int id) {
		String sql = "DELETE FROM users WHERE id = ?";
		
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public int update(Users user) {
		StringBuffer sb = new StringBuffer(); 
		sb.append("UPDATE users SET password = ?, email = ? ");
		sb.append("WHERE id = ?");
		String sql = sb.toString();
		
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			pstmt.setInt(3, user.getId());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public Users selectById(int id) {
		String sql = "SELECT id, password, username, email FROM users WHERE id = ?";
		
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.password(rs.getString("password"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Users login(Users user) {
		String sql = "SELECT id, username, email FROM users WHERE username = ? AND password = ?";
		
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int insert(Users user) {
		StringBuffer sb = new StringBuffer(); // String전용 컬렉션 (동기화)
		sb.append("INSERT INTO users(username, password, email) ");
		sb.append("VALUES(?,?,?)");
		String sql = sb.toString();
		
		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			int result = pstmt.executeUpdate();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
}
