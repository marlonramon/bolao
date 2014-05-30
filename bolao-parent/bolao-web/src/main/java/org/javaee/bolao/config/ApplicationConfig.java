package org.javaee.bolao.config;

import java.util.Set;

import javax.ws.rs.core.Application;

import org.javaee.bolao.campeonato.CampeonatoFacadeREST;
import org.javaee.bolao.security.SecurityInterceptor;
import org.javaee.bolao.usuario.UsuarioFacadeREST;

@javax.ws.rs.ApplicationPath("/app")
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
    	
        resources.add(UsuarioFacadeREST.class);
        resources.add(CrossOriginResourceSharingFilter.class);
        resources.add(SecurityInterceptor.class);
        resources.add(CampeonatoFacadeREST.class);
    }
    
}

