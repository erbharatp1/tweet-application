/*package com.csipl.tms.common.exception;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.rest.webmvc.support.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
 
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
 
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.csipl.hrms.common.util.ErrorHandling;

 
@ControllerAdvice
public class TmsRestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(TmsRestResponseEntityExceptionHandler.class);

 
	@ExceptionHandler(ErrorHandling.class)
	public final ResponseEntity<ErrorHandling> handleErrorHandling(ErrorHandling e) {
		// return e.getMessage();
		System.out.println("handleErrorHandlingException " + e.getMessage());
		// e.printStackTrace();  
//		ErrorHandling error = new ErrorHandling(e.getMessage()); 
		return new ResponseEntity(e.getMessage(), new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
	}

	*//**
	 * Catch all for any other exceptions...
	 *//*
	@ExceptionHandler({ Exception.class })

	public ResponseEntity<?> handleAnyException(Exception e) {
		// System.out.println("handleAnyException");
		return errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	*//**
	 * Handle failures commonly thrown from code
	 *//*
	@ExceptionHandler({ InvocationTargetException.class, IllegalArgumentException.class, ClassCastException.class,
			ConversionFailedException.class })
	@ResponseBody
	public ResponseEntity handleMiscFailures(Throwable t) {
		return errorResponse(t, HttpStatus.BAD_REQUEST);
	}

	*//**
	 * Send a 409 Conflict in case of concurrent modification
	 *//*
	@ExceptionHandler({ ObjectOptimisticLockingFailureException.class, OptimisticLockingFailureException.class,
			DataIntegrityViolationException.class })
	@ResponseBody
	public ResponseEntity handleConflict(Exception ex) {
		return errorResponse(ex, HttpStatus.CONFLICT);
	}

	
	 * protected ResponseEntity<ExceptionMessage> errorResponse(Throwable throwable,
	 * HttpStatus status) {
	 * 
	 * System.out.println("errorResponse ==== "); if (null != throwable) {
	 * System.out.println(throwable.getMessage());; log.error("error caught: " +
	 * throwable.getMessage(), throwable); return response(new
	 * ExceptionMessage(throwable), status); } else {
	 * log.error("unknown error caught in RESTController, {}", status); return
	 * response(null, status); } }
	 

	protected ResponseEntity<ErrorHandling> errorResponse(Throwable throwable, HttpStatus status) {

		if (null != throwable) {
			log.error("error caught: " + throwable.getMessage(), throwable);
			// throwable.getStackTrace();
			// throwable.printStackTrace();
			return response(new ErrorHandling(status.getReasonPhrase()), status);
		} else {

			log.error("unknown error caught in RESTController, {}", status);
			return response(null, status);
		}
	}

	protected <T> ResponseEntity<T> response(T body, HttpStatus status) {
		log.debug("Responding with a status of {}", status);
		return new ResponseEntity<>(body, new HttpHeaders(), status);
	}
}*/