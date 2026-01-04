package com.backend.event.management.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import com.backend.event.management.dao.UserDAO;
import com.backend.event.management.dto.APIResponse;
import com.backend.event.management.dto.UserDTO;
import com.backend.event.management.dto.UserLoginDTO;
import com.backend.event.management.service.UserService;
import com.backend.event.management.util.DBUtil;
import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService;

	private Gson gson;

	public LoginServlet() {

		DBUtil dbUtil = new DBUtil();
		UserDAO userDAO = new UserDAO(dbUtil);

		this.userService = new UserService(userDAO);
		this.gson = new Gson();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Obejct Created : doPost");
		login(request, response);

	}

	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		APIResponse apiResponse = null;
		UserLoginDTO userLoginDTO = gson.fromJson(request.getReader(), UserLoginDTO.class);

		try {

			UserDTO activeuserDTO = userService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
			if (activeuserDTO != null) {
				System.out.println("User Login SuccessFully");
				apiResponse = new APIResponse("success", "User Login SuccessFully", activeuserDTO, 200);
			} else {
				System.out.println("User Login Failed");
				apiResponse = new APIResponse("error", "User Login Failed", 500);
			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "User Login Failed due to :" + e.getMessage(), 500);
		}
		response.setContentType("application/json");
		response.setStatus(apiResponse.getStatusCode());
		PrintWriter printWrite = response.getWriter();
		printWrite.append(gson.toJson(apiResponse));
		printWrite.close();
	}
}
