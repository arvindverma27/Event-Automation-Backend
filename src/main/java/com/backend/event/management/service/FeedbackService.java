package com.backend.event.management.service;

import java.sql.Date;
import java.util.List;

import com.backend.event.management.dao.FeedbackDAO;
import com.backend.event.management.dto.FeedbackDTO;

public class FeedbackService {

	
	private FeedbackDAO feedbackDAO;
	
	public FeedbackService(FeedbackDAO feedbackDAO) {
		this.feedbackDAO = feedbackDAO;
	}
	
	public int save(FeedbackDTO feedbackDTO) throws Exception {
		int count = feedbackDAO.save(feedbackDTO);
		
		if(count > 0) {
			
		}
		return count;
	}
	
	public int update(FeedbackDTO feedbackDTO) throws Exception {
		return feedbackDAO.update(feedbackDTO);
	}
	
	public FeedbackDTO findById(int id) throws Exception {
		return feedbackDAO.findById(id);
	}
	
	public int deleteById(int id) throws Exception {
		return feedbackDAO.deleteById(id);
	}
	
	public List<FeedbackDTO> findByEventId(int eventId) throws Exception {
		return feedbackDAO.findByEventId(eventId);
	}
	
	public List<FeedbackDTO> findByUserId(int userId) throws Exception {
		return feedbackDAO.findByUserId(userId);
	}
	

	public List<FeedbackDTO> findByRating(int rating) throws Exception {
		return feedbackDAO.findByRating(rating);
	}
	public List<FeedbackDTO> searchFeedbackByDate(Date exactDate, Date startDate, Date endDate, boolean lastMonth) throws Exception {
		return feedbackDAO.searchFeedbackByDate(exactDate, startDate, endDate, lastMonth);
	}
	
	public List<FeedbackDTO> searchFeedbackBykeyword(String keyword) throws Exception {
		return feedbackDAO.searchFeedbackBykeyword(keyword);
	}
	
	public List<FeedbackDTO> findAllFeedback() throws Exception {
		return feedbackDAO.findAllFeedback();
	}
	
}
