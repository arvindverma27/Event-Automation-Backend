package com.backend.event.management.service;

import java.sql.Date;
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

		if (count < 0) {

		}

		return count;
	}

	public boolean update(RegistrationsDTO registrationsDTO) throws Exception {
		return registrationsDAO.update(registrationsDTO);
	 
	}
	
	public RegistrationsDTO findById(int id) throws Exception {
		return registrationsDAO.findById(id);
	 
	}
	
	public int deleteById(int id) throws Exception {
		return registrationsDAO.deleteById(id);
	 
	}
	
	public List<RegistrationsDTO> findByEventId(int id) throws Exception {
		return registrationsDAO.findByEventId(id);
	 
	}
	
	public List<RegistrationsDTO> findByUserId(int id) throws Exception {
		return registrationsDAO.findByUserId(id);
	 
	}
	
	public List<RegistrationsDTO> searchRegistrationByDate(Date exactDate, Date startDate, Date endDate, boolean lastMonth) throws Exception {
		return registrationsDAO.searchRegistrationByDate(exactDate, startDate, endDate, lastMonth);
	 
	}
	
	
	
	public List<RegistrationsDTO> findByStatus(String status) throws Exception {
		return registrationsDAO.findByStatus(status);
	 
	}
	
	public List<RegistrationsDTO> findAllRegistartions() throws Exception {
		return registrationsDAO.findAllRegistartions();
	 
	}
	
	public List<RegistrationsDTO> searchRegistartionForkeyword(String keyword) throws Exception {
		return registrationsDAO.searchRegistartionForkeyword(keyword);
	 
	}
}
