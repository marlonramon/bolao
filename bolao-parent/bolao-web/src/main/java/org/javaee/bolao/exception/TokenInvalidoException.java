package org.javaee.bolao.exception;

import javax.ws.rs.core.Response.Status;

public class TokenInvalidoException extends BolaoWebApplicationException {
	private static final long serialVersionUID = 1L;

	public TokenInvalidoException(String messagem) {
		super(Status.UNAUTHORIZED, messagem);
	}
	
	public TokenInvalidoException() {
		this("Token de acesso inválido");
	}
	
}
