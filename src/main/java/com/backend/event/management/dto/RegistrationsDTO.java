package com.backend.event.management.dto;

import java.sql.Timestamp;

public class RegistrationsDTO {

	    private int id;
	    private int eventsId;
	    private int usersId;

	    private String eventName;   // joined table se
	    private String fullName;    // joined table se

	    private Timestamp registrationDate;
	    private String status;

	    // ----- Getters & Setters -----

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

	    public Timestamp getRegistrationDate() {
	        return registrationDate;
	    }
	    public void setRegistrationDate(java.sql.Timestamp timestamp) {
	        this.registrationDate = timestamp;
	    }

	    public String getStatus() {
	        return status;
	    }
	    public void setStatus(String status) {
	        this.status = status;
	    }
	}

    
    

