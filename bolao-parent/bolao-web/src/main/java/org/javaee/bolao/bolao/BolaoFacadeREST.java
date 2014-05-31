package org.javaee.bolao.bolao;

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

import org.javaee.bolao.entidades.Bolao;

@Stateless
@Path("boloes")
public class BolaoFacadeREST
{

  @Inject
  private BolaoFacade bolaoFacade;

  @POST
  @Consumes({"application/xml", "application/json"})
  public Bolao insertOrUpdate(Bolao bolao)
  {
    return this.bolaoFacade.insertOrUpdate(bolao);
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Long id) {
    this.bolaoFacade.delete(id);
  }

  @GET
  @Path("{id}")
  @Produces({"application/xml", "application/json"})
  public Bolao find(@PathParam("id") Long id) {
    return this.bolaoFacade.find(id);
  }

  @GET
  @Produces({"application/xml", "application/json"})
  public List<Bolao> findAll() {
    return this.bolaoFacade.findAll();
  }
}