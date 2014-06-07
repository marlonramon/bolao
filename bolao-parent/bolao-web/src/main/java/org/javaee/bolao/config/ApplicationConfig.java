package org.javaee.bolao.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.javaee.bolao.aposta.ApostaFacadeREST;
import org.javaee.bolao.bolao.BolaoFacadeREST;
import org.javaee.bolao.campeonato.CampeonatoFacadeREST;
import org.javaee.bolao.clube.ClubeFacadeREST;
import org.javaee.bolao.exception.BolaoExceptionMapper;
import org.javaee.bolao.exception.ConstraintViolationExceptionMapper;
import org.javaee.bolao.partida.PartidaFacadeREST;
import org.javaee.bolao.rodada.RodadaFacadeREST;
import org.javaee.bolao.usuario.UsuarioFacadeREST;
import org.javaee.bolao.usuariobolao.UsuarioBolaoFacadeREST;

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
        resources.add(ConstraintViolationExceptionMapper.class);
        resources.add(BolaoExceptionMapper.class);
//        resources.add(SecurityInterceptor.class);
        resources.add(CampeonatoFacadeREST.class);
        resources.add(BolaoFacadeREST.class);        
        resources.add(PartidaFacadeREST.class);
        resources.add(RodadaFacadeREST.class);
        resources.add(ClubeFacadeREST.class);
        resources.add(UsuarioBolaoFacadeREST.class);
        resources.add(ApostaFacadeREST.class);
    }
    
    @Override
    public Map<String, Object> getProperties() {
    	Map<String, Object> properties = new HashMap<String, Object>();
    	
    	properties.put("jersey.config.beanValidation.enableOutputValidationErrorEntity.server", true);
    	
    	return properties;
    	
    }
    
}

