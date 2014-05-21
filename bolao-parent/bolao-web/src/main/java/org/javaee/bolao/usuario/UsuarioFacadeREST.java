package org.javaee.bolao.usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;

@Stateless
@Path("users")
public class UsuarioFacadeREST {
    
	@Inject
	private UsuarioFacade usuarioFacade;
	
    public UsuarioFacadeREST() {
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public void insertOrUpdate(Usuario user) {
    	usuarioFacade.insertOrUpdate(user);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
    	usuarioFacade.delete(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Usuario find(@PathParam("id") Long id) {
    	return usuarioFacade.find(id);
    }
    
    @GET
    @Path("login/{login}")
    @Produces({"application/xml", "application/json"})
    public Usuario findByLogin(@PathParam("login") String login) {
    	return usuarioFacade.findByLogin(login);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<Usuario> findAll() {
    	return usuarioFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return usuarioFacade.findRange(from, to);
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String count() {
        return String.valueOf(usuarioFacade.count());
    }
    
    @POST
    @Path("login")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public SessaoUsuario login(Usuario user) {
    	return usuarioFacade.login(user);
    }
    
    @POST
    @Path("logout")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public SessaoUsuario logout(Usuario user) {
    	return usuarioFacade.logout(user);
    }
    
}
