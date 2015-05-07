package org.javaee.bolao.usuario;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.javaee.bolao.config.BolaoConfig;
import org.javaee.bolao.eao.BolaoEAO;
import org.javaee.bolao.eao.SessaoUsuarioEAO;
import org.javaee.bolao.eao.UsuarioEAO;
import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.exception.BolaoWebApplicationException;
import org.javaee.bolao.exception.TokenInvalidoException;
import org.javaee.bolao.usuariobolao.UsuarioBolaoFacade;
import org.javaee.bolao.util.Encryptor;

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

	public Usuario insertOrUpdate(Usuario usuario, String token) {

		validarEmailDuplicado(usuario);
		validarConfirmarSenha(usuario);
		criptografarSenha(usuario);

		if (!usuario.hasId()) {
			usuarioEAO.insert(usuario);
			vincularUsuarioBoloes(usuario);
		} else {
			validarAlteracao(usuario, token);
			usuarioEAO.update(usuario);
		}

		return usuario;
	}

	private void validarAlteracao(Usuario usuario, String token) {
		SessaoUsuario sessaoUsuario = findSessaoUsuarioByToken(token);

		if (sessaoUsuario == null) {
			throw new TokenInvalidoException();
		}

		Usuario usuarioLogado = sessaoUsuario.getUsuario();

		if (!usuario.equals(usuarioLogado)) {
			if (!usuarioLogado.isAdmin()) {
				throw new BolaoWebApplicationException("O Usuário Logado {0} não tem permissão para alterar dados de outro Usuário.", usuarioLogado.getNome());
			}
		}

	}

	private void vincularUsuarioBoloes(Usuario usuario) {
		List<Bolao> boloes = bolaoEAO.findAll();

		for (Bolao bolao : boloes) {
			usuarioBolaoFacade.vincularUsuarioBolao(usuario, bolao);
		}

	}

	private void criptografarSenha(Usuario usuario) {

		if (usuario.getSenha() != null) {
			if (usuario.hasId()) {
				if (isSenhaAlterada(usuario)) {
					usuario.setSenha(Encryptor.encryptPassword(usuario.getSenha()));
				}
			} else {
				usuario.setSenha(Encryptor.encryptPassword(usuario.getSenha()));
			}
		}
	}

	private boolean isSenhaAlterada(Usuario usuario) {
		Usuario usuarioDB = usuarioEAO.find(usuario);
		return !usuarioDB.getSenha().equals(usuario.getSenha());
	}

	public void validarEmailDuplicado(Usuario usuario) {
		Usuario userDB = usuarioEAO.findByEmail(usuario.getEmail());
		if ((userDB != null) && (!userDB.equals(usuario))) {
			throw new BolaoWebApplicationException("Já existe um Usuário com o Email {0}", usuario.getEmail());
		}
	}

	public void validarConfirmarSenha(Usuario usuario) {

		if (usuario.getSenha() != null) {
			if (!usuario.getSenha().equals(usuario.getConfirmarSenha())) {
				throw new BolaoWebApplicationException("A Senha não foi confirmada corretamente.");
			}
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

		if (email == null) {
			throw new BolaoWebApplicationException("Email é de preenchimento Obrigatório");
		}

		if (senha == null) {
			throw new BolaoWebApplicationException("Senha é de preenchimento Obrigatório");
		}

		Usuario usuario = findByEmail(email);
		if (usuario == null) {
			throw new BolaoWebApplicationException("Email e/ou senha inválido.");
		}

		if (!usuarioEAO.checkPassword(usuario, senha)) {
			throw new BolaoWebApplicationException("Email e/ou senha inválido.");
		}

		apagarSessoesDoUsuario(usuario);

		SessaoUsuario validSession = sessaoUsuarioEAO.create(usuario, BolaoConfig.getExpirationLoginTime());

		return validSession;
	}

	private void apagarSessoesDoUsuario(Usuario user) {
		sessaoUsuarioEAO.apagarSessoesDoUsuario(user);
	}

	public boolean logout(String email) {
		SessaoUsuario validSession = null;

		Usuario usuario = findByEmail(email);

		if (usuario != null) {
			apagarSessoesDoUsuario(usuario);
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

	public void trocarSenha() {

	}

	public SessaoUsuario findSessaoUsuarioByToken(String token) {
		return sessaoUsuarioEAO.findByToken(token);
	}

	public void update(SessaoUsuario sessaoUsuario) {
		sessaoUsuarioEAO.update(sessaoUsuario);
	}
}