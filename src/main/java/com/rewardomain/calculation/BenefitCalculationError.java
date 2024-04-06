package com.rewardomain.calculation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BenefitCalculationError {
	@JsonProperty("status_code") 
	private Long statusCode;
	@JsonProperty("error_message")
	private String message ;
	
	
	public BenefitCalculationError(Long statusCode, String message) {
		super();
		this.statusCode = statusCode;
		this.message = message;
	}
	public Long getStatusCode() {
		return statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setStatusCode(Long statusCode) {
		this.statusCode = statusCode;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
