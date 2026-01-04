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

	private final String INSERT_EVENT = "insert into events (users_id,  event_name, event_date, location, description, created_by) values (31,?,?,?,?,31)";

	private final String UPDATE_EVENT = "update events set users_id=31, event_name=?, event_date=?, location=?, description=? where id=? ";

	private final String FIND_BY_ID = "select * from events where id=?";

	private final String DELETE_EVENT = "delete from events where id=?";

	private final String SEARCH_EVENT_BY_KEYWORD = "select * from events where  event_name LIKE CONCAT('%', ?, '%')"
			+ "    OR description LIKE CONCAT('%', ?, '%')" + "OR location LIKE CONCAT('%', ?, '%')";
//
//	private final String SEARCH_EVENT_BY_DATE = "SELECT *FROM events WHERE  -- 1) Exact date  ( ? IS NOT NULL AND event_date = ? ) OR  -- 2) Date range (between)"
//			+ "    ( ? IS NOT NULL AND ? IS NOT NULL AND event_date BETWEEN ? AND ? )" + "" + "OR" + ""
//			+ "    -- 3) Last month" + "    ( ? = 1 AND"
//			+ "        MONTH(event_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)"
//			+ "        AND YEAR(event_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)" + ")";

	private final String FIND_ALL_EVENTS = "select id, event_name, event_date, location, description, users_id, created_by  from events    where users_id = 31";

	private DBUtil dbUtil;

	private EventsDTO eventsDTO;

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

			pstmt.setString(1, eventsDTO.getEventName());
			pstmt.setString(2, eventsDTO.getEventDate());
			pstmt.setString(3, eventsDTO.getLocation());
			pstmt.setString(4, eventsDTO.getDescription());

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

			pstmt.setString(1, eventsDTO.getEventName());
			pstmt.setString(2, eventsDTO.getEventDate());
			pstmt.setString(3, eventsDTO.getLocation());
			pstmt.setString(4, eventsDTO.getDescription());
			pstmt.setInt(5, eventsDTO.getId());

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

		eventsDTO = null;

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
				eventsDTO.setId(rs.getInt("id"));

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(rs, connection, pstmt);
		}
		return eventsDTO;

	}

	public int deletetById(int id) throws Exception {

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
			    
			    eventsDTO.setId(rs.getInt("id"));
			    eventsDTO.setEventName(rs.getString("event_name"));
			    eventsDTO.setEventDate(rs.getString("event_date"));
			    eventsDTO.setLocation(rs.getString("location"));
			    eventsDTO.setDescription(rs.getString("description"));
			    
			    eventsDTO.setUsersId(rs.getInt("users_id"));
			    eventsDTO.setCreatedBy(rs.getInt("created_by"));

			    eventsDTOlist.add(eventsDTO);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(rs, connection, pstmt);
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
			    
			    eventsDTO.setId(rs.getInt("id"));
			    eventsDTO.setEventName(rs.getString("event_name"));
			    eventsDTO.setEventDate(rs.getString("event_date"));
			    eventsDTO.setLocation(rs.getString("location"));
			    eventsDTO.setDescription(rs.getString("description"));
			    
			    eventsDTO.setUsersId(rs.getInt("users_id"));
			    eventsDTO.setCreatedBy(rs.getInt("created_by"));

			    eventsDTOlist.add(eventsDTO);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(rs, connection, pstmt);
		}
		return eventsDTOlist;

	}

}
