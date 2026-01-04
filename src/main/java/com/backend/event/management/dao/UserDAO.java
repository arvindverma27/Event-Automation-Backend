package com.backend.event.management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.backend.event.management.dto.UserDTO;
import com.backend.event.management.util.DBUtil;

public class UserDAO {

	private final String INSERT_USER = "INSERT INTO users (full_name, role, email, mobile_no, password, verification_token, token_expiry) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

	private final String UPDATE_USER = "UPDATE users SET full_name=?, role=?, email=?, mobile_no=?, password=? WHERE id=?";

	private final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

	private final String LOGIN_USER = "SELECT * FROM users WHERE (mobile_no=? OR email=?) AND password=?";

	private final String UPDATE_LAST_LOGIN = "UPDATE users SET last_login = NOW() WHERE id=?";

	private final String DELETE_USER = "DELETE FROM users WHERE id=?";

	private final String FIND_ALL_STUDENT = "SELECT * FROM users WHERE role='student'";

	private final String FIND_ALL_VOLUNTEER = "SELECT * FROM users WHERE role='volunteer'";

	private final String SEARCH_USERS = "SELECT * FROM users WHERE role IN ('student', 'volunteer') "
			+ "AND ( full_name LIKE ? OR email LIKE ? OR mobile_no LIKE ? )";

	
	private final String FIND_BY_VERIFICATION_TOKEN = "SELECT * FROM users WHERE verification_token=?";

	private final String CLEAR_VERIFICATION_TOKEN = "UPDATE users SET verification_token=NULL, token_expiry=NULL WHERE id=?";

	
	private final String UPDATE_RESET_TOKEN = "UPDATE users SET reset_token=?, reset_expiry=? WHERE email=?";

	private final String FIND_BY_RESET_TOKEN = "SELECT * FROM users WHERE reset_token=?";

	private final String RESET_PASSWORD = "UPDATE users SET password=?, reset_token=NULL, reset_expiry=NULL WHERE id=?";

	private DBUtil dbUtil;

	public UserDAO(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	
	public int save(UserDTO userDTO) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(INSERT_USER);

			pstmt.setString(1, userDTO.getFullName());
			pstmt.setString(2, userDTO.getRole());
			pstmt.setString(3, userDTO.getEmail());
			pstmt.setString(4, userDTO.getMobileNumber());
			pstmt.setString(5, userDTO.getPassword());
			pstmt.setString(6, userDTO.getVerificationToken());
			pstmt.setTimestamp(7, userDTO.getTokenExpiry());

			return pstmt.executeUpdate();

		} finally {
			dbUtil.close(connection, pstmt);
		}
	}


	public int update(UserDTO userDTO) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(UPDATE_USER);

			pstmt.setString(1, userDTO.getFullName());
			pstmt.setString(2, userDTO.getRole());
			pstmt.setString(3, userDTO.getEmail());
			pstmt.setString(4, userDTO.getMobileNumber());
			pstmt.setString(5, userDTO.getPassword());
			pstmt.setInt(6, userDTO.getId());

			return pstmt.executeUpdate();

		} finally {
			dbUtil.close(connection, pstmt);
		}
	}

	

	public UserDTO findById(int id) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(FIND_USER_BY_ID);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				UserDTO user = new UserDTO();
				user.setId(rs.getInt("id"));
				user.setFullName(rs.getString("full_name"));
				user.setRole(rs.getString("role"));
				user.setEmail(rs.getString("email"));
				user.setMobileNumber(rs.getString("mobile_no"));
				return user;
			}

		} finally {
			dbUtil.close(connection, pstmt, rs);
		}

		return null;
	}

	

	public UserDTO login(String username, String password) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO userDTO = null;
		

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(LOGIN_USER);

			pstmt.setString(1, username);
			pstmt.setString(2, username);
			pstmt.setString(3, password);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				 userDTO = new UserDTO();
				userDTO.setId(rs.getInt("id"));
				userDTO.setFullName(rs.getString("full_name"));
				userDTO.setRole(rs.getString("role"));
				userDTO.setEmail(rs.getString("email"));
				userDTO.setMobileNumber(rs.getString("mobile_no"));

			
				updateLastLogin(userDTO.getId());


			}

		} finally {
			dbUtil.close(connection, pstmt, rs);
		}

		return userDTO;
	}

	private void updateLastLogin(int userId) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(UPDATE_LAST_LOGIN);
			pstmt.setInt(1, userId);
			pstmt.executeUpdate();

		} finally {
			dbUtil.close(connection, pstmt);
		}
	}


	public int deleteById(int id) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(DELETE_USER);
			pstmt.setInt(1, id);

			return pstmt.executeUpdate();

		} finally {
			dbUtil.close(connection, pstmt);
		}
	}



	public List<UserDTO> findAllByRole(String role) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<UserDTO> userDTOList = new ArrayList<>();

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_STUDENT);

			pstmt.setString(1, role);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setId(rs.getInt("id"));
				userDTO.setFullName(rs.getString("full_name"));
				userDTO.setRole(rs.getString("role"));
				userDTO.setEmail(rs.getString("email"));
				userDTO.setMobileNumber(rs.getString("mobile_no"));
				userDTOList.add(userDTO);
			}

		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return userDTOList;
	}

	

	

	public List<UserDTO> searchUsers(String keyword) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<UserDTO> userDTOlist = new ArrayList<>();

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(SEARCH_USERS);

			String pattern = "%" + keyword + "%";

			pstmt.setString(1, pattern);
			pstmt.setString(2, pattern);
			pstmt.setString(3, pattern);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				UserDTO userDTO = new UserDTO();
				userDTO.setId(rs.getInt("id"));
				userDTO.setFullName(rs.getString("full_name"));
				userDTO.setRole(rs.getString("role"));
				userDTO.setEmail(rs.getString("email"));
				userDTO.setMobileNumber(rs.getString("mobile_no"));
				userDTOlist.add(userDTO);
			}

		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return userDTOlist;
	}



	public UserDTO findByVerificationToken(String token) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(FIND_BY_VERIFICATION_TOKEN);
			pstmt.setString(1, token);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				UserDTO user = new UserDTO();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				return user;
			}

		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return null;
	}

	public void clearVerificationToken(int userId) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(CLEAR_VERIFICATION_TOKEN);
			pstmt.setInt(1, userId);
			pstmt.executeUpdate();

		} finally {
			dbUtil.close(connection, pstmt);
		}
	}

	

	public void saveResetToken(String email, String token, Timestamp expiry) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(UPDATE_RESET_TOKEN);

			pstmt.setString(1, token);
			pstmt.setTimestamp(2, expiry);
			pstmt.setString(3, email);

			pstmt.executeUpdate();

		} finally {
			dbUtil.close(connection, pstmt);
		}
	}

	public UserDTO findByResetToken(String token) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(FIND_BY_RESET_TOKEN);
			pstmt.setString(1, token);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				UserDTO user = new UserDTO();
				user.setId(rs.getInt("id"));
				return user;
			}

		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return null;
	}

	public void resetPassword(int id, String newPassword) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(RESET_PASSWORD);

			pstmt.setString(1, newPassword);
			pstmt.setInt(2, id);

			pstmt.executeUpdate();

		} finally {
			dbUtil.close(connection, pstmt);
		}
	}
}
