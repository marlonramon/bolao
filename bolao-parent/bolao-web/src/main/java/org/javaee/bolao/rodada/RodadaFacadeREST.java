package org.javaee.bolao.rodada;

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

import org.javaee.bolao.entidades.Rodada;

@Stateless
@Path("rodadas")
public class RodadaFacadeREST
{

  @Inject
  private RodadaFacade rodadaFacade;

  @POST
  @Consumes({"application/xml", "application/json"})
  public Rodada insertOrUpdate(Rodada rodada)
  {
    return this.rodadaFacade.insertOrUpdate(rodada);
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Long id) {
    this.rodadaFacade.delete(id);
  }

  @GET
  @Path("{id}")
  @Produces({"application/xml", "application/json"})
  public Rodada find(@PathParam("id") Long id) {
    return this.rodadaFacade.find(id);
  }

  @GET
  @Produces({"application/xml", "application/json"})
  public List<Rodada> findAll() {
    return this.rodadaFacade.findAll();
  }
}