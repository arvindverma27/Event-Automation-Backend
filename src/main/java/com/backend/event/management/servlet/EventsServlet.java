package com.backend.event.management.servlet;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import com.backend.event.management.dao.EventsDAO;
import com.backend.event.management.dto.EventsDTO;
import com.backend.event.management.dto.FeedbackDTO;
import com.backend.event.management.service.EventsService;
import com.backend.event.management.util.DBUtil;
import com.google.gson.Gson;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EventsServlet")
public class EventsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private EventsService eventsService;

	public EventsServlet() {

		DBUtil dbUtil = new DBUtil();

		EventsDAO eventsDAO = new EventsDAO(dbUtil);

		this.eventsService = new EventsService(eventsDAO);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("EventsServlet : doGet Method");
		String task = request.getParameter("task");
		System.out.println("task :" + task);
		if (task.equalsIgnoreCase("findById")) {
			try {
				findById(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("findAllEvents")) {
			try {
				findAllEvents(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("deleteById")) {
			try {
				deleteById(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("searchEventsByDate")) {
			try {
				searchEventsByDate(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("searchEventsBykeyword")) {
			try {
				searchEventsBykeyword(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("No task Match");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("EventsServlet : doPost Method");
		String task = request.getParameter("task");
		System.out.println("task :" + task);

		if (task.equalsIgnoreCase("save")) {
			try {
				save(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("update")) {
			try {
				update(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void destroy() {
		System.out.println("destroy : Object Created");
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			EventsDTO eventsDTO = new EventsDTO();
			eventsDTO.setUsersId(Integer.parseInt(request.getParameter("users_id")));
			eventsDTO.setEventName(request.getParameter("event_name"));
			eventsDTO.setEventDate(request.getParameter("event_date"));
			eventsDTO.setLocation(request.getParameter("location"));
			eventsDTO.setDescription(request.getParameter("description"));
			eventsDTO.setCreatedBy(Integer.parseInt(request.getParameter("created_by")));

			int count = eventsService.save(eventsDTO);
			if (count > 0) {
				System.out.println("Event  created successfully");
				request.setAttribute("message", "Event Created successfully");
				request.setAttribute("status", "success");
				request.setAttribute("redirectUrl", "");

				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			} else {
				System.out.println("Failed to create Event.");
				request.setAttribute("message", "unable to Event Created ");
				request.setAttribute("message", "error");
				request.setAttribute("redirectUrl", "event.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("message", "unable to Event due to :" + e.getMessage());
			request.setAttribute("status", "failed");
			request.setAttribute("redirectUrl", "event.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);

		}

	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			EventsDTO eventsDTO = new EventsDTO();
			eventsDTO.setId(Integer.parseInt(request.getParameter("Id")));
			eventsDTO.setUsersId(Integer.parseInt(request.getParameter("users_id")));
			eventsDTO.setEventName(request.getParameter("event_name"));
			eventsDTO.setEventDate(request.getParameter("event_date"));
			eventsDTO.setLocation(request.getParameter("location"));
			eventsDTO.setDescription(request.getParameter("description"));
			eventsDTO.setCreatedBy(Integer.parseInt(request.getParameter("created_by")));

			int count = eventsService.update(eventsDTO);

			if (count > 0) {
				System.out.println("feedback Form update successfully");
				request.setAttribute("message", "feedback Form update successfully");
				request.setAttribute("status", "success");
				request.setAttribute("redirectUrl", "home.jsp");

				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("feedback Form not found.");
				request.setAttribute("message", "feedback Form not found. ");
				request.setAttribute("status", "error");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("message", "unable to update feedback due to :" + e.getMessage());
			request.setAttribute("status", "failed");
			request.setAttribute("redirectUrl", "home.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);

		}
	}

	private void findById(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			int id = Integer.parseInt(request.getParameter("Id"));
			EventsDTO eventsDTO = eventsService.findById(id);
			
			if (eventsDTO != null) {
				request.setAttribute("status", "success");
				request.setAttribute("message", "Event Found successfully");
				request.setAttribute("editEventDTO", eventsDTO);
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("message", "Event not found. ");
				request.setAttribute("status", "error");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Unable to find Event due to :" + e.getMessage());
			request.setAttribute("status", "Failed");
			request.setAttribute("redirectUrl", "home.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);
		}
	}

	private void deleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {


		try {

			int id = Integer.parseInt(request.getParameter("id"));

			int count = eventsService.deleteById(id);
			if (count > 0) {
				System.out.println("Event delete successfully");
				request.setAttribute("message", "Event delete successfully");
				request.setAttribute("status", "success");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);
			} else {

				System.out.println("Event not found.");
				request.setAttribute("message", "Event not found. ");
				request.setAttribute("status", "Error");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("message", "Unable to Event Events due to :" + e.getMessage());
			request.setAttribute("status", "Failed");
			request.setAttribute("redirectUrl", "home.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);

		}
	}

	private void searchEventsBykeyword(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String keyword = request.getParameter("keyword");
			List<EventsDTO> eventsDTOList = eventsService.searchEventsBykeyword(keyword);

			
			String isAjax = request.getHeader("X-Requested-With");
			if ("XMLHttpRequest".equals(isAjax)) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				Gson gson = new Gson();
				response.getWriter().write(gson.toJson(eventsDTOList));
				return;
			}

			
			if (!eventsDTOList.isEmpty()) {
				request.setAttribute("status", "success");
				request.setAttribute("eventsDTOList", eventsDTOList);
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("message", "No Events found");
				request.setAttribute("status", "Failed");
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "unable to found  Events due to :" + e.getMessage());
			request.setAttribute("status", "error");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);
		}
	}

	private void searchEventsByDate(HttpServletRequest request, HttpServletResponse response) throws Exception {

		  try {
		        String exact = request.getParameter("exact_date");
		        String start = request.getParameter("start_date");
		        String end = request.getParameter("end_date");
		        String last = request.getParameter("last_month");

		        Date exactDate = null;
		        Date startDate = null;
		        Date endDate = null;
		        boolean lastMonth = false;

		        if (exact != null && !exact.trim().isEmpty()) {
		            exactDate = Date.valueOf(exact);
		        }

		        if (start != null && !start.trim().isEmpty()) {
		            startDate = Date.valueOf(start);
		        }

		        if (end != null && !end.trim().isEmpty()) {
		            endDate = Date.valueOf(end);
		        }

		        if ("true".equals(last)) {
		            lastMonth = true;
		        }

		        List<EventsDTO> eventsDTOList =
		                eventsService.searchEventsByDate(exactDate, startDate, endDate, lastMonth);

		        String isAjax = request.getHeader("X-Requested-With");
		        if ("XMLHttpRequest".equals(isAjax)) {
		            response.setContentType("application/json");
		            response.setCharacterEncoding("UTF-8");
		            Gson gson = new Gson();
		            response.getWriter().write(gson.toJson(eventsDTOList));
		            return;
		        }

		        if (!eventsDTOList.isEmpty()) {
		            request.setAttribute("status", "success");
		            request.setAttribute("feedbackList", eventsDTOList);
		            RequestDispatcher rd = request.getRequestDispatcher("event-search-result.jsp");
		            rd.forward(request, response);
		        } else {
		            request.setAttribute("message", "No Event found for given date");
		            request.setAttribute("status", "Failed");
		            RequestDispatcher rd = request.getRequestDispatcher("event-search-result.jsp");
		            rd.forward(request, response);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		        request.setAttribute("message", "Unable to fetch Event: " + e.getMessage());
		        request.setAttribute("status", "error");
		        RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
		        rd.forward(request, response);
		    }
	}

	private void findAllEvents(HttpServletRequest request, HttpServletResponse response) throws Exception {


		try {
			List<EventsDTO> eventsDTOList = eventsService.findAllEvents();
			if (!eventsDTOList.isEmpty()) {
				request.setAttribute("feedbackDTOList", eventsDTOList);
				RequestDispatcher rd = request.getRequestDispatcher("event-list.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("message", "Failed to Find Feedback ");
				request.setAttribute("status", "error");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to find Events due to :" + e.getMessage());
			request.setAttribute("status", "Failed");
			request.setAttribute("redirectUrl", "home.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);
		}
	}
	}


