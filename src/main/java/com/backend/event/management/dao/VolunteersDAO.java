package com.backend.event.management.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.backend.event.management.dto.VolunteersDTO;
import com.backend.event.management.util.DBUtil;

public class VolunteersDAO {

	private final String INSERT_VOLUNTEERS = "insert into volunteers (users_id, events_id, role_in_event) values ((select id FROM users where full_name = ?), (select id from events where event_name = ?), ? )";

	private final String UPDATE_VOLUNTEER = "update volunteers set users_id = (select id from users where full_name = ?),"
			+ "    events_id = (select id from events where event_name = ?)," + "role_in_event = ? where id = ?";

	private final String DELETE_VOLUNTEER = "delete from volunteers where id=?";

	private final String FIND_VOLUNTEER_BY_ID = "select  volunteers.id, volunteers.users_id,  users.full_name AS full_name, volunteers.events_id, events.event_name AS event_name, volunteers.role_in_event FROM volunteers"
			+ "JOIN users ON volunteers.users_id = users.id" + "JOIN events ON volunteers.events_id = events.id"
			+ "WHERE volunteers.id = ?";

	private final String FIND_ALL_VOLUNTEERS = "SELECT    volunteers.id,  volunteers.users_id, users.full_name AS full_name, volunteers.events_id, events.event_name AS event_name, volunteers.role_in_event FROM volunteers"
			+ "JOIN users ON volunteers.users_id = users.id" + "JOIN events ON volunteers.events_id = events.id";

	private final String SEARCH_VOLUNTEERS_BY_KEYWORD = "SELECT volunteers.id, users.full_name AS full_name, events.event_name, volunteers.role_in_event FROM volunteers"
			+ " JOIN users ON volunteers.users_id = users.id" + " JOIN events ON volunteers.events_id = events.id"
			+ " WHERE users.full_name LIKE CONCAT('%', ?, '%')" + " OR events.event_name LIKE CONCAT('%', ?, '%')"
			+ " OR volunteers.role_in_event LIKE CONCAT('%', ?, '%')";

	private DBUtil dbUtil;

	public VolunteersDAO(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	public int save(VolunteersDTO volunteersDTO) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		int count;

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(INSERT_VOLUNTEERS);

			pstmt.setString(1, volunteersDTO.getFullName());
			pstmt.setString(2, volunteersDTO.getEventName());
			pstmt.setString(3, volunteersDTO.getRoleInEvent());

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count;
	}

	public int update(VolunteersDTO volunteersDTO) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		int count;

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(UPDATE_VOLUNTEER);

			pstmt.setString(1, volunteersDTO.getFullName());
			pstmt.setString(2, volunteersDTO.getEventName());
			pstmt.setString(3, volunteersDTO.getRoleInEvent());
			pstmt.setInt(4, volunteersDTO.getId());

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count;
	}

	public int deleteById(int id) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		int count;

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(DELETE_VOLUNTEER);

			pstmt.setInt(1, id);

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}

		return count;

	}

	public VolunteersDTO findById(int id) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		VolunteersDTO volunteersDTO = null;

		try {

			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(FIND_VOLUNTEER_BY_ID);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				volunteersDTO = new VolunteersDTO();
				volunteersDTO.setId(rs.getInt("id"));
				volunteersDTO.setFullName(rs.getString("full_name"));
				volunteersDTO.setEventName(rs.getString("event_name"));
				volunteersDTO.setRoleInEvent(rs.getString("role_in_event"));

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return volunteersDTO;

	}

	public List<VolunteersDTO> findAllVolunteers() throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<VolunteersDTO> volunteersDTOlist = new ArrayList<VolunteersDTO>();

		try {

			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(FIND_ALL_VOLUNTEERS);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				VolunteersDTO volunteersDTO = new VolunteersDTO();
				volunteersDTO.setId(rs.getInt("id"));
				volunteersDTO.setFullName(rs.getString("full_name"));
				volunteersDTO.setEventName(rs.getString("event_name"));
				volunteersDTO.setRoleInEvent(rs.getString("role_in_event"));
				volunteersDTOlist.add(volunteersDTO);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return volunteersDTOlist;

	}

	public List<VolunteersDTO> searchVolunteersBykeyword(String keyword) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<VolunteersDTO> volunteersDTOlist = new ArrayList<VolunteersDTO>();

		try {

			connection = dbUtil.getConnection();
			pstmt = connection.prepareStatement(SEARCH_VOLUNTEERS_BY_KEYWORD);

			String pattern = "%" + keyword + "";

			pstmt.setString(1, pattern);
			pstmt.setString(2, pattern);
			pstmt.setString(3, pattern);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				VolunteersDTO volunteersDTO = new VolunteersDTO();
				volunteersDTO.setId(rs.getInt("id"));
				volunteersDTO.setFullName(rs.getString("full_name"));
				volunteersDTO.setEventName(rs.getString("event_name"));
				volunteersDTO.setRoleInEvent(rs.getString("role_in_event"));
				volunteersDTOlist.add(volunteersDTO);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return volunteersDTOlist;

	}

}
