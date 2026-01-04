package com.backend.event.management.dto;

public class VolunteersDTO {

	private int id;
	private int usersId;
	private int eventsId;
	private String eventName;
	private String fullName;
	private String roleInEvent;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUsersId() {
		return usersId;
	}
	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}
	public int getEventsId() {
		return eventsId;
	}
	public void setEventsId(int eventsId) {
		this.eventsId = eventsId;
	}
	public String getRoleInEvent() {
		return roleInEvent;
	}
	public void setRoleInEvent(String roleInEvent) {
		this.roleInEvent = roleInEvent;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
}
