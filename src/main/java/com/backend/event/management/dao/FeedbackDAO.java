package com.backend.event.management.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.backend.event.management.dto.FeedbackDTO;
import com.backend.event.management.util.DBUtil;

public class FeedbackDAO {

	private final String FEEDBACK_FORM = "insert into feedback (events_id, users_id, rating, comments, feedback_date) values ( (select id from events where event_name = ?), (select id FROM users where full_name = ?), ?, ?, ? )";

	private final String UPDATE_FEEDBACK_FORM = "update feedback set rating=?, comments=? where id=?";

	private final String FIND_FEEDBACK_BY_ID = "select  feedback.id, feedback.events_id, events.event_name AS event_name,feedback.users_id,  users.full_name AS full_name, feedback.rating, feedback.comments,  feedback.feedback_date FROM feedback"
			+ "JOIN events ON feedback.events_id = events.id " + "JOIN users ON feedback.users_id = users.id"
			+ "WHERE feedback.id = ?";

	private final String FIND_FEEDBACK_BY_EVENTS_ID = "select  feedback.id, feedback.events_id, events.event_name AS event_name,feedback.users_id,  users.full_name AS full_name, feedback.rating, feedback.comments, feedback.feedback_date FROM feedback"
			+ "JOIN events ON feedback.events_id = events.id " + "JOIN users ON feedback.users_id = users.id"
			+ "WHERE feedback.events_id = ?";
	
	private final String DELETE_BY_ID = "delete from feedback where id=?";

	private final String FIND_FEEDBACK_BY_RATING = "SELECT feedback.id, events.event_name, users.full_name AS full_name, feedback.rating, feedback.comments, feedback.feedback_date FROM feedback "
			+ "JOIN events ON feedback.events_id = events.id " + "JOIN users ON feedback.users_id = users.id "
			+ "WHERE feedback.rating = ?";

	private final String FIND_FEEDBACK_BY_USERS_ID = "SELECT feedback.id, events.event_name, users.name AS user_full_name, feedback.rating, feedback.comments, feedback.feedback_date FROM feedback JOIN events ON feedback.events_id = events.id"
			+ "JOIN users ON feedback.users_id = users.id WHERE feedback.users_id = ?";

	private final String SEARCH_FEEDBACK_BY_DATE = "SELECT *FROM feedback WHERE  -- 1) Exact date  ( ? IS NOT NULL AND feedback_date = ? ) OR  -- 2) Date range (between)"
			+ "    ( ? IS NOT NULL AND ? IS NOT NULL AND feedback_date BETWEEN ? AND ? )" + "" + "OR" + ""
			+ "    -- 3) Last month" + "    ( ? = 1 AND"
			+ "        MONTH(feedback_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)"
			+ "        AND YEAR(feedback_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)" + ")";

	private final String SEARCH_FEEDBACK_BY_KEYWORD = "SELECT feedback.id, events.event_name, users.name AS user_full_name, feedback.rating, feedback.comments, feedback.feedback_date,  FROM feedback JOIN events ON feedback.events_id = events.id "
			+ "JOIN users ON feedback.users_id = users.id " + "WHERE users.full_name LIKE ?"
			+ "OR events.event_name LIKE ? " + "OR CAST(feedback.rating AS CHAR) LIKE  ?";

	private final String FIND_ALL_FEEDBACK = "SELECT feedback.id, events.event_name, users.full_name, feedback.rating, feedback.comments, feedback.feedback_date "
			+ "FROM feedback" + "JOIN events ON feedback.events_id = events.id"
			+ "JOIN users ON feedback.users_id = users.id";

	private DBUtil dbUtil;

	public FeedbackDAO(DBUtil dbUtil) {
		this.dbUtil = dbUtil;
	}

	public int save(FeedbackDTO feedbackDTO) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		int count = 0;

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FEEDBACK_FORM);

			pstmt.setString(1, feedbackDTO.getEventName());
			pstmt.setString(2, feedbackDTO.getFullName());
			pstmt.setInt(3, feedbackDTO.getRating());
			pstmt.setString(4, feedbackDTO.getComments());
			pstmt.setTimestamp(5, feedbackDTO.getFeedbackDate());

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count;
	}

	public int update(FeedbackDTO feedbackDTO) throws Exception {
		Connection connection = null;

		PreparedStatement pstmt = null;

		int count = 0;

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(UPDATE_FEEDBACK_FORM);

			pstmt.setInt(1, feedbackDTO.getRating());
			pstmt.setString(2, feedbackDTO.getComments());
			pstmt.setInt(3, feedbackDTO.getId());

			count = pstmt.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count;
	}

	public FeedbackDTO findById(int id) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		FeedbackDTO feedbackDTO = new FeedbackDTO();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_FEEDBACK_BY_ID);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				feedbackDTO = new FeedbackDTO();
				feedbackDTO.setId(rs.getInt("id"));
				feedbackDTO.setEventName(rs.getString("event_name"));
				feedbackDTO.setFullName(rs.getString("full_name"));
				feedbackDTO.setRating(rs.getInt("rating"));
				feedbackDTO.setComments(rs.getString("comments"));
				feedbackDTO.setFeedbackDate(rs.getTimestamp("feedback_date"));

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return feedbackDTO;
	}
	
	public int deleteById(int id) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		int count = 0;
		

		try {
			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(DELETE_BY_ID);

			pstmt.setInt(1, id);
			
			count = pstmt.executeUpdate();


			

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt);
		}
		return count;
	}

	public List<FeedbackDTO> findByEventId(int eventId) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<FeedbackDTO> feedbackDTOlist = new ArrayList<FeedbackDTO>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_FEEDBACK_BY_EVENTS_ID);

			pstmt.setInt(1, eventId);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				FeedbackDTO feedbackDTO = new FeedbackDTO();

				feedbackDTO.setEventId(rs.getInt("events_id"));
				;
				feedbackDTO.setEventName(rs.getString("event_name"));
				feedbackDTO.setFullName(rs.getString("full_name"));
				feedbackDTO.setRating(rs.getInt("rating"));
				feedbackDTO.setComments(rs.getString("comments"));
				feedbackDTO.setFeedbackDate(rs.getTimestamp("feedback_date"));
				feedbackDTOlist.add(feedbackDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return feedbackDTOlist;
	}

	public List<FeedbackDTO> findByRating(int rating) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<FeedbackDTO> feedbackDTOlist = new ArrayList<FeedbackDTO>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_FEEDBACK_BY_RATING);

			pstmt.setInt(1, rating);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				FeedbackDTO feedbackDTO = new FeedbackDTO();

				feedbackDTO.setId(rs.getInt("id"));
				feedbackDTO.setEventName(rs.getString("event_name"));
				feedbackDTO.setFullName(rs.getString("full_name"));
				feedbackDTO.setRating(rs.getInt("rating"));
				feedbackDTO.setComments(rs.getString("comments"));
				feedbackDTO.setFeedbackDate(rs.getTimestamp("feedback_date"));
				feedbackDTOlist.add(feedbackDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return feedbackDTOlist;
	}

	public List<FeedbackDTO> findByUserId(int userId) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<FeedbackDTO> feedbackDTOlist = new ArrayList<FeedbackDTO>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_FEEDBACK_BY_USERS_ID);

			pstmt.setInt(1, userId);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				FeedbackDTO feedbackDTO = new FeedbackDTO();

				feedbackDTO.setUserId(rs.getInt("users_id"));
				feedbackDTO.setEventName(rs.getString("event_name"));
				feedbackDTO.setFullName(rs.getString("full_name"));
				feedbackDTO.setRating(rs.getInt("rating"));
				feedbackDTO.setComments(rs.getString("comments"));
				feedbackDTO.setFeedbackDate(rs.getTimestamp("feedback_date"));
				feedbackDTOlist.add(feedbackDTO);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
		}
		return feedbackDTOlist;
	}

	public List<FeedbackDTO> searchFeedbackByDate(Date exactDate, Date startDate, Date endDate, boolean lastMonth)
			throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<FeedbackDTO> feedbackDTOlist = new ArrayList<FeedbackDTO>();

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

				FeedbackDTO feedbackDTO = new FeedbackDTO();
				feedbackDTO.setEventName(rs.getString("event_name"));
				feedbackDTO.setFullName(rs.getString("full_name"));
				feedbackDTO.setRating(rs.getInt("rating"));
				feedbackDTO.setComments(rs.getString("comments"));
				feedbackDTO.setFeedbackDate(rs.getTimestamp("feedback_date"));
				feedbackDTOlist.add(feedbackDTO);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
			;
		}
		return feedbackDTOlist;

	}

	public List<FeedbackDTO> searchFeedbackBykeyword(String keyword) throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<FeedbackDTO> feedbackDTOlist = new ArrayList<FeedbackDTO>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(SEARCH_FEEDBACK_BY_KEYWORD);

			String pattern = "%" + keyword + "%";
			pstmt.setString(1, pattern);
			pstmt.setString(2, pattern);
			pstmt.setString(3, pattern);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				FeedbackDTO feedbackDTO = new FeedbackDTO();
				feedbackDTO.setEventName(rs.getString("event_name"));
				feedbackDTO.setFullName(rs.getString("full_name"));
				feedbackDTO.setRating(rs.getInt("rating"));
				feedbackDTO.setComments(rs.getString("comments"));
				feedbackDTO.setFeedbackDate(rs.getTimestamp("feedback_date"));
				feedbackDTOlist.add(feedbackDTO);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
			
		}
		return feedbackDTOlist;

	}
	
	public List<FeedbackDTO> findAllFeedback() throws Exception {

		Connection connection = null;

		PreparedStatement pstmt = null;

		ResultSet rs = null;

		List<FeedbackDTO> feedbackDTOlist = new ArrayList<FeedbackDTO>();

		try {

			connection = dbUtil.getConnection();

			pstmt = connection.prepareStatement(FIND_ALL_FEEDBACK);

		

			rs = pstmt.executeQuery();

			while (rs.next()) {

				FeedbackDTO feedbackDTO = new FeedbackDTO();
				feedbackDTO.setEventName(rs.getString("event_name"));
				feedbackDTO.setFullName(rs.getString("full_name"));
				feedbackDTO.setRating(rs.getInt("rating"));
				feedbackDTO.setComments(rs.getString("comments"));
				feedbackDTO.setFeedbackDate(rs.getTimestamp("feedback_date"));
				feedbackDTOlist.add(feedbackDTO);

			}

		} catch (Exception e) {
			throw e;
		} finally {
			dbUtil.close(connection, pstmt, rs);
			
		}
		return feedbackDTOlist;

	}

}
