package org.javaee.bolao.rest;

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

import org.javaee.bolao.entities.User;
import org.javaee.bolao.entities.UserSession;

@Stateless
@Path("users")
public class UserFacadeREST {
    
	@Inject
	private UserFacade userFacade;
	
    public UserFacadeREST() {
    }
    
    @POST
    @Consumes({"application/xml", "application/json"})
    public void insertOrUpdate(User user) {
    	userFacade.insertOrUpdate(user);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Long id) {
    	userFacade.delete(id);
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public User find(@PathParam("id") Long id) {
    	return userFacade.find(id);
    }
    
    @GET
    @Path("login/{login}")
    @Produces({"application/xml", "application/json"})
    public User findByLogin(@PathParam("login") String login) {
    	return userFacade.findByLogin(login);
    }

    @GET
    @Produces({"application/xml", "application/json"})
    public List<User> findAll() {
    	return userFacade.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return userFacade.findRange(from, to);
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String count() {
        return String.valueOf(userFacade.count());
    }
    
    @POST
    @Path("login")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public UserSession login(User user) {
    	return userFacade.login(user);
    }
    
    @POST
    @Path("logout")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public UserSession logout(User user) {
    	return userFacade.logout(user);
    }
    
}
