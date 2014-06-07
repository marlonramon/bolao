package org.javaee.bolao.usuario;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
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
import org.javaee.bolao.entidades.UsuarioBolao;

@Path("usuarios")
public class UsuarioFacadeREST
{

  @Inject
  private UsuarioFacade usuarioFacade;

  @POST
  @Consumes({"application/xml", "application/json"})
  public Usuario insertOrUpdate(Usuario usuario)
  {	
    return this.usuarioFacade.insertOrUpdate(usuario);
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Long id) {
    this.usuarioFacade.delete(id);
  }

  @GET
  @Path("{id}")
  @Produces({"application/xml", "application/json"})
  public Usuario find(@PathParam("id") Long id) {
    return this.usuarioFacade.find(id);
  }

  @GET
  @Path("email/{email}")
  @Produces({"application/xml", "application/json"})
  public Usuario findByEmail(@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "E-mail inválido") @PathParam("email") String login) {
    return this.usuarioFacade.findByEmail(login);
  }

  @GET
  @Produces({"application/xml", "application/json"})
  public List<Usuario> findAll() {
    return this.usuarioFacade.findAll();
  }

//  @GET
//  @Path("{from}/{to}")
//  @Produces({"application/xml", "application/json"})
//  public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//    return this.usuarioFacade.findRange(from, to);
//  }
//
//  @GET
//  @Path("count")
//  @Produces({"text/plain"})
//  public String count() {
//    return String.valueOf(this.usuarioFacade.count());
//  }

  @POST
  @Path("login")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public SessaoUsuario login(@NotNull Usuario user) {
    return this.usuarioFacade.login(user.getEmail(), user.getSenha());
  }

  @POST
  @Path("logout")
  @Consumes({"application/xml", "application/json"})
  @Produces({"application/xml", "application/json"})
  public Response logout(@NotNull Usuario usuario) {
    if (this.usuarioFacade.logout(usuario.getEmail())) {
      return Response.ok().build();
    }
    return Response.notModified().build();
  }
  
  @GET
  @Path("{idUsuario}/boloes")
  @Produces({"application/xml", "application/json"})
  public List<UsuarioBolao> findBoloes(@PathParam("idUsuario") Long idUsuario) {
    return this.usuarioFacade.findBoloes(idUsuario);
  }
}