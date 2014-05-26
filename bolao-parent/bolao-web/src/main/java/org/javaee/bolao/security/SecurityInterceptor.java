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
import javax.ws.rs.ext.Provider;
import org.javaee.bolao.eao.SessaoUsuarioEAO;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.Usuario.PerfilUsuario;
import org.javaee.bolao.exception.NaoAutorizadoException;

@Provider
public class SecurityInterceptor
  implements ContainerRequestFilter
{
  public static final String AUTHORIZATION_PROPERTY = "Authorization";

  @Context
  private ResourceInfo resourceInfo;

  @Inject
  private SessaoUsuarioEAO sessaoUsuarioEAO;

  public void filter(ContainerRequestContext requestContext)
    throws IOException
  {
  }

  private void doFilter(ContainerRequestContext requestContext)
  {
    List perfisAutorizados = getUserRoles(this.resourceInfo);

    if (perfisAutorizados.isEmpty())
      return;
    String token = requestContext.getHeaderString("Authorization");

    if ((token == null) || (token.isEmpty())) {
      throw new NaoAutorizadoException("Permissão negado para acessar o recurso.");
    }

    SessaoUsuario sessaoUsuario = this.sessaoUsuarioEAO.findByToken(token);
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

  private List<Usuario.PerfilUsuario> getUserRoles(ResourceInfo resourceInfo)
  {
    return Arrays.asList(Usuario.PerfilUsuario.values());
  }

  private void atualizarDataDeExpiracao(SessaoUsuario sessaoUsuario, Date dataAtual) {
    Date dataExpiracao = sessaoUsuario.getDataExpiracao();
    long novaDataExpiracao = dataAtual.getTime() + dataExpiracao.getTime();
    sessaoUsuario.setDataExpiracao(new Date(novaDataExpiracao));
  }
}