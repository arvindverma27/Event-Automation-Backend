package com.backend.event.management.dto;

import java.util.Date;

public class NotificationsDTO {

    private int id;
    private Integer usersId; 
    private String title;
    private String message;
    private String type;
    private boolean isRead;
    private String targetUrl;
    private Date createdAt;

    public NotificationsDTO() {}

    public NotificationsDTO(int id, Integer usersId, String title, String message, 
                            String type, boolean isRead, String targetUrl, Date createdAt) {
        this.id = id;
        this.usersId = usersId;
        this.title = title;
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.targetUrl = targetUrl;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getUsersId() { return usersId; }
    public void setUsersId(Integer usersId) { this.usersId = usersId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public String getTargetUrl() { return targetUrl; }
    public void setTargetUrl(String targetUrl) { this.targetUrl = targetUrl; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
