package com.backend.event.management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.backend.event.management.dto.NotificationsDTO;
import com.backend.event.management.util.DBUtil;

public class NotificatiosDAO {

	private final String SAVE = "INSERT INTO notifications (users_id, title, message, type, is_read, target_url) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	private final String NOTIFICATION_UPDATE_BY_USER_ID = "UPDATE notifications SET is_read = 1 WHERE users_id = ?";
	private final String NOTIFICATION_FOR_USER = "SELECT * FROM notifications WHERE users_id = ? OR users_id IS NULL ORDER BY created_at DESC";

	private final String NOTIFICATION_READ = "UPDATE notifications SET is_read = 1 WHERE id = ?";

	private final String FIND_ALL_NOTIFICATIONS = "SELECT * FROM notifications ORDER BY created_at DESC";

	private DBUtil dbUtil;

	public NotificatiosDAO(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	public int save(NotificationsDTO notificationsDTO) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		int count = 0;

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(SAVE);

			if (notificationsDTO.getUsersId() != null) {
				pstmt.setInt(1, notificationsDTO.getUsersId());
			} else {
				pstmt.setNull(1, Types.INTEGER);
			}
			pstmt.setString(2, notificationsDTO.getTitle());
			pstmt.setString(3, notificationsDTO.getMessage());
			pstmt.setString(4, notificationsDTO.getType());
			pstmt.setBoolean(5, notificationsDTO.isRead());
			pstmt.setString(6, notificationsDTO.getTargetUrl());

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count;

	}

	public int markAsRead(int id) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		int count = 0;

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(NOTIFICATION_READ);

			pstmt.setInt(1, id);
			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count;

	}

	public int markAsAllRead(int userId) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		int count = 0;

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(NOTIFICATION_UPDATE_BY_USER_ID);

			pstmt.setInt(1, userId);
			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count;

	}

	public List<NotificationsDTO> NotificationsForUser(int userId) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<NotificationsDTO> notificationsDTOList = new ArrayList<>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(NOTIFICATION_FOR_USER);

			pstmt.setInt(1, userId);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				notificationsDTOList.add(mapRow(rs));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(rs, connection, pstmt);
		}
		return notificationsDTOList;
	}

	public List<NotificationsDTO> findAllNotifications() throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<NotificationsDTO> notificationsDTOList = new ArrayList<>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_ALL_NOTIFICATIONS);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				notificationsDTOList.add(mapRow(rs));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(rs, connection, pstmt);
		}
		return notificationsDTOList;
	}

	private NotificationsDTO mapRow(ResultSet rs) throws Exception {
		NotificationsDTO notificationsDTO = new NotificationsDTO();

		notificationsDTO.setId(rs.getInt("id"));
		notificationsDTO.setUsersId((Integer) rs.getObject("users_id"));
		notificationsDTO.setTitle(rs.getString("title"));
		notificationsDTO.setMessage(rs.getString("message"));
		notificationsDTO.setType(rs.getString("type"));
		notificationsDTO.setRead(rs.getBoolean("is_read"));
		notificationsDTO.setTargetUrl(rs.getString("target_url"));
		notificationsDTO.setCreatedAt(rs.getTimestamp("created_at"));

		return notificationsDTO;
	}

}
