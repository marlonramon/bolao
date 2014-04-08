package org.javaee.bolao.rest;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.persistence.internal.oxm.conversion.Base64;

public class SecurityInterceptor implements ContainerRequestFilter {
	
	private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
	

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		
		 //Get request headers
        final MultivaluedMap<String, String> headers = requestContext.getHeaders();

        //Fetch authorization header
        final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

        //If no authorization information present; block access
        if (authorization == null || authorization.isEmpty()) {
        	System.out.println("nao achou o cabecalho");
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
            return;
        }
        
        //Get encoded username and password
        final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

        //Decode username and password
        String usernameAndPassword;
        usernameAndPassword = new String(Base64.base64Decode(encodedUserPassword.getBytes()));

        //Split username and password tokens
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

        //Verifying Username and password
        System.out.println(username);
        System.out.println(password);

        if(!(username.equals("marlon") && password.equals("123"))) {
           requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());        
        }
		
	
		
		
	}
	
	

}
