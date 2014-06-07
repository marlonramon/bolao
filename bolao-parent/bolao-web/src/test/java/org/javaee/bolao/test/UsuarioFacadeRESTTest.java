package org.javaee.bolao.test;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.Usuario.PerfilUsuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UsuarioFacadeRESTTest {

	private static final String EMAIL_TEST = "teste@test.com.br";
	private static final String uriBase = "http://localhost:8080/bolao-web/app";

	public UsuarioFacadeRESTTest() {
	}

	@Before
	public void init() {

	}

	private Usuario findByEmail(String email) {

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(UriBuilder.fromPath(uriBase)
				.path("usuarios/email").path(EMAIL_TEST));

		Usuario user = target.request(MediaType.APPLICATION_XML_TYPE).get(
				Usuario.class);

		client.close();

		return user;
	}

	private Usuario insert(Usuario usuario) {
		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path(
				"usuarios"));

		Entity<Usuario> xml = Entity.xml(usuario);

		usuario = target.request(MediaType.APPLICATION_XML_TYPE).post(xml,
				Usuario.class);

		client.close();

		return usuario;

	}

	private Usuario update(Usuario usuario) {
		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path(
				"usuarios"));

		Entity<Usuario> xml = Entity.xml(usuario);

		usuario = target.request(MediaType.APPLICATION_XML_TYPE).post(xml,
				Usuario.class);

		client.close();

		return usuario;

	}

	@Test
	public void insert() {

		Usuario userTest = new Usuario();// findByEmail(EMAIL_TEST);

		// userTest.setNome("teste");
		userTest.setSenha("teste");
		userTest.setEmail(EMAIL_TEST);
		userTest.setPerfil(PerfilUsuario.USUARIO);

		insert(userTest);

		Assert.assertNotNull(userTest.getIdUsuario());

		Assert.assertEquals(userTest.getEmail(), EMAIL_TEST);

	}

	@Test
	@Ignore
	public void loginFail() {

		Usuario userTest = new Usuario();

		userTest.setEmail(EMAIL_TEST);
		userTest.setSenha("");

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path(
				"usuarios/login"));

		Entity<Usuario> xml = Entity.xml(userTest);

		Response response = target.request(MediaType.APPLICATION_XML_TYPE)
				.post(xml);

		Assert.assertEquals(response.getStatus(),
				Status.UNAUTHORIZED.getStatusCode());

		client.close();

	}

	@Test
	@Ignore
	public void login() {

		Usuario userTest = new Usuario();

		userTest.setEmail(EMAIL_TEST);
		userTest.setSenha("teste");

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path(
				"usuarios/login"));

		Entity<Usuario> xml = Entity.xml(userTest);

		SessaoUsuario sessaoUsuario = target.request(
				MediaType.APPLICATION_XML_TYPE).post(xml, SessaoUsuario.class);

		Assert.assertNotNull(sessaoUsuario);

		client.close();

	}

	@Test
	@Ignore
	public void logout() {

		Usuario userTest = new Usuario();

		userTest.setEmail(EMAIL_TEST);
		userTest.setSenha("teste");

		Client client = ClientBuilder.newClient();

		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path(
				"usuarios/logout"));

		Entity<Usuario> xml = Entity.xml(userTest);

		SessaoUsuario sessaoUsuario = target.request(
				MediaType.APPLICATION_XML_TYPE).post(xml, SessaoUsuario.class);

		Assert.assertNotNull(sessaoUsuario);

		client.close();

	}

	// @Test
	public void listUsers() {

		Client client = ClientBuilder.newClient();

		// Entity<Class<User>> userJson = Entity.json(User.class);

		GenericType<List<Usuario>> responseType = new GenericType<List<Usuario>>() {
		};

		WebTarget target = client.target(UriBuilder.fromPath(uriBase).path(
				"usuarios"));

		List<Usuario> userList = target.request(MediaType.APPLICATION_XML_TYPE)
				.get(responseType);

		for (Usuario user : userList) {

			System.out.println(user.getIdUsuario());
			System.out.println(user.getNome());
			System.out.println(user.getSenha());

		}

		client.close();

	}

}
