package org.javaee.bolao.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BolaoExceptionMapper implements ExceptionMapper<BolaoRuntimeException> {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Override
	public Response toResponse(BolaoRuntimeException exception) {
		logger.log(Level.SEVERE, exception.getMessage(), exception);
		
		BolaoWebApplicationException webApplicationException = new BolaoWebApplicationException(exception.getMessage());
		
		return webApplicationException.getResponse();
	}

}
