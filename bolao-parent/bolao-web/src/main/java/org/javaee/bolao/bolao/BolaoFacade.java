package org.javaee.bolao.bolao;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.javaee.bolao.eao.BolaoEAO;
import org.javaee.bolao.eao.UsuarioEAO;
import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.usuariobolao.UsuarioBolaoFacade;
import org.javaee.bolao.vo.RankingBolaoVO;

@Stateless
public class BolaoFacade {

	@Inject
	private BolaoEAO bolaoEAO;

	@Inject
	private UsuarioEAO usuarioEAO;
	
	@Inject
	private UsuarioBolaoFacade usuarioBolaoFacade;

	public Bolao insertOrUpdate(Bolao bolao) {

		if (!bolao.hasId()) {
			bolaoEAO.insert(bolao);
			vincularBolaoUsuarios(bolao);
		} else {
			bolaoEAO.update(bolao);
		}

		return bolao;
	}

	private void vincularBolaoUsuarios(Bolao bolao) {
		List<Usuario> usuarios = usuarioEAO.findAll();
		
		for (Usuario usuario : usuarios) {
			usuarioBolaoFacade.vincularUsuarioBolao(usuario, bolao);
		}
		
	}

	public void delete(Long id) {
		bolaoEAO.delete(id);
	}

	public Bolao find(Long id) {
		return bolaoEAO.find(id);
	}

	public List<Bolao> findAll() {
		return bolaoEAO.findAll();
	}

	public RankingBolaoVO ranking(Long id, Integer limite) {
		Bolao bolao = bolaoEAO.find(id);
		return bolaoEAO.ranking(bolao, limite);
	}
	
	public List<Bolao> findByUsuario(Long idUsuario) {
		List<Bolao> boloes = bolaoEAO.findAll();
		Usuario usuario = usuarioEAO.find(idUsuario);
		
		for (Bolao bolao : boloes) {
			bolao.setUsuarioVinculado(usuarioBolaoFacade.isUsuarioVinculadoBolao(usuario, bolao));
		}
		
		return boloes;
	}
	
}