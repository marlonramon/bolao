package org.javaee.bolao.usuario;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.javaee.bolao.config.Config;
import org.javaee.bolao.eao.SessaoUsuarioEAO;
import org.javaee.bolao.eao.UsuarioBolaoEAO;
import org.javaee.bolao.eao.UsuarioEAO;
import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.exception.ErrorResponse;
import org.javaee.bolao.exception.NaoAutorizadoException;
import org.javaee.rest.common.Encryptor;

@Stateless
public class UsuarioFacade {

	@Inject
	private UsuarioEAO usuarioEAO;

	@Inject
	private SessaoUsuarioEAO sessaoUsuarioEAO;

	@Inject
	private UsuarioBolaoEAO usuarioBolaoEAO;

	public Usuario insertOrUpdate(Usuario user) {
		validarEmailDuplicado(user);

		criptografarSenha(user);

		if (!user.hasId())
			this.usuarioEAO.insert(user);
		else {
			this.usuarioEAO.update(user);
		}

		return user;
	}

	private void criptografarSenha(Usuario usuario) {
		if (usuario.hasId()) {
			if (isSenhaAlterada(usuario))
				usuario.setSenha(Encryptor.encryptPassword(usuario.getSenha()));
		} else
			usuario.setSenha(Encryptor.encryptPassword(usuario.getSenha()));
	}

	private boolean isSenhaAlterada(Usuario usuario) {
		Usuario usuarioDB = (Usuario) this.usuarioEAO.find(usuario);
		return !usuarioDB.getSenha().equals(usuario.getSenha());
	}

	public void validarEmailDuplicado(Usuario usuario) {
		Usuario userDB = this.usuarioEAO.findByEmail(usuario.getEmail());
		if ((userDB != null) && (!userDB.equals(usuario)))
			throw new WebApplicationException(Response
					.status(Response.Status.CONFLICT)
					.entity(new ErrorResponse(
							"Já existe um Usuário com o Email {0}",
							new Object[] { usuario.getEmail() })).build());
	}

	public void delete(Long id) {
		this.usuarioEAO.delete(id);
	}

	public Usuario find(Long id) {
		return (Usuario) this.usuarioEAO.find(id);
	}

	public List<Usuario> findAll() {
		return this.usuarioEAO.findAll();
	}

	public SessaoUsuario login(String email, String senha) {
		Usuario usuario = findByEmail(email);
		if (usuario == null) {
			throw new NaoAutorizadoException("Email e/ou senha inválido.");
		}

		if (!this.usuarioEAO.checkPassword(usuario, senha)) {
			throw new NaoAutorizadoException("Email e/ou senha inválido.");
		}

		Date actualDate = new Date();

		removeInvalidSessions(usuario, actualDate);

		SessaoUsuario validSession = this.sessaoUsuarioEAO.findSessaoValida(
				email, actualDate);

		if (validSession == null) {
			validSession = this.sessaoUsuarioEAO.create(usuario,
					Config.getExpirationLoginTime());
		}

		return validSession;
	}

	private void removeInvalidSessions(Usuario user, Date actualDate) {
		this.sessaoUsuarioEAO.deleteExpiratedSessions(user, actualDate);
	}

	public boolean logout(String email) {
		SessaoUsuario validSession = null;

		Date expirateDate = new Date();

		Usuario usuario = findByEmail(email);

		if (usuario != null) {
			validSession = this.sessaoUsuarioEAO.findSessaoValida(
					usuario.getEmail(), expirateDate);

			if (validSession != null) {
				validSession.setDataExpiracao(expirateDate);
			}
		}

		return validSession != null;
	}

	public Usuario findByEmail(String login) {
		return this.usuarioEAO.findByEmail(login);
	}

	public List<UsuarioBolao> findBoloes(Long idUsuario) {
		Usuario usuario = find(idUsuario);
		return usuarioBolaoEAO.finBoloes(usuario);
	}
}