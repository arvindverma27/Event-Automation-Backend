package com.backend.event.management.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.backend.event.management.dto.EventsDTO;
import com.backend.event.management.util.DBUtil;

public class EventsDAO {

	private final String INSERT_EVENT = "insert into events (users_id,  event_name, event_date, location, description, created_by) values (?,?,?,?,?,?)";

	private final String UPDATE_EVENT = "update events set users_id=?, event_name=?, event_date=?, location=?, description=? where id=? ";

	private final String FIND_BY_ID = "select * from events where id=?";

	private final String DELETE_EVENT = "delete from events where id=?";

	private final String SEARCH_EVENT_BY_KEYWORD = "select * from events where  event_name LIKE CONCAT('%', ?, '%')"
			+ "    OR description LIKE CONCAT('%', ?, '%')" + "OR location LIKE CONCAT('%', ?, '%')";

	private final String SEARCH_EVENT_BY_DATE = "SELECT *FROM events WHERE  -- 1) Exact date  ( ? IS NOT NULL AND event_date = ? ) OR  -- 2) Date range (between)"
			+ "    ( ? IS NOT NULL AND ? IS NOT NULL AND event_date BETWEEN ? AND ? )" + "" + "OR" + ""
			+ "    -- 3) Last month" + "    ( ? = 1 AND"
			+ "        MONTH(event_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)"
			+ "        AND YEAR(event_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)" + ")";

	private final String FIND_ALL_EVENTS = "select * from events";

	private DBUtil dbUtil;

	public EventsDAO(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	public int save(EventsDTO eventsDTO) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		int count = 0;

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(INSERT_EVENT);

			pstmt.setInt(1, eventsDTO.getUsersId());
			pstmt.setString(2, eventsDTO.getEventName());
			pstmt.setString(3, eventsDTO.getEventDate());
			pstmt.setString(4, eventsDTO.getLocation());
			pstmt.setString(5, eventsDTO.getDescription());
			pstmt.setInt(6, eventsDTO.getCreatedBy());

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count;

	}

	public int update(EventsDTO eventsDTO) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		int count = 0;

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(UPDATE_EVENT);

			pstmt.setInt(1, eventsDTO.getUsersId());
			pstmt.setString(2, eventsDTO.getEventName());
			pstmt.setString(3, eventsDTO.getEventDate());
			pstmt.setString(4, eventsDTO.getLocation());
			pstmt.setString(5, eventsDTO.getDescription());
			pstmt.setInt(6, eventsDTO.getId());

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count;

	}

	public EventsDTO findById(int id) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		EventsDTO eventsDTO = null;

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_BY_ID);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				eventsDTO.setUsersId(rs.getInt("users_id"));
				eventsDTO.setEventName(rs.getString("event_name"));
				eventsDTO.setEventDate(rs.getString("event_date"));
				eventsDTO.setLocation(rs.getString("location"));
				eventsDTO.setDescription(rs.getString("description"));

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
			;
		}
		return eventsDTO;

	}

	public int deleteById(int id) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		int count;

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(DELETE_EVENT);

			pstmt.setInt(1, id);

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
			;
		}
		return count;

	}

	public List<EventsDTO> searchEventsBykeyword(String keyword) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<EventsDTO> eventsDTOlist = new ArrayList<EventsDTO>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(SEARCH_EVENT_BY_KEYWORD);

			String pattern = "%" + keyword + "%";

			pstmt.setString(1, pattern);
			pstmt.setString(2, pattern);
			pstmt.setString(3, pattern);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				EventsDTO eventsDTO = new EventsDTO();
				eventsDTO.setUsersId(rs.getInt("users_id"));
				eventsDTO.setEventName(rs.getString("event_name"));
				eventsDTO.setEventDate(rs.getString("event_date"));
				eventsDTO.setLocation(rs.getString("location"));
				eventsDTO.setDescription(rs.getString("description"));
				eventsDTOlist.add(eventsDTO);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
			;
		}
		return eventsDTOlist;

	}

	public List<EventsDTO> searchEventsByDate(Date exactDate, Date startDate, Date endDate, boolean lastMonth)
			throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<EventsDTO> eventsDTOlist = new ArrayList<EventsDTO>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(SEARCH_EVENT_BY_DATE);

			pstmt.setDate(1, exactDate);
			pstmt.setDate(2, exactDate);
			pstmt.setDate(3, startDate);
			pstmt.setDate(4, endDate);
			pstmt.setDate(5, startDate);
			pstmt.setDate(6, endDate);
			pstmt.setInt(7, lastMonth ? 1 : 0);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				EventsDTO eventsDTO = new EventsDTO();
				eventsDTO.setUsersId(rs.getInt("users_id"));
				eventsDTO.setEventName(rs.getString("event_name"));
				eventsDTO.setEventDate(rs.getString("event_date"));
				eventsDTO.setLocation(rs.getString("location"));
				eventsDTO.setDescription(rs.getString("description"));
				eventsDTOlist.add(eventsDTO);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
			;
		}
		return eventsDTOlist;

	}

	public List<EventsDTO> findAllEvents() throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<EventsDTO> eventsDTOlist = new ArrayList<EventsDTO>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_ALL_EVENTS);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				EventsDTO eventsDTO = new EventsDTO();
				eventsDTO.setUsersId(rs.getInt("users_id"));
				eventsDTO.setEventName(rs.getString("event_name"));
				eventsDTO.setEventDate(rs.getString("event_date"));
				eventsDTO.setLocation(rs.getString("location"));
				eventsDTO.setDescription(rs.getString("description"));
				eventsDTOlist.add(eventsDTO);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
			;
		}
		return eventsDTOlist;

	}

}
