package org.javaee.bolao.config;

import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.javaee.bolao.bolao.BolaoFacadeREST;
import org.javaee.bolao.campeonato.CampeonatoFacadeREST;
import org.javaee.bolao.clube.ClubeFacadeREST;
import org.javaee.bolao.partida.PartidaFacadeREST;
import org.javaee.bolao.rodada.RodadaFacadeREST;
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
//        resources.add(SecurityInterceptor.class);
        resources.add(CampeonatoFacadeREST.class);
        resources.add(BolaoFacadeREST.class);        
        resources.add(PartidaFacadeREST.class);
        resources.add(RodadaFacadeREST.class);
        resources.add(ClubeFacadeREST.class);
    }
    
    @Override
    public Map<String, Object> getProperties() {
    	return super.getProperties();
    }
    
}

