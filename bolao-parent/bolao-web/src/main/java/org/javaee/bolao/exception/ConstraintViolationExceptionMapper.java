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
		
		Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
		
		ErroResposta errorBean = new ErroResposta(exception.getMessage());
		
		for (ConstraintViolation<?> constraintViolation : constraintViolations) {
			errorBean.addMensagem(constraintViolation.getPropertyPath()+ " "+ constraintViolation.getMessage());
		}
		
		logger.log(Level.SEVERE, "ConstraintViolationException: "+errorBean);
		
		return new BolaoWebApplicationException(errorBean).getResponse();
	}

}
