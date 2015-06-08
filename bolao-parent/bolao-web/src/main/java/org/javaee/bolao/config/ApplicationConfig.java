package org.javaee.bolao.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.javaee.bolao.security.AuthzController;

@javax.ws.rs.ApplicationPath("/app")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    
    @Override
    public Map<String, Object> getProperties() {
    	Map<String, Object> properties = new HashMap<String, Object>();
    	
//    	properties.put("jersey.config.beanValidation.enableOutputValidationErrorEntity.server", true);
    	
    	return properties;
    	
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
    	resources.add(org.javaee.bolao.aposta.ApostaFacadeREST.class);
        resources.add(org.javaee.bolao.bolao.BolaoFacadeREST.class);
        resources.add(org.javaee.bolao.campeonato.CampeonatoFacadeREST.class);
        resources.add(org.javaee.bolao.clube.ClubeFacadeREST.class);
        resources.add(org.javaee.bolao.config.CrossOriginResourceSharingFilter.class);
        resources.add(org.javaee.bolao.exception.BolaoExceptionMapper.class);
        resources.add(org.javaee.bolao.exception.ConstraintViolationExceptionMapper.class);
        resources.add(org.javaee.bolao.partida.PartidaFacadeREST.class);
        resources.add(org.javaee.bolao.rodada.RodadaFacadeREST.class);
        resources.add(org.javaee.bolao.security.SecurityInterceptor.class);
        resources.add(org.javaee.bolao.usuario.UsuarioFacadeREST.class);
        resources.add(org.javaee.bolao.usuariobolao.UsuarioBolaoFacadeREST.class);
        resources.add(AuthzController.class);
        resources.add(CORSResourceRequestFilter.class);
        
    }
    
}

