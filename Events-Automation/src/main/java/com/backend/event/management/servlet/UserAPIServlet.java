package com.backend.event.management.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.backend.event.management.dao.UserDAO;
import com.backend.event.management.dto.APIResponse;
import com.backend.event.management.dto.UserDTO;
import com.backend.event.management.service.UserService;
import com.backend.event.management.util.DBUtil;
//import com.backend.event.management.util.JwtUtil;
import com.google.gson.Gson;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UserAPIServlet")
public class UserAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService;
	private Gson gson;

	public UserAPIServlet() {
		DBUtil dbUtil = new DBUtil();
		UserDAO userDAO = new UserDAO(dbUtil);
		this.userService = new UserService(userDAO);
		this.gson = new Gson();

	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("UserAPIServlet : init Method ");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("UserAPIServlet: doGet method..");
		String task = request.getParameter("task");
		System.out.println("Task: " + task);
		if (task.equalsIgnoreCase("findById")) {
			try {
				findById(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}
		} else if (task.equalsIgnoreCase("findAllStudent")) {
			try {
				findAllStudent(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}

		}
		 else if (task.equalsIgnoreCase("findAllVolunteer")) {
				try {
					findAllVolunteer(request, response);
				} catch (Exception e) {

					e.printStackTrace();
				}

			}else if (task.equalsIgnoreCase("searchUsers")) {
			try {
				searchUsers(request, response);
			} catch (Exception e) {

				e.printStackTrace();
			}

		} else {

			System.out.println("No task Match");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserAPIServlet: doGet method..");
		String task = request.getParameter("task");
		System.out.println("task :" + task);
		if(task.equalsIgnoreCase("signup")) {
			try {
				save(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserAPIServlet: doGet method..");
		try {
			update(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("UserAPIServlet: doDelete method..");
		try {
			deleteById(request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void doDestroy() throws ServletException, IOException {
		System.out.println("UserAPIServlet: doDestroy method..");

	}


	private void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		APIResponse apiResponse = null;
		try {

			UserDTO userDTO = gson.fromJson(request.getReader(), UserDTO.class);
			int count = userService.save(userDTO);
			if (count > 0) {
				System.out.println("User Account Created Successfully");
				apiResponse = new APIResponse("success", "User Account Created Successfully", 200);
			} else {
				System.out.println("faild to Create User Account");
				apiResponse = new APIResponse("error", "faild to Create User Account", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Create User Account Due to :" + e.getMessage(), 500);

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

			UserDTO userDTO = gson.fromJson(request.getReader(), UserDTO.class);
			int count = userService.update(userDTO);
			if (count > 0) {
				System.out.println("User Account Updated Successfully");
				apiResponse = new APIResponse("success", "User Account Updated Successfully", 200);
			} else {
				System.out.println("faild to Updated User Account");
				apiResponse = new APIResponse("error", "faild to Updated User Account", 500);

			}

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new APIResponse("error", "faild to Updated User Account Due to :" + e.getMessage(), 500);

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

		int userId = Integer.parseInt(request.getParameter("userId"));

		try {
			UserDTO userDTO = userService.findById(userId);
			if (userDTO != null) {
				System.out.println("User Found Successfully");
				apiResponse = new APIResponse("success", "User Found Successfully", userDTO, 200);
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
private void deleteById(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
	APIResponse apiResponse = null;

	int id = Integer.parseInt(request.getParameter("id"));

	try {
		int count = userService.deleteById(id);
		if (count > 0) {
			System.out.println("User Delete Successfully");
			apiResponse = new APIResponse("success", "User Delete Successfully", 200);
		} else {
			System.out.println("User Not Found");
			apiResponse = new APIResponse("error", "User Not Delete", 500);

		}

	} catch (Exception e) {
		e.printStackTrace();
		apiResponse = new APIResponse("error", "faild to Delete User Due to :" + e.getMessage(), 500);

	}

	response.setContentType("application/json");
	response.setStatus(apiResponse.getStatusCode());
	PrintWriter printWrite = response.getWriter();
	printWrite.append(gson.toJson(apiResponse));
	printWrite.close();

}


	private void findAllStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {

    APIResponse apiResponse = null;

    try {
        List<UserDTO> studentDTOList = userService.findAllStudent();

        if (studentDTOList != null && !studentDTOList.isEmpty()) {
            System.out.println("User Found Successfully");
            apiResponse = new APIResponse("success", "User Found Successfully", studentDTOList, 200);
        } else {
            System.out.println("User Not Found");
            apiResponse = new APIResponse("error", "User Not Found", 404);
        }

    } catch (Exception e) {
        e.printStackTrace();
        apiResponse = new APIResponse("error", "Failed to Find User Due to: " + e.getMessage(), 500);
    }

    response.setContentType("application/json");
    response.setStatus(apiResponse.getStatusCode());

    PrintWriter printWrite = response.getWriter();
    printWrite.append(gson.toJson(apiResponse));
    printWrite.close();
}


	private void findAllVolunteer(HttpServletRequest request, HttpServletResponse response) throws Exception {

		APIResponse apiResponse = null;

		
		try {
			List<UserDTO> volunteerDTOList = userService.findAllVolunteer();
			if (!volunteerDTOList.isEmpty()) {
				System.out.println("User Found Successfully");
				apiResponse = new APIResponse("success", "User Found Successfully", volunteerDTOList, 200);
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
	
	public void searchUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		APIResponse apiResponse = null;

		String keyword = request.getParameter("keyword");
		
		try {
			List<UserDTO> userDTOList = userService.searchUsers(keyword);
			if (userDTOList != null && !userDTOList.isEmpty()) {
				System.out.println("User Found Successfully");
				apiResponse = new APIResponse("success", "User Found Successfully", userDTOList, 200);
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

}
