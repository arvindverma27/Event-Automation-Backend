package com.backend.event.management.servlet;

import java.io.IOException;

import com.backend.event.management.dao.UserDAO;
import com.backend.event.management.service.UserService;
import com.backend.event.management.util.DBUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	private UserService userService;
	
   
    public LoginServlet() {
          
    	DBUtil dbUtil = new DBUtil();
    	UserDAO userDAO = new UserDAO(dbUtil);
    	
    	this.userService = new UserService(userDAO);
    }

	


	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");

	    try {
	        boolean isAuthenticated = userService.authenticate(username, password);

	        if(isAuthenticated) {
	            HttpSession session = request.getSession(); 
	            session.setAttribute("username", username);
	            session.setMaxInactiveInterval(30*60); 

	            response.getWriter().println("Login successful for: " + username);
	        } else {
	            response.getWriter().println("Invalid username or password");
	        }
	    } catch(Exception e) {
	        e.printStackTrace();
	        response.getWriter().println("Error: " + e.getMessage());
	    }
	}


}
