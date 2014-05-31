package org.javaee.bolao.partida;

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

import org.javaee.bolao.entidades.Partida;

@Stateless
@Path("partidas")
public class PartidaFacadeREST
{

  @Inject
  private PartidaFacade partidaFacade;

  @POST
  @Consumes({"application/xml", "application/json"})
  public Partida insertOrUpdate(Partida partida)
  {
    return this.partidaFacade.insertOrUpdate(partida);
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Long id) {
    this.partidaFacade.delete(id);
  }

  @GET
  @Path("{id}")
  @Produces({"application/xml", "application/json"})
  public Partida find(@PathParam("id") Long id) {
    return this.partidaFacade.find(id);
  }

  @GET
  @Produces({"application/xml", "application/json"})
  public List<Partida> findAll() {
    return this.partidaFacade.findAll();
  }
}