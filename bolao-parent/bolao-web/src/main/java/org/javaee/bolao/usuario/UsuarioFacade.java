package org.javaee.bolao.usuario;

import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.javaee.bolao.config.Config;
import org.javaee.bolao.eao.SessaoUsuarioEAO;
import org.javaee.bolao.eao.UsuarioEAO;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.exception.ErrorResponse;
import org.javaee.bolao.exception.NaoAutorizadoException;
import org.javaee.rest.common.Encryptor;

@ManagedBean
public class UsuarioFacade {
    
	@Inject
	private UsuarioEAO usuarioEAO;
	
    public UsuarioFacade() {
    }
    
    @Inject
	private SessaoUsuarioEAO sessaoUsuarioEAO ;

    public Usuario insertOrUpdate(Usuario user) {
    	
    	validarEmailDuplicado(user);
    	
    	criptografarSenha(user);
    	
    	if(!user.hasId()){
	    	usuarioEAO.insert(user);
    	}else{
    		usuarioEAO.update(user);
    	}
    	
    	return user;
    }

	private void criptografarSenha(Usuario usuario) {
		if(usuario.hasId()){
			if(isSenhaAlterada(usuario)){
				usuario.setSenha(Encryptor.encryptPassword(usuario.getSenha()));
			}
		}else{
			usuario.setSenha(Encryptor.encryptPassword(usuario.getSenha()));
		}
	}

	private boolean isSenhaAlterada(Usuario usuario) {
		Usuario usuarioDB = usuarioEAO.find(usuario);
		return !usuarioDB.getSenha().equals(usuario.getSenha());
	}
    
    public void validarEmailDuplicado(Usuario usuario){
    	Usuario userDB = usuarioEAO.findByEmail(usuario.getEmail());
    	if(userDB != null && !userDB.equals(usuario)){
    		throw new WebApplicationException(Response.status(Status.CONFLICT).entity(new ErrorResponse("Já existe um Usuário com o Email {0}", usuario.getEmail())).build());
    	}
    }

    public void delete(Long id) {
    	usuarioEAO.delete(id);
    }

    public Usuario find(Long id) {
    	return usuarioEAO.find(id);
    }

    public List<Usuario> findAll() {
    	return usuarioEAO.findAll();
    }

    public List<Usuario> findRange(Integer from, Integer to) {
        return usuarioEAO.findRange(new int[]{from, to});
    }

    public String count() {
        return String.valueOf(usuarioEAO.count());
    }
    
    public SessaoUsuario login(String email, String senha) {
    	
    	Usuario usuario = findByEmail(email);
    	if(usuario == null){
    		throw new NaoAutorizadoException("Email e/ou senha inválido.");
    	}
    	
    	if (!usuarioEAO.checkPassword(usuario, senha)) {
    		throw new NaoAutorizadoException("Email e/ou senha inválido.");
		}
    	
    	SessaoUsuario validSession = sessaoUsuarioEAO.findSessaoValida(email, new Date());
    	
    	if(validSession == null){
    		validSession = sessaoUsuarioEAO.create(usuario, Config.getExpirationLoginTime());
    	}
    	
    	return validSession;
    }
    
    public boolean logout(String email) {
    	Date expirateDate = new Date();
    	
    	Usuario usuario = findByEmail(email);
    	
		SessaoUsuario validSession = sessaoUsuarioEAO.findSessaoValida(usuario.getEmail(), expirateDate);
    	
		if(validSession != null){
    		validSession.setDataExpiracao(expirateDate);
    	}
		
		return validSession != null;
    }

	public Usuario findByEmail(String login) {
		return usuarioEAO.findByEmail(login);
	}
    
}
