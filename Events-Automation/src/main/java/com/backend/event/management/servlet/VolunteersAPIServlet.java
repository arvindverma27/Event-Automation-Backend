package com.backend.event.management.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.backend.event.management.dao.VolunteersDAO;
import com.backend.event.management.dto.APIResponse;
import com.backend.event.management.dto.RegistrationsDTO;
import com.backend.event.management.dto.VolunteersDTO;
import com.backend.event.management.service.VolunteersService;
import com.backend.event.management.util.DBUtil;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/VolunteersAPIServlet")
public class VolunteersAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private VolunteersService volunteersService;

	private Gson gson;

	public VolunteersAPIServlet() {
		DBUtil dbUtil = new DBUtil();
		VolunteersDAO volunteersDAO = new VolunteersDAO(dbUtil);
		this.volunteersService = new VolunteersService(volunteersDAO);

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("VolunteersAPIServlet : doGet");
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

				e.printStackTrace();
			}
		} else {
			System.out.println("No Task Match");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("VolunteersAPIServlet: doPost");
		String task = request.getParameter("task");
		System.out.println("task :" + task);
		if (task.equalsIgnoreCase("save")) {
			try {
				save(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("No Task Match");
		}

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("VolunteersAPIServlet: doPut");
		try {
			update(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("VolunteersAPIServlet: doDelete");
		try {
			deleteById(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void destroy() {
		System.out.println("VolunteersAPIServlet destroyed");
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;
		try {

			VolunteersDTO volunteersDTO = gson.fromJson(request.getReader(), VolunteersDTO.class);
			int count = volunteersService.save(volunteersDTO);
			if (count > 0) {
				System.out.println("Volunteer Created  Successfully");
				apiResponse = new APIResponse("success", "Volunteer Created Successfully", 200);
			} else {
				System.out.println("faild to Create Volunteer ");
				apiResponse = new APIResponse("error", "faild to Volunteer Created", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to  Volunteer Created Due to :" + e.getMessage(), 500);

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

			VolunteersDTO volunteersDTO = gson.fromJson(request.getReader(), VolunteersDTO.class);
			int count = volunteersService.update(volunteersDTO);
			if (count > 0) {
				System.out.println("Volunteer Updated Successfully");
				apiResponse = new APIResponse("success", "Volunteer Updated Successfully", 200);
			} else {
				System.out.println("faild to Create Volunteer ");
				apiResponse = new APIResponse("error", "faild to Volunteer Updated", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to  Volunteer Updated Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	}

	private void deleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;

		int volunteerId  = Integer.parseInt(request.getParameter("volunteerId"));

		try {
			int count = volunteersService.deleteById(volunteerId);
			if (count > 0) {
				System.out.println("Registration Found Successfully");
				apiResponse = new APIResponse("success", "Registration Found Successfully", 200);
			} else {
				System.out.println("Registration Not Found");
				apiResponse = new APIResponse("error", "Registration Not Found", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Find Registration Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	
	}

	private void findById(HttpServletRequest request, HttpServletResponse response) throws Exception {


		APIResponse apiResponse = null;

		int volunteerId  = Integer.parseInt(request.getParameter("volunteerId"));

		try {
			VolunteersDTO volunteersDTO= volunteersService.findById(volunteerId);
			if (volunteersDTO != null) {
				System.out.println("Registration Found Successfully");
				apiResponse = new APIResponse("success", "Registration Found Successfully", volunteersDTO, 200);
			} else {
				System.out.println("Registration Not Found");
				apiResponse = new APIResponse("error", "Registration Not Found", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Find Registration Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	}

	private void findAllVolunteers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;

		
		try {
			List<VolunteersDTO>  volunteersDTOList = volunteersService.findAllVolunteers();
			if (!volunteersDTOList.isEmpty()) {
				System.out.println(" Volunteers Found Successfully");
				apiResponse = new APIResponse("success", "Volunteers Found Successfully", volunteersDTOList, 200);
			} else {
				System.out.println("Volunteers Not Found");
				apiResponse = new APIResponse("error", "Volunteers Not Found", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Find Volunteers Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	}

	
	private void searchVolunteersBykeyword(HttpServletRequest request, HttpServletResponse response) throws Exception {
		APIResponse apiResponse = null;

		String keyword = request.getParameter("keyword");

		try {
			List<VolunteersDTO>  volunteersDTOList = volunteersService.searchEventsBykeyword(keyword);
			if (!volunteersDTOList.isEmpty()) {
				System.out.println("Volunteer Found Successfully");
				apiResponse = new APIResponse("success", "Volunteer Found Successfully", volunteersDTOList, 200);
			} else {
				System.out.println("Volunteer Not Found");
				apiResponse = new APIResponse("error", "Volunteer Not Found", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Find Volunteer Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	}
		
	}

