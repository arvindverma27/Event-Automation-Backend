package com.backend.event.management.service;

import java.util.List;

import com.backend.event.management.dao.VolunteersDAO;
import com.backend.event.management.dto.VolunteersDTO;

public class VolunteersService {

	
	private VolunteersDAO volunteersDAO;
	
	public VolunteersService(VolunteersDAO volunteersDAO) {
		this.volunteersDAO = volunteersDAO;
	}
	
	public int save(VolunteersDTO volunteersDTO) throws Exception {
		int count = volunteersDAO.save(volunteersDTO);
		
		if(count > 0) {
		
		}
		return  count;
	}
	public int update(VolunteersDTO volunteersDTO) throws Exception {
		return volunteersDAO.update(volunteersDTO);
	}
	public int deleteById(int id) throws Exception {
		return volunteersDAO.deleteById(id);
	}
	
	public VolunteersDTO findById(int id) throws Exception {
		return volunteersDAO.findById(id);
	}
	
	public List<VolunteersDTO> findAllVolunteers() throws Exception {
		return volunteersDAO.findAllVolunteers();
	}
	public List<VolunteersDTO> searchEventsBykeyword(String keyword) throws Exception {
		return volunteersDAO.searchVolunteersBykeyword(keyword);
	}
}
