package org.javaee.bolao.security;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

public class NaoAutorizadoException extends WebApplicationException {
	private static final long serialVersionUID = 1L;

	public NaoAutorizadoException(String messagem) {
		super(messagem, Status.UNAUTHORIZED);
	}
}
