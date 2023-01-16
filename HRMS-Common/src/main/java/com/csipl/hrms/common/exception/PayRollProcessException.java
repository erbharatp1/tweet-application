package com.csipl.hrms.common.exception;

public class PayRollProcessException extends BaseException{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	 
	 public PayRollProcessException( String message ) {
		 this.message = message;
	 }

	public String getMessage() {
		return message;
	}
	 
}
