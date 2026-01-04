package com.backend.event.management.dto;

public class RegistrationsDTO {

    private int id;
    private String fullName;
    private String eventName;
    private String eventDate;
    private int eventsId;
    private int usersId;
    private String status;

    private String createdAt;
    private String updatedAt;
    private int attended; 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getEventsId() {
        return eventsId;
    }
    public void setEventsId(int eventsId) {
        this.eventsId = eventsId;
    }

    public int getUsersId() {
        return usersId;
    }
    public void setUsersId(int usersId) {
        this.usersId = usersId;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public int getAttended() {
		return attended;
	}
	public void setAttended(int attended) {
		this.attended = attended;
	}
    
	
}

