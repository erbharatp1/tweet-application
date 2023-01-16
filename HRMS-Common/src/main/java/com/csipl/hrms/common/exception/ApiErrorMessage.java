package com.csipl.hrms.common.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiErrorMessage {
	private HttpStatus status;
	   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	   private LocalDateTime timestamp;
	   private String code;
	   private String message;
	   private String debugMessage;
		  
	   private ApiErrorMessage errors;
	 
	   
	   ApiErrorMessage(String message,String code) {
	     //  this();
	       this.code = code;
	       this.message = message;
	     
	   }
	   
	   ApiErrorMessage(String message,HttpStatus code) {
		     //  this();
		       this.status = code;
		       this.message = message;
		     
		   }

	   ApiErrorMessage(HttpStatus status, Throwable ex) {
	    //   this();
	       this.status = status;
	       this.message = "Unexpected error";
	       this.timestamp = timestamp.now();
	       this.debugMessage = ex.getLocalizedMessage();
	   }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public ApiErrorMessage getErrors() {
		return errors;
	}

	public void setErrors(ApiErrorMessage errors) {
		this.errors = errors;
	}

	
	   
	   
	   
	   

	 
}
