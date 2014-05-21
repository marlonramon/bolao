package org.javaee.bolao.test;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.Usuario.PerfilUsuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserFacadeRESTTtest {

	private static final String uriBase = "http://localhost:8080/bolao-web/";
	
	public UserFacadeRESTTtest() {
	}
	
	@Before
	public void init(){
		
	}
	
	@Test
	public void insert(){
		
		Usuario userTest = new Usuario(); 
		
		userTest.setNome("teste");
		userTest.setSenha("teste");
		userTest.setEmail("teste@test.com.br");
		userTest.setPerfil(PerfilUsuario.USUARIO);
		
		Client client = ClientBuilder.newClient();
		
		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path("users"));
		
		Usuario user = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(userTest), Usuario.class);
		
		Assert.assertNotNull(user.getIdUsuario());
		
		client.close();
		
		
	}
	
//	@Test
	public void login(){
		
		Usuario userTest = new Usuario();
		
		Client client = ClientBuilder.newClient();
		
		//Entity<Class<User>> userJson = Entity.json(User.class);
		
		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path("users/login/"+userTest.getIdUsuario()));
		
		Usuario user = target.request(MediaType.APPLICATION_JSON_TYPE).get(Usuario.class);
		
		if(user == null){
		}
		
		client.close();
		
		
	}
	
//	@Test
	public void listUsers(){
		
		Client client = ClientBuilder.newClient();
		
		//Entity<Class<User>> userJson = Entity.json(User.class);
		
		GenericType<List<Usuario>> responseType = new GenericType<List<Usuario>>(){};
		
		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path("users"));
		
		List<Usuario> userList = target.request(MediaType.APPLICATION_JSON_TYPE).get(responseType);
		
		for (Usuario user : userList) {
			
			System.out.println(user.getIdUsuario());
			System.out.println(user.getNome());
			System.out.println(user.getSenha());
			
		}
		
		client.close();
		
	}
	
}
