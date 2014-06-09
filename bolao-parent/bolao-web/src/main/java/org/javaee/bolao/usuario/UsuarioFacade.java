package org.javaee.bolao.usuario;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.javaee.bolao.config.Config;
import org.javaee.bolao.eao.BolaoEAO;
import org.javaee.bolao.eao.SessaoUsuarioEAO;
import org.javaee.bolao.eao.UsuarioBolaoEAO;
import org.javaee.bolao.eao.UsuarioEAO;
import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.exception.BolaoWebApplicationException;
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

	@Inject
	private BolaoEAO bolaoEAO;

	public Usuario insertOrUpdate(Usuario user) {
		validarEmailDuplicado(user);
		validarConfirmarSenha(user);
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
		Usuario usuarioDB = usuarioEAO.find(usuario);
		return !usuarioDB.getSenha().equals(usuario.getSenha());
	}

	public void validarEmailDuplicado(Usuario usuario) {
		Usuario userDB = this.usuarioEAO.findByEmail(usuario.getEmail());
		if ((userDB != null) && (!userDB.equals(usuario))){
			throw new BolaoWebApplicationException("Já existe um Usuário com o Email {0}", userDB.getEmail());
		}
	}

	public void validarConfirmarSenha(Usuario usuario) {
		if (!usuario.getSenha().equals(usuario.getConfirmarSenha())){
			throw new BolaoWebApplicationException("A Senha não foi confirmada corretamente.");
		}
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
		
		if(email == null){
			throw new BolaoWebApplicationException("Email é de preenchimento Obrigatório");
		}
		
		if(senha == null){
			throw new BolaoWebApplicationException("Senha é de preenchimento Obrigatório");
		}
		
		Usuario usuario = findByEmail(email);
		if (usuario == null) {
			throw new NaoAutorizadoException("Email e/ou senha inválido.");
		}

		if (!this.usuarioEAO.checkPassword(usuario, senha)) {
			throw new NaoAutorizadoException("Email e/ou senha inválido.");
		}

		Date actualDate = new Date();

		removeInvalidSessions(usuario, actualDate);

		SessaoUsuario validSession = this.sessaoUsuarioEAO.findSessaoValida(email, actualDate);

		if (validSession == null) {
			validSession = this.sessaoUsuarioEAO.create(usuario, Config.getExpirationLoginTime());
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
			validSession = this.sessaoUsuarioEAO.findSessaoValida(usuario.getEmail(), expirateDate);

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

	public boolean vincularUsuarioBolao(Long idUsuario, Long idBolao) {

		Usuario usuario = usuarioEAO.find(idUsuario);

		Bolao bolao = bolaoEAO.find(idBolao);

		if (!isUsuarioVinculadoBolao(usuario, bolao)) {
			UsuarioBolao usuarioBolao = new UsuarioBolao();
			usuarioBolao.setUsuario(usuario);
			usuarioBolao.setBolao(bolao);

			usuarioBolaoEAO.insert(usuarioBolao);

			return true;
		}

		return false;
	}

	private boolean isUsuarioVinculadoBolao(Usuario usuario, Bolao bolao) {
		return usuarioBolaoEAO.findByUsuarioAndBolao(usuario, bolao) != null;
	}
}