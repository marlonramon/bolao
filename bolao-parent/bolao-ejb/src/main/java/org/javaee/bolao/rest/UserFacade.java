package org.javaee.bolao.rest;

import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.javaee.bolao.config.Config;
import org.javaee.bolao.eao.UserEAO;
import org.javaee.bolao.eao.UserSessionEAO;
import org.javaee.bolao.entities.User;
import org.javaee.bolao.entities.UserSession;
import org.javaee.bolao.exception.ErrorResponse;

@ManagedBean
public class UserFacade {
    
	@Inject
	private UserEAO userEAO;
	
    public UserFacade() {
    }
    
    @Inject
	private UserSessionEAO userSessionEAO ;

    public void insertOrUpdate(User user) {
    	
    	validateDuplicateLogin(user);
    	
    	if(!user.hasId()){
	    	userEAO.insert(user);
    	}else{
    		userEAO.update(user);
    	}
    	
    }
    
    public void validateDuplicateLogin(User user){
    	User userDB = userEAO.findByLogin(user.getLogin());
    	if(userDB != null && !userDB.equals(user)){
    		throw new WebApplicationException(Response.status(Status.CONFLICT).entity(new ErrorResponse("Já existe um Usuário com o Login {0}", user.getLogin())).build());
    	}
    }

    public void delete(Long id) {
    	userEAO.delete(id);
    }

    public User find(Long id) {
    	return userEAO.find(id);
    }

    public List<User> findAll() {
    	return userEAO.findAll();
    }

    public List<User> findRange(Integer from, Integer to) {
        return userEAO.findRange(new int[]{from, to});
    }

    public String count() {
        return String.valueOf(userEAO.count());
    }
    
    public UserSession login(User user) {
    	
    	UserSession validSession = userSessionEAO.findValidSession(user.getLogin(), new Date());
    	
    	if(validSession == null){
    		validSession = userSessionEAO.create(user, Config.getExpirationLoginTime());
    	}
    	
    	return validSession;
    }
    
    public UserSession logout(User user) {
    	Date expirateDate = new Date();
		UserSession validSession = userSessionEAO.findValidSession(user.getLogin(), expirateDate);
    	
		if(validSession != null){
    		validSession.setExpiratedDate(expirateDate);
    	}
		
    	return validSession;
    }

	public User findByLogin(String login) {
		return userEAO.findByLogin(login);
	}
    
}
