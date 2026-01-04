package com.backend.event.management.service;

import java.util.List;

import com.backend.event.management.dao.NotificatiosDAO;
import com.backend.event.management.dto.NotificationsDTO;

public class NotificationsService {

	private NotificatiosDAO notificatiosDAO;

	public NotificationsService(NotificatiosDAO notificatiosDAO) {
		this.notificatiosDAO = notificatiosDAO;
	}

	
	public void sendToUser(int userId, String title, String msg, String type, String url) throws Exception {
		NotificationsDTO notificationsDTO = new NotificationsDTO();
		notificationsDTO.setUsersId(userId);
		notificationsDTO.setTitle(title);
		notificationsDTO.setMessage(msg);
		notificationsDTO.setType(type);
		notificationsDTO.setTargetUrl(url);
		notificationsDTO.setRead(false);

		notificatiosDAO.save(notificationsDTO);
	}

	
	public void sendBroadcast(String title, String msg, String type, String url) throws Exception {
		NotificationsDTO n = new NotificationsDTO();
		n.setUsersId(null); // broadcast
		n.setTitle(title);
		n.setMessage(msg);
		n.setType(type);
		n.setTargetUrl(url);
		n.setRead(false);

		notificatiosDAO.save(n);
	}


	public List<NotificationsDTO> getUserNotifications(int userId) throws Exception {
		return notificatiosDAO.NotificationsForUser(userId);
	}

	public void markRead(int id) throws Exception {
		notificatiosDAO.markAsRead(id);
	}

	public void markAllRead(int userId) throws Exception {
		notificatiosDAO.markAsAllRead(userId);
	}

	
	public List<NotificationsDTO> getAllNotifications() throws Exception {
		return notificatiosDAO.findAllNotifications();
	}
}
