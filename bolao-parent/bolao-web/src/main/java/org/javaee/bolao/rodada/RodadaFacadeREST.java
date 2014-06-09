package org.javaee.bolao.rodada;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javaee.bolao.entidades.Rodada;
import org.javaee.bolao.vo.RankingRodadaVO;

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
    return rodadaFacade.findAll();
  }
  
  @GET
  @Path("{id}/ranking/{limite}")
  @Produces({"application/xml", "application/json"})
  public RankingRodadaVO ranking(@PathParam("id") Long id, @PathParam("limite") Integer limite) {
    return this.rodadaFacade.ranking(id, limite);
  }
  
}