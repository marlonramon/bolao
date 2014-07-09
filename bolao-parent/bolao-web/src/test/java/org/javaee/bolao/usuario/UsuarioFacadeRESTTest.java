package org.javaee.bolao.usuario;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UsuarioFacadeRESTTest {

	
	@Test @Ignore
	public void teste1(){
		Assert.assertTrue(true);
	}
	
	@Test(expected=IllegalArgumentException.class) @Ignore
	public void teste2(){
		throw new IllegalArgumentException();
	}
	
	@Test
	public void teste3(){
			Client client = ClientBuilder.newClient();
			
			WebTarget target = client.target("http://obi-wankenobi.datacoper.com.br:58080/bolao-web/app/usuarios");
					
			Builder request = target.request(MediaType.APPLICATION_XML_TYPE);
			
			Response resposen = request.get();
			
			Assert.assertEquals(Response.status(Status.UNAUTHORIZED.getStatusCode()), resposen.getStatus());
		
	}
	
	
	
}
