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

	private final String REGISTRATION_FORM = "insert into registrations (events_id, users_id, registration_date, status) values (?,?, now(), ? )";

	private final String DELETE_REGISTRATION = "delete from registrations where id=?";
	
	private final String UPDATE_STATUS= "update registrations set status = ?, registration_date = now() where id = ?";
	
	private static final String UPDATE_ATTENDED =
		    "UPDATE registrations SET attended = ? WHERE users_id = ? AND events_id = ?";


	private final String FIND_ALL_REGISTRATION = "SELECT registrations.id, events.event_name, users.full_name, "
			+ "registrations.registration_date, registrations.status " + "FROM registrations "
			+ "JOIN events ON registrations.events_id = events.id " + "JOIN users ON registrations.users_id = users.id";

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

			pstmt.setInt(1, registrationsDTO.getEventsId());
			pstmt.setInt(2, registrationsDTO.getUsersId());
			pstmt.setString(3, registrationsDTO.getStatus());

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}

		return count;
	}

	public int updateStatus(int id, String status) throws Exception {

    Connection connection = null;
    PreparedStatement pstmt = null;
    
    int count = 0;

    try {
    	
        connection = dbUtil.getConnection();
        pstmt = connection.prepareStatement(UPDATE_STATUS);

        pstmt.setString(1, status);
        pstmt.setInt(2, id);

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
	public int markAttendance(int usersId, int eventsId, int attended) throws Exception {
	    Connection connection = null;
	    PreparedStatement pstmt = null;

	    try {
	    	connection = dbUtil.getConnection();
	    	pstmt = connection.prepareStatement(UPDATE_ATTENDED);

	    	pstmt.setInt(1, attended);
	    	pstmt.setInt(2, usersId);
	    	pstmt.setInt(3, eventsId);

	        return pstmt.executeUpdate();
	    } finally {
	        dbUtil.close(connection, pstmt);
	    }
	}



	public List<RegistrationsDTO> findAllRegistrations() throws Exception {

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
				registrationsDTO.setFullName(rs.getString("full_name"));
				registrationsDTO.setEventName(rs.getString("event_name"));
				registrationsDTO.setEventDate(rs.getString("registration_date"));
				registrationsDTO.setStatus(rs.getString("status"));
				registrationsDTOlist.add(registrationsDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(rs, connection, pstmt);
		}

		return registrationsDTOlist;
	}
	
	
//	public List<RegistrationsDTO> findByStatus(String status) throws Exception {
//
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		List<RegistrationsDTO> registrationsDTOlist = new ArrayList<RegistrationsDTO>();
//
//		try {
//			connection = dbUtil.getConnection();
//
//			pstmt = connection.prepareStatement(FIND_BY_STATUS);
//			pstmt.setString(1, status);
//			;
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//
//				RegistrationsDTO registrationsDTO = new RegistrationsDTO();
//				registrationsDTO = new RegistrationsDTO();
//				registrationsDTO.setId(rs.getInt("id"));
//				registrationsDTO.setStatus(rs.getString("status"));
//				registrationsDTOlist.add(registrationsDTO);
//			}
//
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			dbUtil.close(rs, connection, pstmt);
//		}
//
//		return registrationsDTOlist;
//	}

//	public List<RegistrationsDTO> findByUserId(int id) throws Exception {
//
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		List<RegistrationsDTO> registrationsDTOlist = new ArrayList<RegistrationsDTO>();
//
//		try {
//			connection = dbUtil.getConnection();
//
//			pstmt = connection.prepareStatement(FIND_REGISTRATION_BY_USERS_ID);
//			pstmt.setInt(1, id);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//
//				RegistrationsDTO registrationsDTO = new RegistrationsDTO();
//				registrationsDTO = new RegistrationsDTO();
//				registrationsDTO.setId(rs.getInt("id"));
//				registrationsDTO.setEventName(rs.getString("event_name"));
//				registrationsDTO.setFullName(rs.getString("full_name"));
//				registrationsDTO.setRegistrationDate(rs.getTimestamp("registration_date"));
//				registrationsDTO.setStatus(rs.getString("status"));
//				registrationsDTOlist.add(registrationsDTO);
//			}
//
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			dbUtil.close(rs, connection, pstmt);		}
//
//		return registrationsDTOlist;
//	}

//	public List<RegistrationsDTO> searchRegistrationByDate(Date exactDate, Date startDate, Date endDate, boolean lastMonth)
//			throws Exception {
//
//		Connection connection = null;
//
//		PreparedStatement pstmt = null;
//
//		ResultSet rs = null;
//
//		List<RegistrationsDTO> registrationsDTOlist = new ArrayList<RegistrationsDTO>();
//
//		try {
//
//			connection = dbUtil.getConnection();
//
//			pstmt = connection.prepareStatement(SEARCH_FEEDBACK_BY_DATE);
//
//			pstmt.setDate(1, exactDate);
//			pstmt.setDate(2, exactDate);
//			pstmt.setDate(3, startDate);
//			pstmt.setDate(4, endDate);
//			pstmt.setDate(5, startDate);
//			pstmt.setDate(6, endDate);
//			pstmt.setInt(7, lastMonth ? 1 : 0);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//
//				RegistrationsDTO registrationsDTO = new RegistrationsDTO();
//				registrationsDTO.setUsersId(rs.getInt("users_id"));
//				registrationsDTO.setEventName(rs.getString("event_name"));
//				registrationsDTO.setFullName(rs.getString("user_name"));
//				registrationsDTO.setRegistrationDate(rs.getTimestamp("registration_date"));
//				registrationsDTO.setStatus(rs.getString("status"));
//				
//			
//				registrationsDTOlist.add(registrationsDTO);
//
//			}
//
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			dbUtil.close(rs, connection, pstmt);			;
//		}
//		return registrationsDTOlist;
//
//	}

//
//	public List<RegistrationsDTO> searchRegistartionForkeyword(String keyword) throws Exception {
//
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		List<RegistrationsDTO> registrationsDTOlist = new ArrayList<RegistrationsDTO>();
//
//		try {
//			connection = dbUtil.getConnection();
//
//			pstmt = connection.prepareStatement(SEARCH_EVENTS);
//
//			String pattern = "%" + keyword + "%";
//			pstmt.setString(1, pattern);
//			pstmt.setString(2, pattern);
//			pstmt.setString(3, pattern);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//
//				RegistrationsDTO registrationsDTO = new RegistrationsDTO();
//				registrationsDTO = new RegistrationsDTO();
//				registrationsDTO.setId(rs.getInt("id"));
//				registrationsDTO.setEventName(rs.getString("event_name"));
//				registrationsDTO.setFullName(rs.getString("full_name"));
//				registrationsDTO.setRegistrationDate(rs.getTimestamp("registration_date"));
//				registrationsDTO.setStatus(rs.getString("status"));
//				registrationsDTOlist.add(registrationsDTO);
//			}
//
//		} catch (Exception e) {
//			throw e;
//		} finally {
//			dbUtil.close(rs, connection, pstmt);		}
//
//		return registrationsDTOlist;
//	}

}
