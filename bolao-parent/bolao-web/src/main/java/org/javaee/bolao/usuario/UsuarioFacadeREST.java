package org.javaee.bolao.usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
    public Usuario insertOrUpdate(Usuario usuario) {
    	return usuarioFacade.insertOrUpdate(usuario);
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
    @Path("email/{email}")
    @Produces({"application/xml", "application/json"})
    public Usuario findByEmail(@PathParam("email") String login) {
    	return usuarioFacade.findByEmail(login);
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
    public SessaoUsuario login(@NotNull Usuario user) {
    	return usuarioFacade.login(user.getEmail(), user.getSenha());
    }
    
    @POST
    @Path("logout")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public Response logout(@NotNull String email) {
    	if(usuarioFacade.logout(email)){
    		return Response.ok().build();
    	}else{
    		return Response.notModified().build();
    	}
    }
    
}
