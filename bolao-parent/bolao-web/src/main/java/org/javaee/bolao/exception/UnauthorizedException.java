package org.javaee.bolao.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends WebApplicationException {
	private static final long serialVersionUID = 1L;

	public UnauthorizedException(String messagem) {
		super(messagem, Status.UNAUTHORIZED);
	}
}
