package com.backend.event.management.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import com.backend.event.management.dao.VolunteersDAO;
import com.backend.event.management.dto.FeedbackDTO;
import com.backend.event.management.dto.VolunteersDTO;
import com.backend.event.management.service.VolunteersService;
import com.backend.event.management.util.DBUtil;
import com.google.gson.Gson;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VolunteersServlet")
public class VolunteersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private VolunteersService volunteersService;

	public VolunteersServlet() {
		DBUtil dbUtil = new DBUtil();
		VolunteersDAO volunteersDAO = new VolunteersDAO(dbUtil);
		this.volunteersService = new VolunteersService(volunteersDAO);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("VolunteersServlet : doGet");
		String task = request.getParameter("task");
		System.out.println("task :" + task);

		if (task.equalsIgnoreCase("findById")) {
			try {
				findById(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("findAllVolunteers")) {
			try {
				findAllVolunteers(request, response);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("searchVolunteersBykeyword")) {
			try {
				searchVolunteersBykeyword(request, response);
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
		} else {
			System.out.println("No Task Match");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("VolunteersServlet: doPost");
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
		} else {
			System.out.println("No Task Match");
		}

	}

	public void destroy() {
		System.out.println("Servlet destroyed");
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

		VolunteersDTO volunteersDTO = new VolunteersDTO();
		volunteersDTO.setFullName(request.getParameter("full_name"));
		volunteersDTO.setEventName(request.getParameter("event_name"));
		volunteersDTO.setRoleInEvent(request.getParameter("role_in_event"));

			int count = volunteersService.save(volunteersDTO);
			if (count > 0) {
				System.out.println("Volunteer  created successfully");
				request.setAttribute("message", "Volunteer Created successfully");
				request.setAttribute("status", "success");
				request.setAttribute("redirectUrl", "");

				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			} else {
				System.out.println("Failed to create Volunteer.");
				request.setAttribute("message", "unable to Volunteer Created ");
				request.setAttribute("message", "error");
				request.setAttribute("redirectUrl", "signup.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("message", "unable to Volunteer due to :" + e.getMessage());
			request.setAttribute("status", "failed");
			request.setAttribute("redirectUrl", "signup.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);

		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			VolunteersDTO volunteersDTO = new VolunteersDTO();

			volunteersDTO.setId(Integer.parseInt(request.getParameter("Id")));
			volunteersDTO.setFullName(request.getParameter("full_name"));
			volunteersDTO.setEventName(request.getParameter("event_name"));
			volunteersDTO.setRoleInEvent(request.getParameter("role_in_event"));
			
				

				int count = volunteersService.update(volunteersDTO);
				if (count > 0) {
					System.out.println("Volunteer  update successfully");
					request.setAttribute("message", "Volunteer  update successfully");
					request.setAttribute("status", "success");
					request.setAttribute("redirectUrl", "home.jsp");

					RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
					rd.forward(request, response);
				} else {
					System.out.println("Volunteer not found.");
					request.setAttribute("message", "Volunteer not found. ");
					request.setAttribute("status", "error");
					request.setAttribute("redirectUrl", "home.jsp");
					RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
					rd.forward(request, response);

				}
			} catch (Exception e) {
				e.printStackTrace();

				request.setAttribute("message", "unable to update Volunteer due to :" + e.getMessage());
				request.setAttribute("status", "failed");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			}
	}

	private void deleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {


		try {

			int id = Integer.parseInt(request.getParameter("id"));

			int count = volunteersService.deleteById(id);
			if (count > 0) {
				System.out.println("Volunteer delete successfully");
				request.setAttribute("message", "Volunteer delete successfully");
				request.setAttribute("status", "success");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);
			} else {

				System.out.println("Volunteer not found.");
				request.setAttribute("message", "Volunteer not found. ");
				request.setAttribute("status", "Error");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("message", "Unable to delete Volunteer due to :" + e.getMessage());
			request.setAttribute("status", "Failed");
			request.setAttribute("redirectUrl", "home.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);

		}
	}

	private void findById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			int id = Integer.parseInt(request.getParameter("Id"));
		VolunteersDTO volunteersDTO = volunteersService.findById(id);
			if (volunteersDTO != null) {
				request.setAttribute("status", "success");
				request.setAttribute("message", "Volunteer Found successfully");
				request.setAttribute("editVolunteerFormDTO", volunteersDTO);
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("message", "Volunteer not found. ");
				request.setAttribute("status", "error");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Unable to find Volunteer due to :" + e.getMessage());
			request.setAttribute("status", "Failed");
			request.setAttribute("redirectUrl", "home.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);
		}
	}

	private void findAllVolunteers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			List<VolunteersDTO> volunteersDTOList = volunteersService.findAllVolunteers() ;
			if (!volunteersDTOList.isEmpty()) {
				request.setAttribute("volunteersDTOList", volunteersDTOList);
				RequestDispatcher rd = request.getRequestDispatcher("volunteers-list.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("message", "Failed to Find Volunteers ");
				request.setAttribute("status", "error");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "Failed to find Volunteers due to :" + e.getMessage());
			request.setAttribute("status", "Failed");
			request.setAttribute("redirectUrl", "home.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);
		}
	}

		
	

	private void searchVolunteersBykeyword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String keyword = request.getParameter("keyword");
			List<VolunteersDTO> volunteersDTOList = volunteersService.searchEventsBykeyword(keyword);

			
			String isAjax = request.getHeader("X-Requested-With");
			if ("XMLHttpRequest".equals(isAjax)) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				Gson gson = new Gson();
				response.getWriter().write(gson.toJson(volunteersDTOList));
				return;
			}

			
			if (!volunteersDTOList.isEmpty()) {
				request.setAttribute("status", "success");
				request.setAttribute("volunteersDTOList", volunteersDTOList);
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("message", "No Volunteers found");
				request.setAttribute("status", "Failed");
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "unable to found  Volunteers due to :" + e.getMessage());
			request.setAttribute("status", "error");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);
		}
	}

}
