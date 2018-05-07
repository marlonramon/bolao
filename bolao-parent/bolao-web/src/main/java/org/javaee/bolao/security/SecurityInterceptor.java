package org.javaee.bolao.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.javaee.bolao.config.BolaoConfig;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.exception.NaoAutorizadoException;
import org.javaee.bolao.exception.SessaoExpiradaException;
import org.javaee.bolao.exception.TokenInvalidoException;
import org.javaee.bolao.usuario.UsuarioFacade;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {
	public static final String AUTHORIZATION_PROPERTY = "Authorization";

	@Context
	private ResourceInfo resourceInfo;

//	@Inject
	private UsuarioFacade usuarioFacade;

	public void filter(ContainerRequestContext requestContext) throws IOException {
		doFilter(requestContext);
	}

	private void doFilter(ContainerRequestContext requestContext) {
		
	    Method method = resourceInfo.getResourceMethod();
		
		String token = requestContext.getHeaderString("Authorization");

		RestricaoAcesso restricaoAcesso = method.getAnnotation(RestricaoAcesso.class);
		
		Acesso acesso = Acesso.ADMINISTRADOR;
		
		if(restricaoAcesso != null){
			acesso = restricaoAcesso.acesso();
		}
		
		if(acesso == Acesso.ANONIMO){
			return;
		}
		
		if ((token == null) || (token.isEmpty())) {
			throw new TokenInvalidoException("Permissão negada para acessar o recurso. Usuário não identificado.");
		}

		SessaoUsuario sessaoUsuario = getUsuarioFacade().findSessaoUsuarioByToken(token);
		if (sessaoUsuario == null) {
			throw new TokenInvalidoException();
		}

		Date dataAtual = new Date();
		if (sessaoUsuario.isExpirado(dataAtual)) {
			throw new SessaoExpiradaException();
		}

		Usuario usuario = sessaoUsuario.getUsuario();
		
		if (acesso == Acesso.ADMINISTRADOR && !usuario.getAdmin()) {
			throw new NaoAutorizadoException("Permissão insuficiente para acessar o recurso.");
		}

		atualizarDataDeExpiracao(sessaoUsuario, dataAtual);
	}

	private UsuarioFacade getUsuarioFacade() {
		if(usuarioFacade == null){
			try {
				usuarioFacade = InitialContext.doLookup("java:global/bolao-web/UsuarioFacade!org.javaee.bolao.usuario.UsuarioFacade");
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
		}
		return usuarioFacade;
	}

	private void atualizarDataDeExpiracao(SessaoUsuario sessaoUsuario, Date dataAtual) {
		long novaDataExpiracao = dataAtual.getTime() + BolaoConfig.getExpirationLoginTime();
		sessaoUsuario.setDataExpiracao(new Date(novaDataExpiracao));
		getUsuarioFacade().update(sessaoUsuario);
	}
}