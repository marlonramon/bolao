package org.javaee.bolao.config;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;

public class CORSResourceRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		if (requestContext.getRequest().getMethod().equals("OPTIONS")) {
			// Just send a OK signal back to the browser
			requestContext.abortWith(Response.status(Response.Status.OK).build());
		}

	}

}
