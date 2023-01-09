package com.ivan.demo.exceptions;

import java.time.LocalDateTime;

public class ErrorDetails {
	
	private LocalDateTime time;
	private String message;
	private String description;
	
	public ErrorDetails(LocalDateTime time, String message, String description) {
		this.time = time;
		this.description = description;
		this.message = message;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}
	
}
