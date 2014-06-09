package org.javaee.bolao.clube;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javaee.bolao.entidades.Clube;

@Path("clubes")
public class ClubeFacadeREST
{

  @Inject
  private ClubeFacade clubeFacade;

  @POST
  @Consumes({"application/xml", "application/json"})
  public Clube insertOrUpdate(Clube clube)
  {
    return this.clubeFacade.insertOrUpdate(clube);
  }

  @DELETE
  @Path("{id}")
  public void delete(@PathParam("id") Long id) {
    this.clubeFacade.delete(id);
  }

  @GET
  @Path("{id}")
  @Produces({"application/xml", "application/json"})
  public Clube find(@PathParam("id") Long id) {
    return this.clubeFacade.find(id);
  }

  @GET
  @Produces({"application/xml", "application/json"})
  public List<Clube> findAll() {
    return this.clubeFacade.findAll();
  }
}