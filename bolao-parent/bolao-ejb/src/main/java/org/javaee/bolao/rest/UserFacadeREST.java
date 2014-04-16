package org.javaee.bolao.rest;

import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javaee.bolao.config.Config;
import org.javaee.bolao.eao.UserEAO;
import org.javaee.bolao.eao.UserSessionEAO;
import org.javaee.bolao.entities.User;
import org.javaee.bolao.entities.UserSession;
import org.javaee.bolao.rest.security.IUserRoles;

@Stateless
@Path("users")
public class UserFacadeREST {
    
	@Inject
	private UserEAO userEAO;
	
    public UserFacadeREST() {
    }
    
    @Inject
	private UserSessionEAO userSessionEAO ;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void insertOrUpdate(User entity) {
    	
    	if(!entity.hasId()){
	    	entity.setDateInsert(new Date());
	    	entity.setDateLastAccess(new Date());
	    	
	    	userEAO.insert(entity);
    	}else{
    		userEAO.update(entity);
    	}
    	
//    	throw new WebApplicationException(Response.status(Response.Status.CONFLICT).entity(new ErrorBean("Usuario duplicado")).build());
//    	throw new BusinessRuntimeException("O usuario já existe.");
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
    @RolesAllowed(IUserRoles.ADMIN)
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
    
    @GET
    @Path("login")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public UserSession login(User user) {
    	
    	UserSession validSession = userSessionEAO.findValidSession(user.getLogin(), new Date());
    	
    	if(validSession == null){
    		validSession = userSessionEAO.create(user, Config.getExpirationLoginTime());
    	}
    	
    	return validSession;
    }
    
    @GET
    @Path("logout")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/xml", "application/json"})
    public UserSession logout(User user) {
    	Date expirateDate = new Date();
		UserSession validSession = userSessionEAO.findValidSession(user.getLogin(), expirateDate);
    	validSession.setExpirateDate(expirateDate);
    	return validSession;
    }
    
}
