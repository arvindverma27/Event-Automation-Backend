package com.backend.event.management.dto;

import java.sql.Timestamp;

public class UserDTO {

    private int id;
    private String fullName;
    private String role;
    private String email;
    private String mobileNumber;
    private String password;

    private String verificationToken;
    private Timestamp tokenExpiry;

    private String resetToken;
    private Timestamp resetExpiry;

    private Timestamp lastLogin;
    private Timestamp updatedAt;

    // Getters & Setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public String getVerificationToken() {
        return verificationToken;
    }
    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public Timestamp getTokenExpiry() {
        return tokenExpiry;
    }
    public void setTokenExpiry(Timestamp tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public String getResetToken() {
        return resetToken;
    }
    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Timestamp getResetExpiry() {
        return resetExpiry;
    }
    public void setResetExpiry(Timestamp resetExpiry) {
        this.resetExpiry = resetExpiry;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }
    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
	
}
