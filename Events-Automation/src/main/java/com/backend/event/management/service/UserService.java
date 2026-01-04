package com.backend.event.management.service;

import java.sql.Timestamp;
import java.util.List;

import com.backend.event.management.dao.UserDAO;
import com.backend.event.management.dto.UserDTO;


public class UserService {

	private UserDAO userDAO;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public int save(UserDTO userDTO) throws Exception {
	    int userId = userDAO.save(userDTO);
	    
	    if (userId >0) {
	    }
	    return userId;
	}
	public int update(UserDTO userDTO) throws Exception {
		return userDAO.update(userDTO);
	}

	public UserDTO findById(int id) throws Exception {
		return userDAO.findById(id);
	}


	public UserDTO login(String username, String password) throws Exception {
		return userDAO.login(username, password);
	}

	public int deleteById(int id) throws Exception {
		return userDAO.deleteById(id);
	}

	public List<UserDTO> findAllStudent() throws Exception {
		return userDAO.findAllStudent();
	}
	
	public List<UserDTO> findAllVolunteer() throws Exception {
		return userDAO.findAllVolunteer();
	}
	

	
	public List<UserDTO> searchUsers(String keyword) throws Exception {
		return userDAO.searchUsers(keyword);
	}

}