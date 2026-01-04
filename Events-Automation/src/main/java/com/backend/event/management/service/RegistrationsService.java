package com.backend.event.management.service;

import java.util.List;

import com.backend.event.management.dao.RegistrationsDAO;
import com.backend.event.management.dto.RegistrationsDTO;

public class RegistrationsService {

	private RegistrationsDAO registrationsDAO;

	public RegistrationsService(RegistrationsDAO registrationsDAO) {
		this.registrationsDAO = registrationsDAO;

	}

	public int save(RegistrationsDTO registrationsDTO) throws Exception {
		int count = registrationsDAO.save(registrationsDTO);

		if (count > 0) {

		}

		return count;
	}

	public int updateStatus(int id, String status) throws Exception {
		return registrationsDAO.updateStatus(id, status);
	}


	public int deleteById(int id) throws Exception {
		return registrationsDAO.deleteById(id);
	 
	}
	
	public int markAttendance(int usersId, int eventsId, int attended) throws Exception {
	    return registrationsDAO.markAttendance(usersId, eventsId, attended);
	}

	
	
//	public List<RegistrationsDTO> findByStatus(String status) throws Exception {
//		return registrationsDAO.findByStatus(status);
//	 
//	}
	
	public List<RegistrationsDTO> findAllRegistrations() throws Exception {
		return registrationsDAO.findAllRegistrations();
	 
	}
	

}
