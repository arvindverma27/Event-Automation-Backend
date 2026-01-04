//package com.backend.event.management.servlet;
//
//import java.io.IOException;
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.util.List;
//
//import com.backend.event.management.dao.FeedbackDAO;
//import com.backend.event.management.dto.FeedbackDTO;
//import com.backend.event.management.service.FeedbackService;
//import com.backend.event.management.util.DBUtil;
//import com.google.gson.Gson;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/FeedbackServlet")
//public class FeedbackServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	private FeedbackService feedbackService;
//
//	public FeedbackServlet() {
//
//		DBUtil dbUtil = new DBUtil();
//		FeedbackDAO feedbackDAO = new FeedbackDAO(dbUtil);
//		this.feedbackService = new FeedbackService(feedbackDAO);
//	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		System.out.println("FeedbackServlet : doGet");
//		String task = request.getParameter("task");
//		System.out.println("task :" + task);
//		if (task.equalsIgnoreCase("findById")) {
//			try {
//				findById(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else if (task.equalsIgnoreCase("deleteById")) {
//			try {
//				deleteById(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
////		}else if (task.equalsIgnoreCase("findByUserId")) {
////				try {
////					findByUserId(request, response);
////				} catch (Exception e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////
////		} else if (task.equalsIgnoreCase("findByEventId")) {
//			try {
//				findByEventId(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else if (task.equalsIgnoreCase("findByRating")) {
//			try {
//				findByRating(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else if (task.equalsIgnoreCase("findAllFeedback")) {
//			try {
//				findAllFeedback(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else if (task.equalsIgnoreCase("searchFeedbackBykeyword")) {
//			try {
//				searchFeedbackBykeyword(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else if (task.equalsIgnoreCase("searchFeedbackByDate")) {
//			try {
//				searchFeedbackByDate(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			System.out.println("NO task Match");
//		}
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		System.out.println("FeedbackServlet : doPost");
//		String task = request.getParameter("task");
//		System.out.println("task :" + task);
//		if (task.equalsIgnoreCase("save")) {
//			try {
//				save(request, response);
//			} catch (Exception e) {
//
//				e.printStackTrace();
//			}
//		} else if (task.equalsIgnoreCase("update")) {
//			try {
//				update(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			System.out.println("No Task Match");
//		}
//	}
//
//	public void destroy() {
//		System.out.println("Servlet destroyed");
//	}
//
//	private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//
//			FeedbackDTO feedbackDTO = new FeedbackDTO();
//
//			feedbackDTO.setEventName(request.getParameter("event_name"));
//			feedbackDTO.setFullName(request.getParameter("full_name"));
//			feedbackDTO.setRating(Integer.parseInt(request.getParameter("rating")));
//			feedbackDTO.setComments(request.getParameter("comments"));
//			feedbackDTO.setFeedbackDate(new Timestamp(System.currentTimeMillis()));
//
//
//			int count = feedbackService.save(feedbackDTO);
//			if (count > 0) {
//				System.out.println("feedback  created successfully");
//				request.setAttribute("message", "feedback Created successfully");
//				request.setAttribute("status", "success");
//				request.setAttribute("redirectUrl", "");
//
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//
//			} else {
//				System.out.println("Failed to create feedback.");
//				request.setAttribute("message", "unable to feedback Created ");
//				request.setAttribute("message", "error");
//				request.setAttribute("redirectUrl", "signup.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			request.setAttribute("message", "unable to feedback due to :" + e.getMessage());
//			request.setAttribute("status", "failed");
//			request.setAttribute("redirectUrl", "signup.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//
//		}
//
//	}
//
//	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//
//		try {
//		FeedbackDTO feedbackDTO = new FeedbackDTO();
//
//		feedbackDTO.setId(Integer.parseInt(request.getParameter("Id")));
//		feedbackDTO.setRating(Integer.parseInt(request.getParameter("rating")));
//		feedbackDTO.setComments(request.getParameter("comments"));
//			
//
//			int count = feedbackService.update(feedbackDTO);
//			if (count > 0) {
//				System.out.println("feedback Form update successfully");
//				request.setAttribute("message", "feedback Form update successfully");
//				request.setAttribute("status", "success");
//				request.setAttribute("redirectUrl", "home.jsp");
//
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			} else {
//				System.out.println("feedback Form not found.");
//				request.setAttribute("message", "feedback Form not found. ");
//				request.setAttribute("status", "error");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			request.setAttribute("message", "unable to update feedback due to :" + e.getMessage());
//			request.setAttribute("status", "failed");
//			request.setAttribute("redirectUrl", "home.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//
//		}
//	}
//
//	private void findById(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//			int id = Integer.parseInt(request.getParameter("Id"));
//			FeedbackDTO feedbackDTO = feedbackService.findById(id);
//			if (feedbackDTO != null) {
//				request.setAttribute("status", "success");
//				request.setAttribute("message", "feedback Found successfully");
//				request.setAttribute("editFeedbackFormDTO", feedbackDTO);
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				rd.forward(request, response);
//			} else {
//				request.setAttribute("message", "feedback not found. ");
//				request.setAttribute("status", "error");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("message", "Unable to find feedback due to :" + e.getMessage());
//			request.setAttribute("status", "Failed");
//			request.setAttribute("redirectUrl", "home.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//		}
//	}
//	private void deleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {
//	
//		
//
//			try {
//
//				int id = Integer.parseInt(request.getParameter("id"));
//
//				int count = feedbackService.deleteFeedbackById(id);
//				if (count > 0) {
//					System.out.println("Feedback delete successfully");
//					request.setAttribute("message", "Feedback delete successfully");
//					request.setAttribute("status", "success");
//					request.setAttribute("redirectUrl", "home.jsp");
//					RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//					rd.forward(request, response);
//				} else {
//
//					System.out.println("Feedback not found.");
//					request.setAttribute("message", "Feedback not found. ");
//					request.setAttribute("status", "Error");
//					request.setAttribute("redirectUrl", "home.jsp");
//					RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//					rd.forward(request, response);
//
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//
//				request.setAttribute("message", "Unable to delete Feedback due to :" + e.getMessage());
//				request.setAttribute("status", "Failed");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//
//			}
//		}
//	
//
//	private void findByEventId(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//			String eventName = request.getParameter("eventName");
//             List<FeedbackDTO> feedbackDTOList = feedbackService.findByEventName(eventName);
//
//			
//			String isAjax = request.getHeader("X-Requested-With");
//			if ("XMLHttpRequest".equals(isAjax)) {
//				response.setContentType("application/json");
//				response.setCharacterEncoding("UTF-8");
//				Gson gson = new Gson();
//				response.getWriter().write(gson.toJson(feedbackDTOList));
//				return;
//			}
//
//			
//			if (!feedbackDTOList.isEmpty()) {
//				request.setAttribute("status", "success");
//				request.setAttribute("feedbackDTOList", feedbackDTOList);
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				rd.forward(request, response);
//			} else {
//				request.setAttribute("message", "No Feedback found");
//				request.setAttribute("status", "Failed");
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				rd.forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("message", "unable to found  Feedback due to :" + e.getMessage());
//			request.setAttribute("status", "error");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//		}
//	}
////
////	private void findByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
////
////		try {
////			int userId = Integer.parseInt(request.getParameter("userId"));
////             List<FeedbackDTO> feedbackDTOList = feedbackService.findByUserId(userId);
////
////			
////			String isAjax = request.getHeader("X-Requested-With");
////			if ("XMLHttpRequest".equals(isAjax)) {
////				response.setContentType("application/json");
////				response.setCharacterEncoding("UTF-8");
////				Gson gson = new Gson();
////				response.getWriter().write(gson.toJson(feedbackDTOList));
////				return;
////			}
////
////			
////			if (!feedbackDTOList.isEmpty()) {
////				request.setAttribute("status", "success");
////				request.setAttribute("feedbackDTOList", feedbackDTOList);
////				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
////				rd.forward(request, response);
////			} else {
////				request.setAttribute("message", "No Feedback found");
////				request.setAttribute("status", "Failed");
////				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
////				rd.forward(request, response);
////			}
////		} catch (Exception e) {
////			e.printStackTrace();
////			request.setAttribute("message", "unable to found  Feedback due to :" + e.getMessage());
////			request.setAttribute("status", "error");
////			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
////			rd.forward(request, response);
////		}
////	}
//
//	private void findByRating(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		try {
//			int rating = Integer.parseInt(request.getParameter("rating"));
//             List<FeedbackDTO> feedbackDTOList = feedbackService.findByRating(rating);
//
//			
//			String isAjax = request.getHeader("X-Requested-With");
//			if ("XMLHttpRequest".equals(isAjax)) {
//				response.setContentType("application/json");
//				response.setCharacterEncoding("UTF-8");
//				Gson gson = new Gson();
//				response.getWriter().write(gson.toJson(feedbackDTOList));
//				return;
//			}
//
//			
//			if (!feedbackDTOList.isEmpty()) {
//				request.setAttribute("status", "success");
//				request.setAttribute("feedbackDTOList", feedbackDTOList);
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				rd.forward(request, response);
//			} else {
//				request.setAttribute("message", "No Feedback found");
//				request.setAttribute("status", "Failed");
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				rd.forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("message", "unable to found  Feedback due to :" + e.getMessage());
//			request.setAttribute("status", "error");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//		}
//	}
//
//	private void searchFeedbackByDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//    try {
//        String exact = request.getParameter("exact_date");
//        String start = request.getParameter("start_date");
//        String end = request.getParameter("end_date");
//        String last = request.getParameter("last_month");
//
//        Date exactDate = null;
//        Date startDate = null;
//        Date endDate = null;
//        boolean lastMonth = false;
//
//        if (exact != null && !exact.trim().isEmpty()) {
//            exactDate = Date.valueOf(exact);
//        }
//
//        if (start != null && !start.trim().isEmpty()) {
//            startDate = Date.valueOf(start);
//        }
//
//        if (end != null && !end.trim().isEmpty()) {
//            endDate = Date.valueOf(end);
//        }
//
//        if ("true".equals(last)) {
//            lastMonth = true;
//        }
//
//        List<FeedbackDTO> feedbackList =
//                feedbackService.searchFeedbackByDate(exactDate, startDate, endDate, lastMonth);
//
//        String isAjax = request.getHeader("X-Requested-With");
//        if ("XMLHttpRequest".equals(isAjax)) {
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            Gson gson = new Gson();
//            response.getWriter().write(gson.toJson(feedbackList));
//            return;
//        }
//
//        if (!feedbackList.isEmpty()) {
//            request.setAttribute("status", "success");
//            request.setAttribute("feedbackList", feedbackList);
//            RequestDispatcher rd = request.getRequestDispatcher("feedback-search-result.jsp");
//            rd.forward(request, response);
//        } else {
//            request.setAttribute("message", "No Feedback found for given date");
//            request.setAttribute("status", "Failed");
//            RequestDispatcher rd = request.getRequestDispatcher("feedback-search-result.jsp");
//            rd.forward(request, response);
//        }
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        request.setAttribute("message", "Unable to fetch feedback: " + e.getMessage());
//        request.setAttribute("status", "error");
//        RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//        rd.forward(request, response);
//    }
//}
//
//
//
//	private void searchFeedbackBykeyword(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//			String keyword = request.getParameter("keyword");
//			List<FeedbackDTO> feedbackDTOList = feedbackService.searchFeedbackBykeyword(keyword);
//
//			
//			String isAjax = request.getHeader("X-Requested-With");
//			if ("XMLHttpRequest".equals(isAjax)) {
//				response.setContentType("application/json");
//				response.setCharacterEncoding("UTF-8");
//				Gson gson = new Gson();
//				response.getWriter().write(gson.toJson(feedbackDTOList));
//				return;
//			}
//
//			
//			if (!feedbackDTOList.isEmpty()) {
//				request.setAttribute("status", "success");
//				request.setAttribute("feedbackDTOlist", feedbackDTOList);
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				rd.forward(request, response);
//			} else {
//				request.setAttribute("message", "No Feedback found");
//				request.setAttribute("status", "Failed");
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				rd.forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("message", "unable to found  Feedback due to :" + e.getMessage());
//			request.setAttribute("status", "error");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//		}
//	}
//	
//	private void findAllFeedback(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//			List<FeedbackDTO> feedbackDTOList = feedbackService.findAllFeedback();
//			if (!feedbackDTOList.isEmpty()) {
//				request.setAttribute("feedbackDTOList", feedbackDTOList);
//				RequestDispatcher rd = request.getRequestDispatcher("user-list.jsp");
//				rd.forward(request, response);
//			} else {
//				request.setAttribute("message", "Failed to Find Feedback ");
//				request.setAttribute("status", "error");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("message", "Failed to find Feedback due to :" + e.getMessage());
//			request.setAttribute("status", "Failed");
//			request.setAttribute("redirectUrl", "home.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//		}
//	}
//
//}
