package org.javaee.bolao.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javaee.bolao.eao.UserEAO;
import org.javaee.bolao.entities.User;

@Stateless
@Path("users")
public class UserFacadeREST {
    
	@Inject
	private UserEAO userEAO;
	
    public UserFacadeREST() {
    }

    @POST
    @Consumes({"application/xml", "application/json"})
    public void insert(User entity) {
    	
    	entity.setDateInsert(new Date());
    	entity.setDateLastAccess(new Date());
    	
    	userEAO.insert(entity);
    	
//    	throw new WebApplicationException(Response.status(Response.Status.CONFLICT).entity(new ErrorBean("Usuario duplicado")).build());
//    	throw new BusinessRuntimeException("O usuario já existe.");
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public User update(@PathParam("id") Long id, User entity) {
    	return userEAO.update(entity);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
    	userEAO.delete(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public User find(@PathParam("id") Long id) {
    	return userEAO.find(id);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<User> findAll() {
    	return userEAO.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return userEAO.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String count() {
        return String.valueOf(userEAO.count());
    }
    
    
    
}
