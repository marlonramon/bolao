package org.javaee.bolao.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.javaee.bolao.eao.SessaoUsuarioEAO;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario.PerfilUsuario;
import org.javaee.bolao.exception.ErrorResponse;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

	public static final String AUTHORIZATION_PROPERTY = "Authorization";

	@Context
	private ResourceInfo resourceInfo;

	@Inject
	private SessaoUsuarioEAO sessaoUsuarioEAO;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		try {

//			doFilter(requestContext);

		} catch (NaoAutorizadoException ue) {
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(new ErrorResponse(ue.getMessage())).build());
		}

	}

	private void doFilter(ContainerRequestContext requestContext) {
		List<PerfilUsuario> perfisAutorizados = getUserRoles(resourceInfo);

		if (!perfisAutorizados.isEmpty()) {

			String token = requestContext.getHeaderString(AUTHORIZATION_PROPERTY);
			
			if (token == null || token.isEmpty()) {
				throw new NaoAutorizadoException("Permissão negado para acessar o recurso.");
			}
			
			SessaoUsuario sessaoUsuario = sessaoUsuarioEAO.findByToken(token);
			if (sessaoUsuario == null) {
				throw new NaoAutorizadoException("Token de acesso inválido.");
			}

			Date dataAtual = new Date();
			if (sessaoUsuario.isExpirado(dataAtual)) {
				throw new NaoAutorizadoException("Sessão Expirada.");
			}

			boolean isUsuarioNoPerfil = perfisAutorizados.indexOf(sessaoUsuario.getUsuario().getPerfil()) != -1;
			
			if (!isUsuarioNoPerfil) {
				throw new NaoAutorizadoException("Permissão negado para acessar o recurso.");
			}
			
			atualizarDataDeExpiracao(sessaoUsuario, dataAtual);

		}
	}

	private List<PerfilUsuario> getUserRoles(ResourceInfo resourceInfo) {
		//TODO buscar da anotacao do metodo
		return Arrays.asList(PerfilUsuario.values());
	}

	private void atualizarDataDeExpiracao(SessaoUsuario sessaoUsuario, Date dataAtual) {
		Date dataExpiracao = sessaoUsuario.getDataExpiracao();
		long novaDataExpiracao = dataAtual.getTime() + dataExpiracao.getTime();
		sessaoUsuario.setDataExpiracao(new Date(novaDataExpiracao));
	}

}
