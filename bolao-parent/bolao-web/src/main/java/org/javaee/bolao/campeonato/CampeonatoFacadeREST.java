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
import org.javaee.bolao.entidades.Rodada;

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
  @Path("{idCampeonato}")
  public void delete(@PathParam("idCampeonato") Long idCampeonato) {
    campeonatoFacade.delete(idCampeonato);
  }

  @GET
  @Path("{idCampeonato}")
  @Produces({"application/xml", "application/json"})
  public Campeonato find(@PathParam("idCampeonato") Long idCampeonato) {
    return campeonatoFacade.find(idCampeonato);
  }
  
  @GET
  @Path("{idCampeonato}/rodadas")
  @Produces({"application/xml", "application/json"})
  public List<Rodada> findRodadas(@PathParam("idCampeonato") Long idCampeonato) {
    return campeonatoFacade.findRodadas(idCampeonato);
  }

  @GET
  @Produces({"application/xml", "application/json"})
  public List<Campeonato> findAll() {
    return campeonatoFacade.findAll();
  }
}