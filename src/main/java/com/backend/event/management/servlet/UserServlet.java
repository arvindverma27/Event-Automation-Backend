package com.backend.event.management.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import com.backend.event.management.dao.UserDAO;
import com.backend.event.management.dto.UserDTO;
import com.backend.event.management.service.UserService;
import com.backend.event.management.util.DBUtil;
import com.google.gson.Gson;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService;

	public UserServlet() {
		DBUtil dbUtil = new DBUtil();

		UserDAO userDAO = new UserDAO(dbUtil);

		this.userService = new UserService(userDAO);
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("UserServlet : init Method ");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("UserServlet: doGet method..");
		String task = request.getParameter("task");
		System.out.println("Task: " + task);
		if (task.equalsIgnoreCase("findById")) {
			try {
				findById(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("findAllRole")) {
			try {
				findAllByRole(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
		
		} else if (task.equalsIgnoreCase("searchUsers")) {
			try {
				searchUsers(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}

		} else if (task.equalsIgnoreCase("deleteById")) {
			try {
				deleteById(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else {

			System.out.println("No task Match");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserServlet: doPost method..");
		String task = request.getParameter("task");
		System.out.println("Task: " + task);

		try {
			if (task.equalsIgnoreCase("signup")) {
				save(request, response);
			} else if (task.equalsIgnoreCase("update")) {
				update(request, response);
			} else if (task.equalsIgnoreCase("verifyToken")) {
				String token = request.getParameter("token");
				UserDTO userDTO = userService.findByVerificationToken(token);
				response.getWriter().println("User found: " + userDTO.getEmail());
			} else if (task.equalsIgnoreCase("clearToken")) {
				int userId = Integer.parseInt(request.getParameter("userId"));
				userService.clearVerificationToken(userId);
				response.getWriter().println("Token cleared for userId: " + userId);
			} else if (task.equalsIgnoreCase("saveResetToken")) {
				String email = request.getParameter("email");
				String resetToken = request.getParameter("token");
				Timestamp expiry = Timestamp.valueOf(request.getParameter("expiry"));
				userService.saveResetToken(email, resetToken, expiry);
				response.getWriter().println("Reset token saved for email: " + email);
			} else if (task.equalsIgnoreCase("resetPassword")) {
				int userId = Integer.parseInt(request.getParameter("userId"));
				String newPassword = request.getParameter("newPassword");
				userService.resetPassword(userId, newPassword);
				response.getWriter().println("Password reset for userId: " + userId);
			} else {
				System.out.println("No Task Match");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().println("Error: " + e.getMessage());
		}
	}

	public void destroy() {
		System.out.println("Servlet destroyed");
	}

	private void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			UserDTO userDTO = new UserDTO();
			userDTO.setFullName(request.getParameter("fullName"));
			userDTO.setRole(request.getParameter("role"));
			userDTO.setMobileNumber(request.getParameter("mobileNumber"));
			userDTO.setEmail(request.getParameter("email"));
			userDTO.setPassword(request.getParameter("password"));

			int count = userService.save(userDTO);
			if (count > 0) {
				System.out.println("User created successfully");
				request.setAttribute("message", "user Created successfully");
				request.setAttribute("status", "success");
				request.setAttribute("redirectUrl", "login.jsp");

				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			} else {
				System.out.println("Failed to create user.");
				request.setAttribute("message", "unable to user Created ");
				request.setAttribute("message", "error");
				request.setAttribute("redirectUrl", "signup.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("message", "unable to user due to :" + e.getMessage());
			request.setAttribute("status", "failed");
			request.setAttribute("redirectUrl", "signup.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);

		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			UserDTO userDTO = new UserDTO();

			userDTO.setId(Integer.parseInt(request.getParameter("Id")));
			userDTO.setFullName(request.getParameter("fullName"));
			userDTO.setRole(request.getParameter("role"));
			userDTO.setMobileNumber(request.getParameter("mobileNumber"));
			userDTO.setEmail(request.getParameter("email"));
			userDTO.setPassword(request.getParameter("password"));

			int count = userService.update(userDTO);
			if (count > 0) {
				System.out.println("User update successfully");
				request.setAttribute("message", "user update successfully");
				request.setAttribute("status", "success");
				request.setAttribute("redirectUrl", "home.jsp");

				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("user not found.");
				request.setAttribute("message", "User not found. ");
				request.setAttribute("status", "error");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("message", "unable to update user due to :" + e.getMessage());
			request.setAttribute("status", "failed");
			request.setAttribute("redirectUrl", "home.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);

		}
	}

	private void findById(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			int id = Integer.parseInt(request.getParameter("id"));

			UserDTO userDTO = userService.findById(id);
			if (userDTO != null) {
				System.out.println("User Found successfully");
				System.out.println("fullName " + userDTO.getFullName());
				request.setAttribute("editUserDTO", userDTO);

				RequestDispatcher rd = request.getRequestDispatcher("user-edit.jsp");
				rd.forward(request, response);
			} else {

				System.out.println("User not found.");
				request.setAttribute("message", "User not found. ");
				request.setAttribute("status", "error");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("message", "Unable to find user due to :" + e.getMessage());
			request.setAttribute("status", "Failed");
			request.setAttribute("redirectUrl", "home.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);

		}
	}

	private void deleteById(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			int id = Integer.parseInt(request.getParameter("UserId"));

			int count = userService.deleteById(id);
			if (count > 0) {
				System.out.println("User delete successfully");
				request.setAttribute("message", "User delete successfully");
				request.setAttribute("status", "success");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);
			} else {

				System.out.println("User not found.");
				request.setAttribute("message", "User not found. ");
				request.setAttribute("status", "Error");
				request.setAttribute("redirectUrl", "home.jsp");
				RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
				rd.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("message", "Unable to delete user due to :" + e.getMessage());
			request.setAttribute("status", "Failed");
			request.setAttribute("redirectUrl", "home.jsp");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);

		}
	}

	
	private void findAllByRole(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String role = request.getParameter("role");
			List<UserDTO> userDTOist = userService.findAllByRole(role);

			
			String isAjax = request.getHeader("X-Requested-With");
			if ("XMLHttpRequest".equals(isAjax)) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				Gson gson = new Gson();
				response.getWriter().write(gson.toJson(userDTOist));
				return;
			}

			
			if (!userDTOist.isEmpty()) {
				request.setAttribute("status", "success");
				request.setAttribute("userDTOContactList", userDTOist);
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("message", "No User found");
				request.setAttribute("status", "Failed");
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "unable to found  User due to :" + e.getMessage());
			request.setAttribute("status", "error");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);
		}
	}

		
	

	public void searchUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String keyword = request.getParameter("keyword");
			List<UserDTO> userDTOist = userService.searchUsers(keyword);

			
			String isAjax = request.getHeader("X-Requested-With");
			if ("XMLHttpRequest".equals(isAjax)) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				Gson gson = new Gson();
				response.getWriter().write(gson.toJson(userDTOist));
				return;
			}

			
			if (!userDTOist.isEmpty()) {
				request.setAttribute("status", "success");
				request.setAttribute("userDTOist", userDTOist);
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			} else {
				request.setAttribute("message", "No User found");
				request.setAttribute("status", "Failed");
				RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "unable to found  User due to :" + e.getMessage());
			request.setAttribute("status", "error");
			RequestDispatcher rd = request.getRequestDispatcher("message.jsp");
			rd.forward(request, response);
		}
	}

}
