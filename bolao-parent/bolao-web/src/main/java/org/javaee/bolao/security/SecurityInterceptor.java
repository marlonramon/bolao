package org.javaee.bolao.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.javaee.bolao.config.Config;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.exception.NaoAutorizadoException;
import org.javaee.bolao.usuario.UsuarioFacade;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {
	public static final String AUTHORIZATION_PROPERTY = "Authorization";

	@Context
	private ResourceInfo resourceInfo;

	@Inject
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
			throw new NaoAutorizadoException("Permissão negada para acessar o recurso.");
		}

		SessaoUsuario sessaoUsuario = usuarioFacade.findByToken(token);
		if (sessaoUsuario == null) {
			throw new NaoAutorizadoException("Token de acesso inválido.");
		}

		Date dataAtual = new Date();
		if (sessaoUsuario.isExpirado(dataAtual)) {
			throw new NaoAutorizadoException("Sessão Expirada. Faça login novamente.");
		}

		Usuario usuario = sessaoUsuario.getUsuario();
		
		if (acesso == Acesso.ADMINISTRADOR && !usuario.getAdmin()) {
			throw new NaoAutorizadoException("Permissão insuficiente para acessar o recurso.");
		}

		atualizarDataDeExpiracao(sessaoUsuario, dataAtual);
	}

	private void atualizarDataDeExpiracao(SessaoUsuario sessaoUsuario, Date dataAtual) {
		long novaDataExpiracao = dataAtual.getTime() + Config.getExpirationLoginTime();
		sessaoUsuario.setDataExpiracao(new Date(novaDataExpiracao));
		usuarioFacade.update(sessaoUsuario);
	}
}