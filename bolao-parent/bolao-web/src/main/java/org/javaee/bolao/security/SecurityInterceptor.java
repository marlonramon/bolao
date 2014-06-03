package org.javaee.bolao.security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {
	public static final String AUTHORIZATION_PROPERTY = "Authorization";

	@Context
	private ResourceInfo resourceInfo;

//	@Inject
//	private SessaoUsuarioEAO sessaoUsuarioEAO;

	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("Authorization: " + requestContext.getHeaderString("Authorization"));
	}

//	private void doFilter(ContainerRequestContext requestContext) {
//		
//		List<PerfilUsuario> perfisAutorizados = getUserRoles(this.resourceInfo);
//
//		if (perfisAutorizados.isEmpty())
//			return;
//		String token = requestContext.getHeaderString("Authorization");
//
//		if ((token == null) || (token.isEmpty())) {
//			throw new NaoAutorizadoException("Permissão negado para acessar o recurso.");
//		}
//
//		SessaoUsuario sessaoUsuario = this.sessaoUsuarioEAO.findByToken(token);
//		if (sessaoUsuario == null) {
//			throw new NaoAutorizadoException("Token de acesso inválido.");
//		}
//
//		Date dataAtual = new Date();
//		if (sessaoUsuario.isExpirado(dataAtual)) {
//			throw new NaoAutorizadoException("Sessão Expirada.");
//		}
//
//		boolean isUsuarioNoPerfil = perfisAutorizados.indexOf(sessaoUsuario.getUsuario().getPerfil()) != -1;
//
//		if (!isUsuarioNoPerfil) {
//			throw new NaoAutorizadoException("Permissão negado para acessar o recurso.");
//		}
//
//		atualizarDataDeExpiracao(sessaoUsuario, dataAtual);
//	}
//
//	private List<PerfilUsuario> getUserRoles(ResourceInfo resourceInfo) {
//		return Arrays.asList(Usuario.PerfilUsuario.values());
//	}
//
//	private void atualizarDataDeExpiracao(SessaoUsuario sessaoUsuario, Date dataAtual) {
//		Date dataExpiracao = sessaoUsuario.getDataExpiracao();
//		long novaDataExpiracao = dataAtual.getTime() + dataExpiracao.getTime();
//		sessaoUsuario.setDataExpiracao(new Date(novaDataExpiracao));
//	}
}