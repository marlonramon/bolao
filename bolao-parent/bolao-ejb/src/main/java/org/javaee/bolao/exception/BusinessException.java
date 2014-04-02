package org.javaee.bolao.exception;

import java.text.MessageFormat;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BusinessException extends Exception{
	private static final long serialVersionUID = 1L;

	public BusinessException(Throwable e, String message, Object... args) {
		super(MessageFormat.format(message, args), e);
	}
	
	public BusinessException(String message, Object... args) {
		super(MessageFormat.format(message, args));
	}
	
	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	
	
}
