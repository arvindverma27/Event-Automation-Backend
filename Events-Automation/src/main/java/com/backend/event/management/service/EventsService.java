package com.backend.event.management.service;


import java.util.List;

import com.backend.event.management.dao.EventsDAO;
import com.backend.event.management.dto.EventsDTO;

public class EventsService {

	private EventsDAO eventsDAO;
	
	public EventsService(EventsDAO eventsDAO) {
		
		this.eventsDAO = eventsDAO;
	}
	
	public int save(EventsDTO eventsDTO) throws Exception {
		
		int count = eventsDAO.save(eventsDTO);
		
		if(count > 0) {
			
		}
		return count;
	}
	
	public int update(EventsDTO eventsDTO) throws Exception {
		return eventsDAO.update(eventsDTO);
	}
	
	public EventsDTO findById(int id) throws Exception {
		return eventsDAO.findById(id);
	}
	
	public int deletetById(int id) throws Exception {
		return eventsDAO.deletetById(id);
	}
	
	public List<EventsDTO> searchEventsBykeyword(String keyword) throws Exception {
		return eventsDAO.searchEventsBykeyword(keyword);
	}

	public List<EventsDTO> findAllEvents() throws Exception {
		return eventsDAO.findAllEvents();
	}
	
	
	
}
