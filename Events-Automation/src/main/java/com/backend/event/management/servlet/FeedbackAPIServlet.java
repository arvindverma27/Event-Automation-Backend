package com.backend.event.management.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import com.backend.event.management.dao.FeedbackDAO;
import com.backend.event.management.dto.APIResponse;
import com.backend.event.management.dto.EventsDTO;
import com.backend.event.management.dto.FeedbackDTO;
import com.backend.event.management.service.FeedbackService;
import com.backend.event.management.util.DBUtil;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FeedbackAPIServlet")
public class FeedbackAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private FeedbackService feedbackService;
	
	private Gson gson;
	

	public FeedbackAPIServlet() {

		DBUtil dbUtil = new DBUtil();
		FeedbackDAO feedbackDAO = new FeedbackDAO(dbUtil);
		this.feedbackService = new FeedbackService(feedbackDAO);
		this.gson = new  Gson();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("FeedbackAPIServlet : doGet");
		String task = request.getParameter("task");
		System.out.println("task :" + task);
		if (task.equalsIgnoreCase("findById")) {
			try {
				findById(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//		} else if (task.equalsIgnoreCase("findByUserId")) {
//			try {
//				findByUserId(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//	} else if (task.equalsIgnoreCase("findByEventName")) {
//			try {
//				findByEventName(request, response);
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
		} else if (task.equalsIgnoreCase("findAllFeedback")) {
			try {
				findAllFeedback(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("searchFeedbackBykeyword")) {
			try {
				searchFeedbackBykeyword(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		} else if (task.equalsIgnoreCase("searchFeedbackByDate")) {
//			try {
//				searchFeedbackByDate(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} else {
			System.out.println("NO task Match");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("FeedbackAPIServlet : doPost");
		String task = request.getParameter("task");
		System.out.println("task :" + task);
		if (task.equalsIgnoreCase("StudentFeedback")) {
			try {
				save(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {
			System.out.println("No Task Match");
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("FeedbackAPIServlet : doPut");
		try {
			update(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("FeedbackAPIServlet : doDelete");
		try {
			deleteFeedbackById(request, response);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
	}

	public void destroy() {
		System.out.println("Servlet destroyed");
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		APIResponse apiResponse = null;
		try {

			FeedbackDTO feedbackDTO = gson.fromJson(request.getReader(), FeedbackDTO.class);
			int count = feedbackService.save(feedbackDTO);
			if (count > 0) {
				System.out.println("Feedback Created Successfully");
				apiResponse = new APIResponse("success", "Feedback Created Successfully", 200);
			} else {
				System.out.println("faild to Create Event");
				apiResponse = new APIResponse("error", "faild to Create Feedback ", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Create Feedback Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();


	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;
		try {

			FeedbackDTO feedbackDTO = gson.fromJson(request.getReader(), FeedbackDTO.class);
			int count = feedbackService.update(feedbackDTO);
			if (count > 0) {
				System.out.println("Event Updated Successfully");
				apiResponse = new APIResponse("success", "Event Updated Successfully", 200);
			} else {
				System.out.println("faild to Updated Event");
				apiResponse = new APIResponse("error", "faild to Updated Event ", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Create Updated Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();

		
	}

	private void findById(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;

		int feedbackId  = Integer.parseInt(request.getParameter("feedbackId"));

		try {
			FeedbackDTO feedbackDTO = feedbackService.findById(feedbackId);
			if (feedbackDTO != null) {
				System.out.println("Feedback Found Successfully");
				apiResponse = new APIResponse("success", "Feedback Found Successfully", feedbackDTO, 200);
			} else {
				System.out.println("Feedback Not Found");
				apiResponse = new APIResponse("error", "Feedback Not Found", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Find Feedback Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	
	}

	private void deleteFeedbackById(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;

		int feedbackId  = Integer.parseInt(request.getParameter("feedbackId"));

		try {
			int count = feedbackService.deleteFeedbackById(feedbackId);
			if (count > 0) {
				System.out.println("Feedback Delete Successfully");
				apiResponse = new APIResponse("success", "Feedback Delete Successfully", 200);
			} else {
				System.out.println("Feedback Not Found");
				apiResponse = new APIResponse("error", "Feedback Not Delete", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Delete Feedback Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	}

	private void searchFeedbackBykeyword(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;

		String keyword = request.getParameter("keyword");

		try {
			List<FeedbackDTO> feedbackDTOList = feedbackService.searchFeedbackBykeyword(keyword);
			if (!feedbackDTOList.isEmpty()) {
				System.out.println("Feedback Found Successfully");
				apiResponse = new APIResponse("success", "Feedback Found Successfully", feedbackDTOList, 200);
			} else {
				System.out.println("Feedback Not Found");
				apiResponse = new APIResponse("error", "Feedback Not Found", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Find Feedback Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
		
		
	}

	private void findAllFeedback(HttpServletRequest request, HttpServletResponse response) throws Exception {


		APIResponse apiResponse = null;

		try {
			List<FeedbackDTO> feedbackDTOList = feedbackService.findAllFeedback();
			if (!feedbackDTOList.isEmpty()) {
				System.out.println("Feedback Found Successfully");
				apiResponse = new APIResponse("success", "Feedback Found Successfully", feedbackDTOList, 200);
			} else {
				System.out.println("Feedback Not Found");
				apiResponse = new APIResponse("error", "Feedback Not Found", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Find Feedback Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	}

	
}


//private void findByEventName(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//	APIResponse apiResponse = null;
//
//	String eventName = request.getParameter("eventName");
//
//	try {
//		
//		List<FeedbackDTO> feedbackDTOList = feedbackService.findByEventName(eventName);
//	
//		if (!feedbackDTOList.isEmpty()) {
//			System.out.println("Feedback Found Successfully");
//			apiResponse = new APIResponse("success", "Feedback Found Successfully", feedbackDTOList, 200);
//		} else {
//			System.out.println("Feedback Not Found");
//			apiResponse = new APIResponse("error", "Feedback Not Found", 500);
//
//		}
//
//	} catch (Exception e) {
//		e.printStackTrace();
//		apiResponse = new APIResponse("error", "faild to Find Feedback Due to :" + e.getMessage(), 500);
//
//	}
//
//	response.setContentType("application/json");
//	response.setStatus(apiResponse.getStatusCode());
//	PrintWriter printWrite = response.getWriter();
//	printWrite.append(gson.toJson(apiResponse));
//	printWrite.close();
//
//}
//
//
//
//private void findByRating(HttpServletRequest request, HttpServletResponse response) throws Exception {
//	
//	APIResponse apiResponse = null;
//
//	int rating  = Integer.parseInt(request.getParameter("rating"));
//
//	try {
//		List<FeedbackDTO> feedbackDTOList  = feedbackService.findByRating(rating);
//		if (!feedbackDTOList.isEmpty()) {
//			System.out.println("Feedback find Successfully");
//			apiResponse = new APIResponse("success", "Feedback find Successfully", feedbackDTOList, 200);
//		} else {
//			System.out.println("Feedback Not Found");
//			apiResponse = new APIResponse("error", "Feedback Not Delete", 500);
//
//		}
//
//	} catch (Exception e) {
//		e.printStackTrace();
//		apiResponse = new APIResponse("error", "faild to find Feedback Due to :" + e.getMessage(), 500);
//
//	}
//
//	response.setContentType("application/json");
//	response.setStatus(apiResponse.getStatusCode());
//	PrintWriter printWrite = response.getWriter();
//	printWrite.append(gson.toJson(apiResponse));
//	printWrite.close();
//	
//}

//private void searchFeedbackByDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//	APIResponse apiResponse;
//
//	try {
//		String exactDateStr = request.getParameter("exactDate");
//		String startDateStr = request.getParameter("startDate");
//		String endDateStr = request.getParameter("endDate");
//		String lastMonthStr = request.getParameter("lastMonth");
//
//		Date exactDate = null;
//		Date startDate = null;
//		Date endDate = null;
//		boolean lastMonth = false;
//
//		if (exactDateStr != null && !exactDateStr.isEmpty()) {
//			exactDate = Date.valueOf(exactDateStr);
//		}
//
//		if (startDateStr != null && endDateStr != null && !startDateStr.isEmpty() && !endDateStr.isEmpty()) {
//			startDate = Date.valueOf(startDateStr);
//			endDate = Date.valueOf(endDateStr);
//		}
//
//		if (lastMonthStr != null && lastMonthStr.equals("true")) {
//			lastMonth = true;
//		}
//
//		List<FeedbackDTO> feedbackDTOList = feedbackService.searchFeedbackByDate(exactDate, startDate, endDate, lastMonth);
//
//		apiResponse = new APIResponse(true, "Feedback fetched", feedbackDTOList, 500);
//
//	} catch (Exception e) {
//		apiResponse = new APIResponse(false, "Feedback Not Found due to " + e.getMessage(), 500);
//	}
//	response.setContentType("application/json");
//	response.setStatus(apiResponse.getStatusCode());
//	PrintWriter printWrite = response.getWriter();
//	printWrite.append(gson.toJson(apiResponse));
//	printWrite.close();
//
//	
//}



//private void findByUserId(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//	
//}
