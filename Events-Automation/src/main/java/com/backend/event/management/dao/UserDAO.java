package com.backend.event.management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.backend.event.management.dto.UserDTO;
import com.backend.event.management.util.DBUtil;

public class UserDAO {

	private final String INSERT_USER = "INSERT INTO users (full_name, role, email, mobile_no, password) "
			+ "VALUES (?, ?, ?, ?, ?)";

	private final String UPDATE_USER = "UPDATE users SET full_name=?, role=?, email=?, mobile_no=?, password=? WHERE id=?";

	private final String FIND_USER_BY_ID = "SELECT * FROM users WHERE id=?";

	private final String LOGIN_USER = "SELECT * FROM users WHERE (mobile_no = ? OR email = ?) AND password = ?";

	private final String DELETE_USER = "DELETE FROM users WHERE id=?";

	private final String FIND_ALL_STUDENT = "SELECT * FROM users WHERE role='student'";

	private final String FIND_ALL_VOLUNTEER = "SELECT * FROM users WHERE role='volunteer'";

	private final String SEARCH_USERS = "SELECT * FROM users WHERE ( full_name LIKE ? OR email LIKE ? OR mobile_no LIKE ? )";

	
	private DBUtil dbUtil;

	public UserDAO(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	public int save(UserDTO userDTO) throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;
		int Count;
		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(INSERT_USER);

			pstmt.setString(1, userDTO.getFullName());
			pstmt.setString(2, userDTO.getRole());
			pstmt.setString(3, userDTO.getEmail());
			pstmt.setString(4, userDTO.getMobileNumber());
			pstmt.setString(5, userDTO.getPassword());

			Count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}

		return Count;
	}

	public int update(UserDTO userDTO) throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;
		int Count;
		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(UPDATE_USER);

			pstmt.setString(1, userDTO.getFullName());
			pstmt.setString(2, userDTO.getRole());
			pstmt.setString(3, userDTO.getMobileNumber());
			pstmt.setString(4, userDTO.getEmail());
			pstmt.setInt(5, userDTO.getId());

			Count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}

		return Count;
	}

	public UserDTO findById(int id) throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserDTO userDTO = null;

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(FIND_USER_BY_ID);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				userDTO = new UserDTO();
				userDTO.setId(rs.getInt("id"));
				userDTO.setFullName(rs.getString("full_name"));
				userDTO.setRole(rs.getString("role"));
				userDTO.setEmail(rs.getString("email"));
				userDTO.setMobileNumber(rs.getString("mobile_no"));

			}

		} finally {
			dbUtil.close(rs, connection, pstmt);
		}

		return userDTO;
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
			
			}

		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (connection != null)
				connection.close();
		}
		return userDTO;

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

	public List<UserDTO> findAllStudent() throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<UserDTO> userDTOList = new ArrayList<>();

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_STUDENT);

			
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
			dbUtil.close(rs, connection, pstmt);
		}
		return userDTOList;
	}
	
	public List<UserDTO> findAllVolunteer() throws Exception {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<UserDTO> userDTOList = new ArrayList<>();

		try {
			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement( FIND_ALL_VOLUNTEER);

		
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
			dbUtil.close(rs, connection, pstmt);
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
			dbUtil.close(rs, connection, pstmt);
		}
		return userDTOlist;
	}
}
//
//	public UserDTO findByVerificationToken(String token) throws Exception {
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			connection = dbUtil.getConnection();
//			pstmt = connection.prepareStatement(FIND_BY_VERIFICATION_TOKEN);
//			pstmt.setString(1, token);
//
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				UserDTO user = new UserDTO();
//				user.setId(rs.getInt("id"));
//				user.setEmail(rs.getString("email"));
//				return user;
//			}
//
//		} finally {
//			dbUtil.close(connection, pstmt, rs);
//		}
//		return null;
//	}
//
//	public void clearVerificationToken(int userId) throws Exception {
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			connection = dbUtil.getConnection();
//			pstmt = connection.prepareStatement(CLEAR_VERIFICATION_TOKEN);
//			pstmt.setInt(1, userId);
//			pstmt.executeUpdate();
//
//		} finally {
//			dbUtil.close(connection, pstmt);
//		}
//	}
//
//	public void saveResetToken(String email, String token, Timestamp expiry) throws Exception {
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			connection = dbUtil.getConnection();
//			pstmt = connection.prepareStatement(UPDATE_RESET_TOKEN);
//
//			pstmt.setString(1, token);
//			pstmt.setTimestamp(2, expiry);
//			pstmt.setString(3, email);
//
//			pstmt.executeUpdate();
//
//		} finally {
//			dbUtil.close(connection, pstmt);
//		}
//	}
//
//	public UserDTO findByResetToken(String token) throws Exception {
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		try {
//			connection = dbUtil.getConnection();
//			pstmt = connection.prepareStatement(FIND_BY_RESET_TOKEN);
//			pstmt.setString(1, token);
//
//			rs = pstmt.executeQuery();
//
//			if (rs.next()) {
//				UserDTO user = new UserDTO();
//				user.setId(rs.getInt("id"));
//				return user;
//			}
//
//		} finally {
//			dbUtil.close(connection, pstmt, rs);
//		}
//		return null;
//	}
//
//	public void resetPassword(int id, String newPassword) throws Exception {
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//
//		try {
//			connection = dbUtil.getConnection();
//			pstmt = connection.prepareStatement(RESET_PASSWORD);
//
//			pstmt.setString(1, newPassword);
//			pstmt.setInt(2, id);
//
//			pstmt.executeUpdate();
//
//		} finally {
//			dbUtil.close(connection, pstmt);
//		}
//}
