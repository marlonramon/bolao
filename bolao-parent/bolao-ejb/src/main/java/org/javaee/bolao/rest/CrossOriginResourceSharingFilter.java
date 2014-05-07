package org.javaee.bolao.rest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.javaee.bolao.rest.security.SecurityInterceptor;

@Provider
public class CrossOriginResourceSharingFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {
        response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
        response.getHeaders().putSingle("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, HEAD");
        response.getHeaders().putSingle("Access-Control-Allow-Headers", "content-type, "+SecurityInterceptor.AUTHORIZATION_PROPERTY);
    }
}