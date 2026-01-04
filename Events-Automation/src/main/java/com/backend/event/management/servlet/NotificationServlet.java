package com.backend.event.management.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import com.backend.event.management.dao.NotificatiosDAO;
import com.backend.event.management.dto.NotificationsDTO;
import com.backend.event.management.service.NotificationsService;
import com.backend.event.management.util.DBUtil;
import com.google.gson.Gson;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/NotificationServlet")
public class NotificationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NotificationsService notificationsService;
	private Gson gson;

	public NotificationServlet() {
		DBUtil dbUtil = new DBUtil();
		NotificatiosDAO notificatiosDAO = new NotificatiosDAO(dbUtil);
		this.notificationsService = new NotificationsService(notificatiosDAO);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		String userIdParam = req.getParameter("userId");

		try {
			if (userIdParam != null) {
				int userId = Integer.parseInt(userIdParam);
				List<NotificationsDTO> notificationsList = notificationsService.getUserNotifications(userId);

				resp.getWriter().write(gson.toJson(notificationsList));
				return;
			}

			// all notifications
			List<NotificationsDTO> notificationsList = notificationsService.getAllNotifications();
			resp.getWriter().write(gson.toJson(notificationsList));

		} catch (Exception e) {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String path = req.getPathInfo(); // e.g. /broadcast

		BufferedReader reader = req.getReader();
		NotificationsDTO notificationsDTO = gson.fromJson(reader, NotificationsDTO.class);

		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		try {
			// /broadcast => broadcast notification
			if ("/broadcast".equals(path)) {
				notificationsService.sendBroadcast(notificationsDTO.getTitle(), notificationsDTO.getMessage(),
						notificationsDTO.getType(), notificationsDTO.getTargetUrl());
				resp.getWriter().write("{\"success\":true, \"broadcast\":true}");
				return;
			}

			// send to single user
			notificationsService.sendToUser(notificationsDTO.getUsersId(), notificationsDTO.getTitle(),
					notificationsDTO.getMessage(), notificationsDTO.getType(), notificationsDTO.getTargetUrl());

			resp.getWriter().write("{\"success\":true}");

		} catch (Exception e) {
			resp.setStatus(500);
			resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String path = req.getPathInfo(); // /mark-all
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		try {
			if ("/mark-all".equals(path)) {
				int userId = Integer.parseInt(req.getParameter("userId"));
				notificationsService.markAllRead(userId);

				resp.getWriter().write("{\"success\":true, \"markAll\":true}");
				return;
			}
			
			int id = Integer.parseInt(req.getParameter("id"));
			notificationsService.markRead(id);
			resp.getWriter().write("{\"success\":true}");

		} catch (Exception e) {
			resp.setStatus(500);
			resp.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
