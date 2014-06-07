package org.javaee.bolao.exception;

import java.text.MessageFormat;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class BolaoRuntimeException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public BolaoRuntimeException(Throwable e, String message, Object... args) {
		super(MessageFormat.format(message, args), e);
	}
	
	public BolaoRuntimeException(String message, Object... args) {
		super(MessageFormat.format(message, args));
	}
	
	public BolaoRuntimeException(String message) {
		super(message);
	}

	public BolaoRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BolaoRuntimeException(Throwable cause) {
		super(cause);
	}
	
	
	
}
