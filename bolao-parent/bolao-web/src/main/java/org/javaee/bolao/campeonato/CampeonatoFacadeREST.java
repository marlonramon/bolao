package org.javaee.bolao.campeonato;

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

import org.javaee.bolao.entidades.Campeonato;

@Stateless
@Path("campeonatos")
public class CampeonatoFacadeREST
{

  @Inject
  private CampeonatoFacade campeonatoFacade;

  @POST
  @Consumes({"application/xml", "application/json"})
  public Campeonato insertOrUpdate(Campeonato campeonato)
  {
    return this.campeonatoFacade.insertOrUpdate(campeonato);
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Long id) {
    this.campeonatoFacade.delete(id);
  }

  @GET
  @Path("{id}")
  @Produces({"application/xml", "application/json"})
  public Campeonato find(@PathParam("id") Long id) {
    return this.campeonatoFacade.find(id);
  }

  @GET
  @Produces({"application/xml", "application/json"})
  public List<Campeonato> findAll() {
    return this.campeonatoFacade.findAll();
  }
}