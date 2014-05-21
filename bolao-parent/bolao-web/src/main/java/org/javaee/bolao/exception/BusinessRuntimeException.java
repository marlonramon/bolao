package org.javaee.bolao.exception;

import java.text.MessageFormat;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BusinessRuntimeException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BusinessRuntimeException(Throwable e, String message, Object... args) {
		super(MessageFormat.format(message, args), e);
	}
	
	public BusinessRuntimeException(String message, Object... args) {
		super(MessageFormat.format(message, args));
	}
	
	public BusinessRuntimeException(String message) {
		super(message);
	}

	public BusinessRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessRuntimeException(Throwable cause) {
		super(cause);
	}
	
	
	
}
