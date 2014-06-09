package org.javaee.bolao.usuario;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.javaee.bolao.config.Config;
import org.javaee.bolao.eao.BolaoEAO;
import org.javaee.bolao.eao.SessaoUsuarioEAO;
import org.javaee.bolao.eao.UsuarioEAO;
import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.exception.BolaoWebApplicationException;
import org.javaee.bolao.exception.NaoAutorizadoException;
import org.javaee.bolao.usuariobolao.UsuarioBolaoFacade;
import org.javaee.rest.common.Encryptor;

@Stateless
public class UsuarioFacade {

	@Inject
	private UsuarioEAO usuarioEAO;

	@Inject
	private SessaoUsuarioEAO sessaoUsuarioEAO;

	@Inject
	private UsuarioBolaoFacade usuarioBolaoFacade;

	@Inject
	private BolaoEAO bolaoEAO;

	public Usuario insertOrUpdate(Usuario usuario) {
		validarEmailDuplicado(usuario);
		validarConfirmarSenha(usuario);
		criptografarSenha(usuario);

		if (!usuario.hasId()){
			usuarioEAO.insert(usuario);
			vincularUsuarioBoloes(usuario);		
		}else {
			usuarioEAO.update(usuario);
		}

		return usuario;
	}

	private void vincularUsuarioBoloes(Usuario usuario) {
		List<Bolao> boloes = bolaoEAO.findAll();
		
		for (Bolao bolao : boloes) {
			usuarioBolaoFacade.vincularUsuarioBolao(usuario, bolao);
		}
		
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
		Usuario userDB = usuarioEAO.findByEmail(usuario.getEmail());
		if ((userDB != null) && (!userDB.equals(usuario))){
			throw new BolaoWebApplicationException("Já existe um Usuário com o Email {0}", usuario.getEmail());
		}
	}

	public void validarConfirmarSenha(Usuario usuario) {
		if (!usuario.getSenha().equals(usuario.getConfirmarSenha())){
			throw new BolaoWebApplicationException("A Senha não foi confirmada corretamente.");
		}
	}
	
	public void delete(Long id) {
		usuarioEAO.delete(id);
	}

	public Usuario find(Long id) {
		return (Usuario) usuarioEAO.find(id);
	}

	public List<Usuario> findAll() {
		return usuarioEAO.findAll();
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

		if (!usuarioEAO.checkPassword(usuario, senha)) {
			throw new NaoAutorizadoException("Email e/ou senha inválido.");
		}

		Date actualDate = new Date();

		removeInvalidSessions(usuario, actualDate);

		SessaoUsuario validSession = sessaoUsuarioEAO.findSessaoValida(email, actualDate);

		if (validSession == null) {
			validSession = sessaoUsuarioEAO.create(usuario, Config.getExpirationLoginTime());
		}

		return validSession;
	}

	private void removeInvalidSessions(Usuario user, Date actualDate) {
		sessaoUsuarioEAO.deleteExpiratedSessions(user, actualDate);
	}

	public boolean logout(String email) {
		SessaoUsuario validSession = null;

		Date expirateDate = new Date();

		Usuario usuario = findByEmail(email);

		if (usuario != null) {
			validSession = sessaoUsuarioEAO.findSessaoValida(usuario.getEmail(), expirateDate);

			if (validSession != null) {
				validSession.setDataExpiracao(expirateDate);
			}
		}

		return validSession != null;
	}

	public Usuario findByEmail(String login) {
		return usuarioEAO.findByEmail(login);
	}

	public List<UsuarioBolao> findBoloes(Long idUsuario) {
		Usuario usuario = find(idUsuario);
		return usuarioBolaoFacade.findBoloesByUsuario(usuario);
	}

}