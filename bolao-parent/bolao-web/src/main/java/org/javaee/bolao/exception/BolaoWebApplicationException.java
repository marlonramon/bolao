package org.javaee.bolao.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class BolaoWebApplicationException extends WebApplicationException{
	private static final long serialVersionUID = 1L;

	public BolaoWebApplicationException(String mensagem, Object... argumentos) {
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErroResposta(mensagem, argumentos)).build());
	}
	
	public BolaoWebApplicationException(ErroResposta erroResposta) {
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(erroResposta).build());
	}
	
	public BolaoWebApplicationException(Status status, String mensagem, Object... argumentos) {
		super(Response.status(status).entity(new ErroResposta(mensagem, argumentos)).build());
	}
	
	public BolaoWebApplicationException(Status status, String mensagem) {
		super(Response.status(status).entity(new ErroResposta(mensagem)).build());
	}

}
