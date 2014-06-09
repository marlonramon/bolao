package org.javaee.bolao.usuariobolao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.javaee.bolao.eao.BolaoEAO;
import org.javaee.bolao.eao.UsuarioBolaoEAO;
import org.javaee.bolao.eao.UsuarioEAO;
import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.UsuarioBolao;

@Stateless
public class UsuarioBolaoFacade {

	@Inject
	private UsuarioBolaoEAO usuarioBolaoEAO;
	
	@Inject
	private UsuarioEAO usuarioEAO;
	
	@Inject
	private BolaoEAO bolaoEAO;

	public void delete(Long id) {
		usuarioBolaoEAO.delete(id);
	}

	public UsuarioBolao find(Long id) {
		return usuarioBolaoEAO.find(id);
	}

	public List<UsuarioBolao> findAll() {
		return this.usuarioBolaoEAO.findAll();
	}

	public boolean vincularUsuarioBolao(Long idUsuario, Long idBolao) {
		
		Usuario usuario = usuarioEAO.find(idUsuario);
		
		Bolao bolao = bolaoEAO.find(idBolao);
		
		return vincularUsuarioBolao(usuario, bolao);
	}

	public boolean vincularUsuarioBolao(Usuario usuario, Bolao bolao) {
		if(!isUsuarioVinculadoBolao(usuario, bolao)){
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

	public List<UsuarioBolao> findAll(Long idUsuario) {
		
		List<UsuarioBolao> usuarioBoloes = new ArrayList<>();
		
		Usuario usuario = usuarioEAO.find(idUsuario);
				
		List<Bolao> boloes = bolaoEAO.findAll();
		
		for (Bolao bolao : boloes) {
			
			UsuarioBolao usuarioBolao = usuarioBolaoEAO.findByUsuarioAndBolao(usuario, bolao);
			if(usuarioBolao == null){
				usuarioBolao = new UsuarioBolao();
				usuarioBolao.setBolao(bolao);
				usuarioBolao.setUsuario(usuario);
			}
			
			usuarioBoloes.add(usuarioBolao);
		}
		
		
		return usuarioBoloes;
	}

	public List<UsuarioBolao> findBoloesByUsuario(Usuario usuario) {
		return usuarioBolaoEAO.findBoloesByUsuario(usuario);
	}
}