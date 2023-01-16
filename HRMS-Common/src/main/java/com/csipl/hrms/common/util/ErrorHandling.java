package com.csipl.hrms.common.util;

import com.csipl.hrms.common.exception.BaseException;

public class ErrorHandling extends BaseException {
	
	
	private String message;
	private String errorMessage;
	private String code;
	
	public ErrorHandling() {}
	
	

	public ErrorHandling( String message) {
		this.message = message;
		
 	}
	
	public ErrorHandling( String message,String code ) {
		this.message = message;
		this.code=code;
 	}
	

	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public final String getMessage() {
		return message;
	}


	public final void setMessage(String message) {
		this.message = message;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
	
}




