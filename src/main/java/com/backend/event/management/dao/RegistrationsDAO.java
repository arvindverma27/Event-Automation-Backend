package com.backend.event.management.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.backend.event.management.dto.RegistrationsDTO;
import com.backend.event.management.util.DBUtil;

public class RegistrationsDAO {

	private final String REGISTRATION_FORM = "insert into registrations (events_id, users_id, registration_date, status) values ( (select id from events where event_name = ?), (select id FROM users where full_name = ?), ?, ? )";

	private final String UPDATE_REGISTRATION_STATUS = "update registration set status=? where id=?";

	private final String DELETE_REGISTRATION = "delete from registrations where id=?";

	private final String FIND_REGISTRATION_BY_ID = "select  registrations.id, registrations.events_id, events.event_name AS event_name,registrations.users_id,  users.full_name AS full_name, registrations.registration_date, registrations.status FROM registrations"
			+ "JOIN events ON registrations.events_id = events.id " + "JOIN users ON registrations.users_id = users.id"
			+ "WHERE registrations.id = ?";

	private final String FIND_REGISTRATION_BY_EVENTS_ID = "select  registrations.id, registrations.events_id, events.event_name AS event_name,registrations.users_id,  users.full_name AS full_name, registrations.registration_date,  registrations.status FROM registrations"
			+ "JOIN events ON registrations.events_id = events.id " + "JOIN users ON registrations.users_id = users.id"
			+ "WHERE registrations.events_id = ?";

	private final String FIND_BY_STATUS = "SELECT registrations.id, events.event_name, users.full_name AS full_name, registrations.registration_date, registrations.status FROM registrations "
			+ "JOIN events ON registrations.events_id = events.id " + "JOIN users ON registrations.users_id = users.id "
			+ "WHERE registrations.status = ?";

	private final String FIND_REGISTRATION_BY_USERS_ID = "SELECT registrations.id, events.event_name, users.name AS user_full_name,  registrations.registration_date, registrations.status FROM registrations JOIN events ON registrations.events_id = events.id"
			+ "JOIN users ON registrations.users_id = users.id WHERE registrations.users_id = ?";

	private final String SEARCH_FEEDBACK_BY_DATE = "SELECT *FROM registrations WHERE  -- 1) Exact date  ( ? IS NOT NULL AND registration_date = ? ) OR  -- 2) Date range (between)"
			+ "    ( ? IS NOT NULL AND ? IS NOT NULL AND registration_date BETWEEN ? AND ? )" + "" + "OR" + ""
			+ "    -- 3) Last month" + "    ( ? = 1 AND"
			+ "        MONTH(registration_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)"
			+ "        AND YEAR(registration_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)" + ")";
	
	private final String SEARCH_EVENTS = "SELECT registrations.id, events.event_name, users.name AS user_full_name,  registrations.registration_date, registrations.status FROM registrations JOIN events ON registrations.events_id = events.id "
			+ "JOIN users ON registrations.users_id = users.id " + "WHERE users.full_name LIKE ?"
			+ "OR events.event_name LIKE ? " + "OR registrations.status LIKE ?";

	private final String FIND_ALL_REGISTRATION = "SELECT registrations.id, events.event_name, users.full_name, registrations.registration_date, registrations.status"
			+ "FROM registrations" + "JOIN events ON registrations.events_id = events.id"
			+ "JOIN users ON registrations.users_id = users.id";

	private DBUtil dbUtil;

	public RegistrationsDAO(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	public int save(RegistrationsDTO registrationsDTO) throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(REGISTRATION_FORM);

			pstmt.setString(1, registrationsDTO.getEventName());
			pstmt.setString(2, registrationsDTO.getFullName());
			pstmt.setNull(4, java.sql.Types.TIMESTAMP);
			pstmt.setString(4, registrationsDTO.getStatus());

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}

		return count;
	}

	public boolean update(RegistrationsDTO registrationsDTO) throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(UPDATE_REGISTRATION_STATUS);

			pstmt.setString(1, registrationsDTO.getStatus());
			pstmt.setInt(2, registrationsDTO.getId());

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count > 0;

	}

	public int deleteById(int id) throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;

		int count = 0;

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(DELETE_REGISTRATION);

			pstmt.setInt(1, id);

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}

		return count;
	}

	public RegistrationsDTO findById(int id) throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		RegistrationsDTO registrationsDTO = null;

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_REGISTRATION_BY_ID);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				registrationsDTO = new RegistrationsDTO();
				registrationsDTO.setId(rs.getInt("id"));
				registrationsDTO.setEventName(rs.getString("event_name"));
				registrationsDTO.setFullName(rs.getString("full_name"));
				registrationsDTO.setRegistrationDate(rs.getTimestamp("registration_date"));
				registrationsDTO.setStatus(rs.getString("status"));
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}

		return registrationsDTO;
	}

	public List<RegistrationsDTO> findByEventId(int id) throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<RegistrationsDTO> registrationsDTOlist = new ArrayList<RegistrationsDTO>();

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_REGISTRATION_BY_EVENTS_ID);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				RegistrationsDTO registrationsDTO = new RegistrationsDTO();
				registrationsDTO = new RegistrationsDTO();
				registrationsDTO.setId(rs.getInt("id"));
				registrationsDTO.setEventName(rs.getString("event_name"));
				registrationsDTO.setFullName(rs.getString("full_name"));
				registrationsDTO.setRegistrationDate(rs.getTimestamp("registration_date"));
				registrationsDTO.setStatus(rs.getString("status"));
				registrationsDTOlist.add(registrationsDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}

		return registrationsDTOlist;
	}

	public List<RegistrationsDTO> findByStatus(String status) throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<RegistrationsDTO> registrationsDTOlist = new ArrayList<RegistrationsDTO>();

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_BY_STATUS);
			pstmt.setString(1, status);
			;

			rs = pstmt.executeQuery();

			while (rs.next()) {

				RegistrationsDTO registrationsDTO = new RegistrationsDTO();
				registrationsDTO = new RegistrationsDTO();
				registrationsDTO.setId(rs.getInt("id"));
				registrationsDTO.setEventName(rs.getString("event_name"));
				registrationsDTO.setFullName(rs.getString("full_name"));
				registrationsDTO.setRegistrationDate(rs.getTimestamp("registration_date"));
				registrationsDTO.setStatus(rs.getString("status"));
				registrationsDTOlist.add(registrationsDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}

		return registrationsDTOlist;
	}

	public List<RegistrationsDTO> findByUserId(int id) throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<RegistrationsDTO> registrationsDTOlist = new ArrayList<RegistrationsDTO>();

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_REGISTRATION_BY_USERS_ID);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				RegistrationsDTO registrationsDTO = new RegistrationsDTO();
				registrationsDTO = new RegistrationsDTO();
				registrationsDTO.setId(rs.getInt("id"));
				registrationsDTO.setEventName(rs.getString("event_name"));
				registrationsDTO.setFullName(rs.getString("full_name"));
				registrationsDTO.setRegistrationDate(rs.getTimestamp("registration_date"));
				registrationsDTO.setStatus(rs.getString("status"));
				registrationsDTOlist.add(registrationsDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}

		return registrationsDTOlist;
	}
	
	public List<RegistrationsDTO> searchRegistrationByDate(Date exactDate, Date startDate, Date endDate, boolean lastMonth)
			throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<RegistrationsDTO> registrationsDTOlist = new ArrayList<RegistrationsDTO>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(SEARCH_FEEDBACK_BY_DATE);

			pstmt.setDate(1, exactDate);
			pstmt.setDate(2, exactDate);
			pstmt.setDate(3, startDate);
			pstmt.setDate(4, endDate);
			pstmt.setDate(5, startDate);
			pstmt.setDate(6, endDate);
			pstmt.setInt(7, lastMonth ? 1 : 0);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				RegistrationsDTO registrationsDTO = new RegistrationsDTO();
				registrationsDTO.setUsersId(rs.getInt("users_id"));
				registrationsDTO.setEventName(rs.getString("event_name"));
				registrationsDTO.setFullName(rs.getString("user_name"));
				registrationsDTO.setRegistrationDate(rs.getTimestamp("registration_date"));
				registrationsDTO.setStatus(rs.getString("status"));
				
			
				registrationsDTOlist.add(registrationsDTO);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
			;
		}
		return registrationsDTOlist;

	}


	public List<RegistrationsDTO> searchRegistartionForkeyword(String keyword) throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<RegistrationsDTO> registrationsDTOlist = new ArrayList<RegistrationsDTO>();

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(SEARCH_EVENTS);

			String pattern = "%" + keyword + "%";
			pstmt.setString(1, pattern);
			pstmt.setString(2, pattern);
			pstmt.setString(3, pattern);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				RegistrationsDTO registrationsDTO = new RegistrationsDTO();
				registrationsDTO = new RegistrationsDTO();
				registrationsDTO.setId(rs.getInt("id"));
				registrationsDTO.setEventName(rs.getString("event_name"));
				registrationsDTO.setFullName(rs.getString("full_name"));
				registrationsDTO.setRegistrationDate(rs.getTimestamp("registration_date"));
				registrationsDTO.setStatus(rs.getString("status"));
				registrationsDTOlist.add(registrationsDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}

		return registrationsDTOlist;
	}

	public List<RegistrationsDTO> findAllRegistartions() throws Exception {

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		List<RegistrationsDTO> registrationsDTOlist = new ArrayList<RegistrationsDTO>();

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_ALL_REGISTRATION);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				RegistrationsDTO registrationsDTO = new RegistrationsDTO();
				registrationsDTO = new RegistrationsDTO();
				registrationsDTO.setId(rs.getInt("id"));
				registrationsDTO.setEventName(rs.getString("event_name"));
				registrationsDTO.setFullName(rs.getString("full_name"));
				registrationsDTO.setRegistrationDate(rs.getTimestamp("registration_date"));
				registrationsDTO.setStatus(rs.getString("status"));
				registrationsDTOlist.add(registrationsDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}

		return registrationsDTOlist;
	}

}
