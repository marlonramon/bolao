package org.javaee.bolao.usuario;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.security.Acesso;
import org.javaee.bolao.security.RestricaoAcesso;
import org.javaee.bolao.security.SecurityInterceptor;

@Path("usuarios")
public class UsuarioFacadeREST {

	@Inject
	private UsuarioFacade usuarioFacade;

	@POST
	@Consumes({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.ANONIMO)
	public void insertOrUpdate(Usuario usuario, @HeaderParam(SecurityInterceptor.AUTHORIZATION_PROPERTY) String token) {
		usuarioFacade.insertOrUpdate(usuario, token);
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") Long id) {
		this.usuarioFacade.delete(id);
	}

	@GET
	@Path("{id}")
	@Produces({ "application/xml", "application/json" })
	public Usuario find(@PathParam("id") Long id) {
		return this.usuarioFacade.find(id);
	}

	@GET
	@Path("email/{email}")
	@Produces({ "application/xml", "application/json" })
	public Usuario findByEmail(@PathParam("email") String login) {
		return this.usuarioFacade.findByEmail(login);
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public List<Usuario> findAll() {
		return this.usuarioFacade.findAll();
	}

	@POST
	@Path("login")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.ANONIMO)
	public Response login(Usuario user) {
		return Response.ok(this.usuarioFacade.login(user.getEmail(), user.getSenha())).build();
	}

	@POST
	@Path("logout")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.ANONIMO)
	public Response logout(Usuario usuario) {
		if (this.usuarioFacade.logout(usuario.getEmail())) {
			return Response.ok().build();
		}
		return Response.notModified().build();
	}

	@GET
	@Path("{idUsuario}/boloes")
	@Produces({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.USUARIO)
	public List<UsuarioBolao> findBoloes(@PathParam("idUsuario") Long idUsuario) {
		return this.usuarioFacade.findBoloes(idUsuario);
	}
}