package org.javaee.bolao.exception;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public Response toResponse(ConstraintViolationException exception) {
		logger.log(Level.SEVERE, "ConstraintViolationException: "+exception.getMessage(), exception);
		
		Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
		
		ErrorBean errorBean = new ErrorBean(exception.getMessage());
		
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			errorBean.addChildMessage(constraintViolation.getMessage());
		}
		
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorBean).build();
	}

}
