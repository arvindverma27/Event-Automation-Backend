//package com.backend.event.management.servlet;
//
//import java.io.IOException;
//import java.sql.Date;
//import java.sql.Timestamp;
//import java.util.List;
//
//import com.backend.event.management.dao.RegistrationsDAO;
//import com.backend.event.management.dto.RegistrationsDTO;
//import com.backend.event.management.service.RegistrationsService;
//import com.backend.event.management.util.DBUtil;
//import com.google.gson.Gson;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/RegistrationsServlet")
//public class RegistrationsServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	private RegistrationsService registrationsService;
//
//	public RegistrationsServlet() {
//
//		DBUtil dbUtil = new DBUtil();
//		RegistrationsDAO registrationsDAO = new RegistrationsDAO(dbUtil);
//		this.registrationsService = new RegistrationsService(registrationsDAO);
//
//	}
//
//	public void init(ServletConfig config) throws ServletException {
//		System.out.println("RegistrationsServlet : init Method ");
//	}
//
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		System.out.println("RegistrationsServlet : doGet Method");
//		String task = request.getParameter("task");
//		System.out.println("task :" + task);
//
//		if (task.equalsIgnoreCase("findById")) {
//			try {
//				findById(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//		}else if(task.equalsIgnoreCase("findByStatus")) {
//			try {
//				findByStatus(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}else if(task.equalsIgnoreCase("findAllRegistartions")) {
//			try {
//				findAllRegistartions(request, response);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}else {
//			System.out.println("No Task Match");
//		}
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//			System.out.println("RegistrationsServlet : doPost Method");
//			String task = request.getParameter("task");
//			System.out.println("task :" +task);
//			if(task.equalsIgnoreCase("save")) {
//				try {
//					save(request, response);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}else if(task.equalsIgnoreCase("update")) {
//				try {
//					update(request, response);
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}else {
//				System.out.println("No Task Match");
//			}
//		
//	}
//
//	private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//		try {
//			RegistrationsDTO registrationsDTO = new RegistrationsDTO();
//			registrationsDTO.setEventName(request.getParameter("event_name"));
//			registrationsDTO.setFullName(request.getParameter("full_name"));
//			registrationsDTO.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
//			registrationsDTO.setStatus(request.getParameter("status"));
//			
//			int count = registrationsService.save(registrationsDTO);
//			if(count >0) {
//				request.setAttribute("status", "success");
//				request.setAttribute("message", "Registration  successfully");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			} else {
//				request.setAttribute("status", "unable to Created Registration");
//				request.setAttribute("message", "error");
//				request.setAttribute("redirectUrl", "add-registration.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("message", "unable to create Registration due to :" + e.getMessage());
//			request.setAttribute("status", "failed");
//			request.setAttribute("redirectUrl", "add-registration.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//		}
//
//
//	}
//
//	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//			RegistrationsDTO registrationsDTO = new RegistrationsDTO();
//			
//			registrationsDTO.setId(Integer.parseInt(request.getParameter("id")));
//			registrationsDTO.setStatus(request.getParameter("status"));
//		
//
//			boolean count = registrationsService.update(registrationsDTO);
//			if (count) {
//				request.setAttribute("message", "Registration update successfully");
//				request.setAttribute("status", "success");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			} else {
//				request.setAttribute("message", "Registration not update. ");
//				request.setAttribute("status", "error");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("message", "unable to update Registration due to :" + e.getMessage());
//			request.setAttribute("status", "failed");
//			request.setAttribute("redirectUrl", "home.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//		}
//		
//	}
//
//	private void findById(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//			int id = Integer.parseInt(request.getParameter("Id"));
//			RegistrationsDTO registrationsDTO = registrationsService.findById(id);
//			if (registrationsDTO != null) {
//				request.setAttribute("status", "success");
//				request.setAttribute("message", "Registration Found successfully");
//				request.setAttribute("editRegistartionrDTO", registrationsDTO);
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				rd.forward(request, response);
//			} else {
//				request.setAttribute("message", "Registration not found. ");
//				request.setAttribute("status", "error");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("message", "Unable to find Registration due to :" + e.getMessage());
//			request.setAttribute("status", "Failed");
//			request.setAttribute("redirectUrl", "home.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//		}
//	}
//
//	private void deleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//
//			int id = Integer.parseInt(request.getParameter("UserId"));
//
//			int count = registrationsService.deleteById(id);
//			if (count > 0) {
//				System.out.println("Registartion delete successfully");
//				request.setAttribute("message", "Registartion delete successfully");
//				request.setAttribute("status", "success");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			} else {
//
//				System.out.println("User not found.");
//				request.setAttribute("message", "Registartion not found. ");
//				request.setAttribute("status", "Error");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//
//			request.setAttribute("message", "Unable to delete Registartion due to :" + e.getMessage());
//			request.setAttribute("status", "Failed");
//			request.setAttribute("redirectUrl", "home.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//
//		}
//
//	}
//
//
//	private void searchRegistrationByDate(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//	    try {
//	        String exact = request.getParameter("exact_date");
//	        String start = request.getParameter("start_date");
//	        String end = request.getParameter("end_date");
//	        String last = request.getParameter("last_month");
//
//	        Date exactDate = null;
//	        Date startDate = null;
//	        Date endDate = null;
//	        boolean lastMonth = false;
//
//	        if (exact != null && !exact.trim().isEmpty()) {
//	            exactDate = Date.valueOf(exact);
//	        }
//
//	        if (start != null && !start.trim().isEmpty()) {
//	            startDate = Date.valueOf(start);
//	        }
//
//	        if (end != null && !end.trim().isEmpty()) {
//	            endDate = Date.valueOf(end);
//	        }
//
//	        if ("true".equals(last)) {
//	            lastMonth = true;
//	        }
//
//	        List<RegistrationsDTO> registrationsDTOList =
//	                registrationsService.searchRegistrationByDate(exactDate, startDate, endDate, lastMonth);
//
//	        String isAjax = request.getHeader("X-Requested-With");
//	        if ("XMLHttpRequest".equals(isAjax)) {
//	            response.setContentType("application/json");
//	            response.setCharacterEncoding("UTF-8");
//	            Gson gson = new Gson();
//	            response.getWriter().write(gson.toJson(registrationsDTOList));
//	            return;
//	        }
//
//	        if (!registrationsDTOList.isEmpty()) {
//	            request.setAttribute("status", "success");
//	            request.setAttribute("registrationsDTOList", registrationsDTOList);
//	            RequestDispatcher rd = request.getRequestDispatcher("registration-search-result.jsp");
//	            rd.forward(request, response);
//	        } else {
//	            request.setAttribute("message", "No Registration found for given date");
//	            request.setAttribute("status", "Failed");
//	            RequestDispatcher rd = request.getRequestDispatcher("registration-search-result.jsp");
//	            rd.forward(request, response);
//	        }
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        request.setAttribute("message", "Unable to fetch Registration: " + e.getMessage());
//	        request.setAttribute("status", "error");
//	        RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//	        rd.forward(request, response);
//	    }
//	}
//
//
//	private void findByStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//			String status = request.getParameter("status");
//             List<RegistrationsDTO> registrationsDTOList = registrationsService.findByStatus(status) ;
//
//			
//			String isAjax = request.getHeader("X-Requested-With");
//			if ("XMLHttpRequest".equals(isAjax)) {
//				response.setContentType("application/json");
//				response.setCharacterEncoding("UTF-8");
//				Gson gson = new Gson();
//				response.getWriter().write(gson.toJson(registrationsDTOList));
//				return;
//			}
//
//			
//			if (!registrationsDTOList.isEmpty()) {
//				request.setAttribute("status", "success");
//				request.setAttribute("registrationsDTOList", registrationsDTOList);
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				rd.forward(request, response);
//			} else {
//				request.setAttribute("message", "No Registartion found");
//				request.setAttribute("status", "Failed");
//				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//				rd.forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("message", "unable to found  Registartion due to :" + e.getMessage());
//			request.setAttribute("status", "error");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//		}
//	}
//
//	private void findAllRegistartions(HttpServletRequest request, HttpServletResponse response) throws Exception {
//
//		try {
//			List<RegistrationsDTO>  registrationsDTOList = registrationsService.findAllRegistartions();
//			if (!registrationsDTOList.isEmpty()) {
//				request.setAttribute("registrationsDTOList", registrationsDTOList);
//				RequestDispatcher rd = request.getRequestDispatcher("registration-list.jsp");
//				rd.forward(request, response);
//			} else {
//				request.setAttribute("message", "Failed to Find Registartion ");
//				request.setAttribute("status", "error");
//				request.setAttribute("redirectUrl", "home.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("message", "Failed to find Registartion due to :" + e.getMessage());
//			request.setAttribute("status", "Failed");
//			request.setAttribute("redirectUrl", "home.jsp");
//			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//			rd.forward(request, response);
//		}
//	}
//	
//
//	private void searchRegistartionForkeyword(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		
//
//			try {
//				String keyword = request.getParameter("keyword");
//				List<RegistrationsDTO> registrationsDTOList  = registrationsService.searchRegistartionForkeyword(keyword) ;
//
//				
//				String isAjax = request.getHeader("X-Requested-With");
//				if ("XMLHttpRequest".equals(isAjax)) {
//					response.setContentType("application/json");
//					response.setCharacterEncoding("UTF-8");
//					Gson gson = new Gson();
//					response.getWriter().write(gson.toJson(registrationsDTOList));
//					return;
//				}
//
//				
//				if (!registrationsDTOList.isEmpty()) {
//					request.setAttribute("status", "success");
//					request.setAttribute("registrationsDTOList", registrationsDTOList);
//					RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//					rd.forward(request, response);
//				} else {
//					request.setAttribute("message", "No Registration found");
//					request.setAttribute("status", "Failed");
//					RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
//					rd.forward(request, response);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				request.setAttribute("message", "unable to found  Registration due to :" + e.getMessage());
//				request.setAttribute("status", "error");
//				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
//				rd.forward(request, response);
//			}
//		}
//		
//
//	}
//
//
