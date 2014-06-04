package org.javaee.bolao.usuariobolao;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javaee.bolao.entidades.UsuarioBolao;

@Path("usuarioboloes")
public class UsuarioBolaoFacadeREST
{

  @Inject
  private UsuarioBolaoFacade usuarioFacade;

  @POST
  @Consumes({"application/xml", "application/json"})
  public void insertOrUpdate(UsuarioBolao usuarioBolao)
  {
    usuarioFacade.vincularUsuarioBolao(usuarioBolao.getUsuario().getIdUsuario(), usuarioBolao.getBolao().getIdBolao());    		
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Long id) {
    this.usuarioFacade.delete(id);
  }

//  @GET
//  @Path("{id}")
//  @Produces({"application/xml", "application/json"})
//  public UsuarioBolao find(@PathParam("id") Long id) {
//    return this.usuarioFacade.find(id);
//  }
//
  @GET
  @Path("usuarios/{idUsuario}")
  @Produces({"application/xml", "application/json"})
  public List<UsuarioBolao> findAll(@PathParam("idUsuario") Long idUsuario) {
    return this.usuarioFacade.findAll(idUsuario);
  }

}