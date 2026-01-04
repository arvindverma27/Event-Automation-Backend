package com.backend.event.management.dto;

public class APIResponse {

	private String status;
	private String message;
	private Object data;
	private int statusCode;
	private String token;
	private boolean statuss;
	
	
	public APIResponse( String status, String message, Object data, int statusCode) {
		this.status = status;
		this.message = message;
		this.data = data;
		this.statusCode = statusCode;
	}
	

	public APIResponse( String status, String message, int statusCode) {
		this.status = status;
		this.message = message;
		this.statusCode = statusCode;
	}
	
	  public APIResponse(String status, String message, String token, int statusCode) {
	        this.status = status;
	        this.message = message;
	        this.statusCode = statusCode;
	        this.token = token;
	    }

		public APIResponse( boolean statuss, String message, Object data, int statusCode) {
			this.statuss = statuss;
			this.message = message;
			this.statusCode = statusCode;
		}
	  
		public APIResponse( boolean statuss, String message, int statusCode) {
			this.statuss = statuss;
			this.message = message;
			this.statusCode = statusCode;
		}
	  

	public String getToken() {
		return token;
	}


	  public void setToken(String token) {
		  this.token = token;
	  }


	public String getStatus() {
		return status;
	}

	public boolean getStatuss() {
		return statuss;
	}


	public String getMessage() {
		return message;
	}


	public Object getData() {
		return data;
	}


	public int getStatusCode() {
		return statusCode;
	}
	
}
