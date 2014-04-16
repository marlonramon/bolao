package org.javaee.bolao.rest.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.javaee.bolao.eao.UserSessionEAO;
import org.javaee.bolao.eao.UserRoleEAO;
import org.javaee.bolao.entities.UserSession;
import org.javaee.bolao.exception.ErrorBean;

@Provider
public class SecurityInterceptor implements ContainerRequestFilter {

	public static final String AUTHORIZATION_PROPERTY = "Authorization";

	@Context
	private ResourceInfo resourceInfo;

	@Inject
	private UserSessionEAO userAuthorizationEAO;

	@Inject
	private UserRoleEAO userRoleEAO;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		try {

			String[] roles = getUserRoles(resourceInfo);

			//has Roles Annotation
			if (roles != null && roles.length > 0) {

				String token = requestContext.getHeaderString(AUTHORIZATION_PROPERTY);
				
				//has token
				if (token == null || token.isEmpty()) {
					throw new UnauthorizedException("Permission Denied");
				}
				
				UserSession userSession = userAuthorizationEAO.findByToken(token);
				//has session for the token
				if (userSession == null) {
					throw new UnauthorizedException("Invalid Token");
				}

				Date actualDate = new Date();
				//is session expirated
				if (userSession.isExpired(actualDate)) {
					throw new UnauthorizedException("Session Expired");
				}

				boolean isUserInRole = userRoleEAO.isUserInRoles(userSession.getUser(), roles);
				
				//is user in some role
				if (!isUserInRole) {
					throw new UnauthorizedException("Permission Denied");
				}
				
				updateExpiratedDate(userSession, actualDate);

			}

		} catch (UnauthorizedException ue) {
			requestContext.abortWith(Response.status(Status.UNAUTHORIZED).entity(new ErrorBean(ue.getMessage())).build());
		}

	}

	private String[] getUserRoles(ResourceInfo resourceInfo) {
		Method method = resourceInfo.getResourceMethod();
		RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
		String[] roles = rolesAllowed != null ? rolesAllowed.value() : null;
		return roles;
	}

	private void updateExpiratedDate(UserSession userSession, Date actualDate) {
		Date expirateDate = userSession.getExpirateDate();
		long newExpiratedDate = actualDate.getTime() + expirateDate.getTime();
		userSession.setExpirateDate(new Date(newExpiratedDate));
	}

}
