package org.javaee.bolao.config;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
public class CrossOriginResourceSharingFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {
    	
    	System.out.println("akiiiii");
    	
    	final MultivaluedMap<String,Object> headers = response.getHeaders();
    	
    	headers.add("Access-Control-Allow-Origin", "*");
    	headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Headers", "Authorization, Origin, X-Requested-With, Content-Type");
        headers.add("Access-Control-Expose-Headers", "Location, Content-Disposition");
        headers.add("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, HEAD, OPTIONS");
        
        /*response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        response.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD");
        response.getHeaders().putSingle("Access-Control-Allow-Headers", "content-type, "+SecurityInterceptor.AUTHORIZATION_PROPERTY);*/
    }
}