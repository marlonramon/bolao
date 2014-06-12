package org.javaee.bolao.exception;

import javax.ws.rs.core.Response.Status;

public class SessaoExpiradaException extends BolaoWebApplicationException {
	private static final long serialVersionUID = 1L;

	public SessaoExpiradaException(String messagem) {
		super(Status.UNAUTHORIZED, messagem);
	}
	
	public SessaoExpiradaException() {
		this("Sessão Expirada. Faça login novamente.");
	}
	
}
