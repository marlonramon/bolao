package org.javaee.bolao.rest;

import java.util.Set;

import javax.ws.rs.core.Application;

import org.javaee.bolao.rest.security.SecurityInterceptor;

@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
    	
        resources.add(UserFacadeREST.class);
        resources.add(CrossOriginResourceSharingFilter.class);
        resources.add(SecurityInterceptor.class);
    }
    
}

