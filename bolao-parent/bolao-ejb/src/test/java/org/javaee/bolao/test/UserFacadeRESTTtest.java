package org.javaee.bolao.test;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.javaee.bolao.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserFacadeRESTTtest {

	private static final String uriBase = "http://localhost:8080/bolao-web/rest";
	
	private User userTest = new User();
	
	public UserFacadeRESTTtest() {
	}
	
	@Before
	public void init(){
		userTest.setName("User Teste");
		userTest.setLogin("UserTest");
		userTest.setPassword("123");
		userTest.setEmail("user@test.com.br");
	}
	
	@Test
	public void insert(){
		
		Client client = ClientBuilder.newClient();
		
		//Entity<Class<User>> userJson = Entity.json(User.class);
		
		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path("users"));
		
		User user = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(userTest), User.class);
		
		Assert.assertNotNull(user.getIdUser());
		
		client.close();
		
		
	}
	
	@Test
	public void login(){
		
		Client client = ClientBuilder.newClient();
		
		//Entity<Class<User>> userJson = Entity.json(User.class);
		
		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path("users/login/"+userTest.getLogin()));
		
		User user = target.request(MediaType.APPLICATION_JSON_TYPE).get(User.class);
		
		if(user == null){
		}
		
		client.close();
		
		
	}
	
	@Test
	public void listUsers(){
		
		Client client = ClientBuilder.newClient();
		
		//Entity<Class<User>> userJson = Entity.json(User.class);
		
		GenericType<List<User>> responseType = new GenericType<List<User>>(){};
		
		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path("users"));
		
		List<User> userList = target.request(MediaType.APPLICATION_JSON_TYPE).get(responseType);
		
		for (User user : userList) {
			
			System.out.println(user.getIdUser());
			System.out.println(user.getLogin());
			System.out.println(user.getName());
			System.out.println(user.getPassword());
			
		}
		
		client.close();
		
	}
	
}
