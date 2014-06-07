package org.javaee.bolao.exception;

import javax.ws.rs.core.Response.Status;

public class NaoAutorizadoException extends BolaoWebApplicationException {
	private static final long serialVersionUID = 1L;

	public NaoAutorizadoException(String messagem) {
		super(Status.UNAUTHORIZED, messagem);
	}
}
