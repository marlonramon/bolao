package org.javaee.bolao.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessRuntimeException> {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public Response toResponse(BusinessRuntimeException exception) {
		logger.log(Level.SEVERE, "BusinessExceptionMapper: "+exception.getMessage(), exception);
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
	}

}
