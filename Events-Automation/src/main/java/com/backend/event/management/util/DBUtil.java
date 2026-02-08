package com.backend.event.management.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

	private final String URL =
		    "jdbc:mysql://host.docker.internal:3306/event_management?useSSL=false&serverTimezone=UTC";


	private final String USERNAME = "root";

	private final String PASSWORD = "root";

	private final String DRIVER = "com.mysql.cj.jdbc.Driver";

	public Connection getConnection() throws ClassNotFoundException, SQLException {

		Class.forName(DRIVER);

		Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

		System.out.println("Connection SuccessFull");

		return connection;

	}

	public void close(ResultSet rs, Connection connection, PreparedStatement pstmt) {
	    try { if (rs != null) rs.close(); } catch (Exception e) {}
	    try { if (pstmt != null) pstmt.close(); } catch (Exception e) {}
	    try { if (connection != null) connection.close(); } catch (Exception e) {}
	}
	public void close(Connection connection, PreparedStatement pstmt) throws SQLException {

		if (connection != null)
			;
		connection.close();

		if (pstmt != null)
			;
		pstmt.close();
	}

	public static void main(String[] args) {

		DBUtil dbUtil = new DBUtil();

		try {
			dbUtil.getConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
