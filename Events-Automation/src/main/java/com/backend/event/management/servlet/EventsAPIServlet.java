package com.backend.event.management.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import com.backend.event.management.dao.EventsDAO;
import com.backend.event.management.dto.APIResponse;
import com.backend.event.management.dto.EventsDTO;
import com.backend.event.management.service.EventsService;
import com.backend.event.management.util.DBUtil;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EventsAPIServlet")
public class EventsAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EventsService eventsService;
	private Gson gson;

	public EventsAPIServlet() {

		DBUtil dbUtil = new DBUtil();

		EventsDAO eventsDAO = new EventsDAO(dbUtil);

		this.eventsService = new EventsService(eventsDAO);
		this.gson = new Gson();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("EventsServlet : doGet Method");
		String task = request.getParameter("task");
		System.out.println("task :" + task);
		if (task.equalsIgnoreCase("findEventById")) {
			try {
				findById(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("findAllEvents")) {
			try {
				findAllEvents(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		} else if (task.equalsIgnoreCase("searchEventsByDate")) {
//			try {
//				searchEventsByDate(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		} else if (task.equalsIgnoreCase("searchEventsBykeyword")) {
			try {
				searchEventsBykeyword(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("No Task Match");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("EventsServlet : doPost Method");
		saveEvent(request, response);

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("EventsAPIServlet : doDelete Method");

		deletetById(request, response);
		

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("EventsServlet : doPut Method");
		try {
			updateEvent(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void destroy() {
		System.out.println("destroy : Object Created");
	}

	private void saveEvent(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		APIResponse apiResponse = null;
		try {

			EventsDTO eventsDTO = gson.fromJson(request.getReader(), EventsDTO.class);
			int count = eventsService.save(eventsDTO);
			if (count > 0) {
				System.out.println("Event Created Successfully");
				apiResponse = new APIResponse("success", "Event Created Successfully", 200);
			} else {
				System.out.println("faild to Create Event");
				apiResponse = new APIResponse("error", "faild to Create Event ", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Create Event Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	}

	private void updateEvent(HttpServletRequest request, HttpServletResponse response) throws Exception {


		APIResponse apiResponse = null;
		try {

			EventsDTO eventsDTO = gson.fromJson(request.getReader(), EventsDTO.class);
			int count = eventsService.update(eventsDTO);
			if (count > 0) {
				System.out.println("Event Updated Successfully");
				apiResponse = new APIResponse("success", "Event Updated Successfully", 200);
			} else {
				System.out.println("faild to Updated Event");
				apiResponse = new APIResponse("error", "faild to Updated Event ", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Updated Event Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	}

	

	private void findById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		APIResponse apiResponse = null;

		int eventId = Integer.parseInt(request.getParameter("eventId"));

		try {
			EventsDTO eventsDTO = eventsService.findById(eventId);
			if (eventsDTO != null) {
				System.out.println("User Found Successfully");
				apiResponse = new APIResponse("success", "User Found Successfully", eventsDTO, 200);
			} else {
				System.out.println("User Not Found");
				apiResponse = new APIResponse("error", "User Not Found", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Find User Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();

	}

	private void deletetById(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		APIResponse apiResponse = null;

		int id = Integer.parseInt(request.getParameter("id"));

		try {
			int count = eventsService.deletetById(id);
			if (count > 0) {
				System.out.println("Event Delete Successfully");
				apiResponse = new APIResponse("success", "Event Delete Successfully", 200);
			} else {
				System.out.println("Event Not Found");
				apiResponse = new APIResponse("error", "Event Not Delete", 500);
			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Delete Event Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();

	}

	public void searchEventsBykeyword(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;

		String keyword = request.getParameter("keyword");

		try {
			List<EventsDTO> eventsDTOList = eventsService.searchEventsBykeyword(keyword);
			if (!eventsDTOList.isEmpty()) {
				System.out.println("User Found Successfully");
				apiResponse = new APIResponse("success", "User Found Successfully", eventsDTOList, 200);
			} else {
				System.out.println("User Not Found");
				apiResponse = new APIResponse("error", "User Not Found", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Find User Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	}

//	private void searchEventsByDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		APIResponse apiResponse;
//
//		try {
//			String exactDateStr = request.getParameter("exactDate");
//			String startDateStr = request.getParameter("startDate");
//			String endDateStr = request.getParameter("endDate");
//			String lastMonthStr = request.getParameter("lastMonth");
//
//			Date exactDate = null;
//			Date startDate = null;
//			Date endDate = null;
//			boolean lastMonth = false;
//
//			if (exactDateStr != null && !exactDateStr.isEmpty()) {
//				exactDate = Date.valueOf(exactDateStr);
//			}
//
//			if (startDateStr != null && endDateStr != null && !startDateStr.isEmpty() && !endDateStr.isEmpty()) {
//				startDate = Date.valueOf(startDateStr);
//				endDate = Date.valueOf(endDateStr);
//			}
//
//			if (lastMonthStr != null && lastMonthStr.equals("true")) {
//				lastMonth = true;
//			}
//
//			List<EventsDTO> eventsDTOList = eventsService.searchEventsByDate(exactDate, startDate, endDate, lastMonth);
//
//			apiResponse = new APIResponse(true, "Events fetched", eventsDTOList, 500);
//
//		} catch (Exception e) {
//			apiResponse = new APIResponse(false, "Events Not Found due to " + e.getMessage(), 500);
//		}
//		response.setContentType("application/json");
//		response.setStatus(apiResponse.getStatusCode());
//		PrintWriter printWrite = response.getWriter();
//		printWrite.append(gson.toJson(apiResponse));
//		printWrite.close();
//
//	}

	private void findAllEvents(HttpServletRequest request, HttpServletResponse response) throws Exception {

    APIResponse apiResponse;

    try {
        List<EventsDTO> eventsDTOList = eventsService.findAllEvents();

        if (eventsDTOList != null && !eventsDTOList.isEmpty()) {
            System.out.println("Events fetched successfully");
            apiResponse = new APIResponse("success", "Events fetched successfully", eventsDTOList, 200);
        } else {
            System.out.println("No events found");
            apiResponse = new APIResponse("error", "No events found", 404);
        }

    } catch (Exception e) {
        e.printStackTrace();
        apiResponse = new APIResponse("error", "Failed to fetch events: " + e.getMessage(), 500);
    }

    response.setContentType("application/json");
    response.setStatus(apiResponse.getStatusCode());
    PrintWriter out = response.getWriter();
    out.append(gson.toJson(apiResponse));
    out.close();
}


}
