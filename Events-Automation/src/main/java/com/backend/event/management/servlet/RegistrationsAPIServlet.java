package com.backend.event.management.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import com.backend.event.management.dao.RegistrationsDAO;
import com.backend.event.management.dto.APIResponse;
import com.backend.event.management.dto.FeedbackDTO;
import com.backend.event.management.dto.RegistrationsDTO;
import com.backend.event.management.service.RegistrationsService;
import com.backend.event.management.util.DBUtil;
import com.google.gson.Gson;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegistrationsAPIServlet")
public class RegistrationsAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RegistrationsService registrationsService;
	private Gson gson;

	public RegistrationsAPIServlet() {

		DBUtil dbUtil = new DBUtil();
		RegistrationsDAO registrationsDAO = new RegistrationsDAO(dbUtil);
		this.registrationsService = new RegistrationsService(registrationsDAO);
		this.gson = new Gson();

	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("RegistrationsAPIServlet : init Method ");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("RegistrationsAPIServlet : doGet Method");
		String task = request.getParameter("task");
		System.out.println("task :" + task);

		if (task.equalsIgnoreCase("findAllRegistrations")) {
			try {
				findAllRegistrations(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

//		 else if (task.equalsIgnoreCase("findByStatus")) {
//			try {
//				findByStatus(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		 }
		else {
			System.out.println("No Task Match");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("RegistrationsAPIServlet : doPost Method");
		String task = request.getParameter("task");

		if (task.equalsIgnoreCase("save")) {
			try {
				save(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("RegistrationsAPIServlet : doDelete Method");
		try {
			deleteById(request, response);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("RegistrationsAPIServlet : doPut Method");
		String task = request.getParameter("task");
		System.out.println("task :" + task);
		if(task.equalsIgnoreCase("updateStatus")) {
			try {
				updateStatus(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(task.equalsIgnoreCase("markAttendance")) {
			try {
				markAttendance(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("No Task Match");
		}
	
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;
		try {

			RegistrationsDTO registrationsDTO = gson.fromJson(request.getReader(), RegistrationsDTO.class);
			int count = registrationsService.save(registrationsDTO);
			if (count > 0) {
				System.out.println("Registration  Successfully");
				apiResponse = new APIResponse("success", "Registration Successfully", 200);
			} else {
				System.out.println("faild to Create Event");
				apiResponse = new APIResponse("error", "faild to Create Registration ", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to  Registration Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();

	}

	private void updateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse;

		try {
			String idParam = request.getParameter("id");
			String status = request.getParameter("status");

			if (idParam == null || status == null || idParam.isEmpty() || status.isEmpty()) {
				apiResponse = new APIResponse("error", "id and status are required", 400);
			} else {
				int id = Integer.parseInt(idParam);

				int count = registrationsService.updateStatus(id, status);

				if (count > 0) {
					apiResponse = new APIResponse("success", "Status Updated Successfully", 200);
				} else {
					apiResponse = new APIResponse("error", "Failed to update status", 500);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "Failed due to: " + e.getMessage(), 500);
		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter writer = response.getWriter();
		writer.append(gson.toJson(apiResponse));
		writer.close();
	}
	
	private void markAttendance(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;
		
		 int usersId = Integer.parseInt(request.getParameter("usersId"));
		    int eventsId = Integer.parseInt(request.getParameter("eventsId"));
		    
		    try {
				int count = registrationsService.markAttendance(usersId, eventsId, 1);
				if (count > 0) {
					System.out.println("Attendance marked");
					apiResponse = new APIResponse(true, "Attendance marked", 200);
				} else {
					System.out.println("Failed to update attendanc");
					apiResponse = new APIResponse(false, "Failed to update attendanc", 500);

				}

			} catch (Exception e) {
				e.printStackTrace();
				apiResponse = new APIResponse("error", "faild to Delete Registration Due to :" + e.getMessage(), 500);

			}

			response.setContentType("application/json");
			response.setStatus(apiResponse.getStatusCode());
			PrintWriter printWrite = response.getWriter();
			printWrite.append(gson.toJson(apiResponse));
			printWrite.close();

	}

	private void deleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;

		int registrationId = Integer.parseInt(request.getParameter("registrationId"));

		try {
			int count = registrationsService.deleteById(registrationId);
			if (count > 0) {
				System.out.println("Registration Delete Successfully");
				apiResponse = new APIResponse("success", "Registration Delete Successfully", 200);
			} else {
				System.out.println("Registration Not Delete");
				apiResponse = new APIResponse("error", "Registration Not Delete", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Delete Registration Due to :" + e.getMessage(), 500);

		}

		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();

	}

//	private void findByStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		APIResponse apiResponse = null;
//
//		String status = request.getParameter("status");
//
//		try {
//			List<RegistrationsDTO> registrationsDTOList = registrationsService.findByStatus(status);
//			if (!registrationsDTOList.isEmpty()) {
//				System.out.println("Registration Found Successfully");
//				apiResponse = new APIResponse("success", "Registration Found Successfully", registrationsDTOList, 200);
//			} else {
//				System.out.println("Registration Not Found");
//				apiResponse = new APIResponse("error", "Registration Not Found", 500);
//
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			apiResponse = new APIResponse("error", "faild to Find Registration Due to :" + e.getMessage(), 500);
//
//		}
//
//		response.setContentType("application/json");
//		response.setStatus(apiResponse.getStatusCode());
//		PrintWriter printWrite = response.getWriter();
//		printWrite.append(gson.toJson(apiResponse));
//		printWrite.close();
//
//	}

	private void findAllRegistrations(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;

		try {
			List<RegistrationsDTO> registrationsDTOList = registrationsService.findAllRegistrations();
			if (!registrationsDTOList.isEmpty()) {
				System.out.println("Registration Found Successfully");
				apiResponse = new APIResponse("success", "Registration Found Successfully", registrationsDTOList, 200);
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

}
