package org.javaee.bolao.usuario;

import java.util.Collections;
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

import org.javaee.bolao.beans.SessaoUsuarioVO;
import org.javaee.bolao.beans.UsuarioBolaoVO;
import org.javaee.bolao.beans.UsuarioVO;
import org.javaee.bolao.entidades.SessaoUsuario;
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
		Response.ok(usuarioFacade.insertOrUpdate(usuario, token)).build();
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
	@RestricaoAcesso(acesso = Acesso.ANONIMO)
	@Produces({ "application/xml", "application/json" })
	public Response login(Usuario user) {
		
		SessaoUsuario sessaoUsuario = this.usuarioFacade.login(user.getEmail(), user.getSenha());
		
		Usuario usuario = sessaoUsuario.getUsuario();
		
		UsuarioVO usuarioVO = new UsuarioVO(usuario.getIdUsuario(), usuario.getNome(), usuario.getEmail(), usuario.getAdmin());
		
		SessaoUsuarioVO sessaoUsuarioVO = new SessaoUsuarioVO(usuarioVO, sessaoUsuario.getToken(), sessaoUsuario.getCode());
		
		return Response.ok(sessaoUsuarioVO).build();
	}

	@POST
	@Path("logout")
	@RestricaoAcesso(acesso = Acesso.ANONIMO)
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Response logout(Usuario usuario) {
		
		this.usuarioFacade.logout(usuario.getEmail());

		return Response.ok().build();
	}

	@GET
	@Path("{idUsuario}/boloes")
	@Produces({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.USUARIO)
	public List<UsuarioBolaoVO> findBoloes(@PathParam("idUsuario") Long idUsuario) {
		
		List<UsuarioBolao> usuarioBoloes = this.usuarioFacade.findBoloes(idUsuario);
		
		List<UsuarioBolaoVO> usuarioBoloesVO = Collections.emptyList();
		
		for (UsuarioBolao usuarioBolao : usuarioBoloes) {
			usuarioBoloesVO.add(new UsuarioBolaoVO(usuarioBolao.getIdUsuarioBolao(), usuarioBolao.getBolao()));
		}
		
		return usuarioBoloesVO;
	}
}