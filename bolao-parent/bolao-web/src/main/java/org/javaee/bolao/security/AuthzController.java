package org.javaee.bolao.security;

import java.io.IOException;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.oltu.oauth2.common.message.types.ResponseType;
import org.apache.oltu.oauth2.common.token.OAuthToken;
import org.javaee.bolao.eao.SessaoUsuarioEAO;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.usuario.UsuarioFacade;
import org.json.JSONObject;

/**
 * Handles requests for the application welcome page.
 */

@Path("/oauth")
public class AuthzController {
	
	@Inject
	private UsuarioFacade usuarioFacade;

	public static final String GOOGLE = OAuthProviderType.GOOGLE.getProviderName();
	public static final String GOOGLE_AUTHZ = OAuthProviderType.GOOGLE.getAuthzEndpoint();
	public static final String GOOGLE_TOKEN = OAuthProviderType.GOOGLE.getTokenEndpoint();

	@Context
	private UriInfo uriInfo;
	
	@Inject
	private SessaoUsuarioEAO sessaoUsuarioEAO;

	@GET
	@Path("/authorize")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.ANONIMO)
	public Response authorize() throws IOException {

		try {
			String redirectURI = UriBuilder.fromUri(uriInfo.getBaseUri()).path("oauth/redirect").build().toString();

			OAuthClientRequest request = OAuthClientRequest.authorizationLocation(GOOGLE_AUTHZ)
					.setClientId(SecurityUtil.CLIENT_ID)
					.setRedirectURI(redirectURI)
					.setResponseType(ResponseType.CODE.toString())
					.setScope("openId profile email").setState("")
					.buildQueryMessage();

			return Response.seeOther(UriBuilder.fromUri(request.getLocationUri()).build()).build();

		} catch (Exception e) {
			return Response.serverError().build();
		}
	}

	@GET
	@Path("/redirect")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.ANONIMO)
	public Response handleRedirect(@Context HttpServletRequest request) {

		String redirectURI = UriBuilder.fromUri(uriInfo.getBaseUri()).path("oauth/redirect").build().toString();

		SessaoUsuario sessaoUsuario = new SessaoUsuario();

		try {

			OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);

			OAuthClientRequest req = OAuthClientRequest.tokenProvider(OAuthProviderType.GOOGLE)
					.setCode(oar.getCode()).setClientId(SecurityUtil.CLIENT_ID)
					.setClientSecret(SecurityUtil.CLIENT_SECRET)
					.setRedirectURI(UriBuilder.fromUri(redirectURI).build().toString())
					.setGrantType(GrantType.AUTHORIZATION_CODE).buildBodyMessage();

			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

			OAuthJSONAccessTokenResponse oAuthResponse = oAuthClient.accessToken(req);

			
			
			// Get the access token from the response
			OAuthToken accessToken = oAuthResponse.getOAuthToken();
			
			
			// Get User info using the following.
			final OAuthClientRequest bearerClientRequest = new OAuthBearerClientRequest("https://www.googleapis.com/oauth2/v1/userinfo?alt=json")
					.setAccessToken(accessToken.getAccessToken()).buildHeaderMessage();

			OAuthResourceResponse resourceResponse = oAuthClient.resource(bearerClientRequest, OAuth.HttpMethod.GET, OAuthResourceResponse.class);
			
			
			JSONObject jsonObject = new JSONObject(resourceResponse.getBody());
			
			Usuario usuario = findUsuario(jsonObject.getString("email"), jsonObject.getString("name"));
			
			
			
			sessaoUsuario = sessaoUsuarioEAO.create(usuario, accessToken.getExpiresIn());
			

		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

		return Response.ok(sessaoUsuario).build();

	}
	
	private Usuario findUsuario(String email, String nome) {
		
		Usuario usuario = usuarioFacade.findByEmail(email);
		
		 
		if (usuario == null) {
			usuarioFacade.insertOrUpdate(new Usuario(nome,email), null);
		}
		
		return usuario;
		
	}
	
	
	

	@GET
	@Path("/token")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.ANONIMO)
	public Response getToken(SessaoUsuario sessaoUsuario) {

		String redirectURI = UriBuilder.fromUri(uriInfo.getBaseUri()).path("oauth/redirect").build().toString();

		try {

			OAuthClientRequest request = OAuthClientRequest.tokenLocation(GOOGLE_AUTHZ).setClientId(SecurityUtil.CLIENT_ID)
					.setClientSecret(SecurityUtil.CLIENT_SECRET).setRedirectURI(redirectURI).setCode(sessaoUsuario.getCode())
					.setGrantType(GrantType.AUTHORIZATION_CODE).buildBodyMessage();

			OAuthClient client = new OAuthClient(new URLConnectionClient());

			OAuthAccessTokenResponse oauthResponse = null;
			Class<? extends OAuthAccessTokenResponse> cl = OAuthJSONAccessTokenResponse.class;

			oauthResponse = client.accessToken(request, cl);

			sessaoUsuario.setToken(oauthResponse.getAccessToken());
			sessaoUsuario.setDataExpiracao(new Date(oauthResponse.getExpiresIn()));

			return Response.ok(sessaoUsuario).build();

		} catch (Exception e) {
			e.printStackTrace();
			return Response.serverError().build();
		}

	}

}
