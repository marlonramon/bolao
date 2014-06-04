package org.javaee.bolao.aposta;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.javaee.bolao.eao.ApostaEAO;
import org.javaee.bolao.eao.PartidaEAO;
import org.javaee.bolao.eao.RodadaEAO;
import org.javaee.bolao.eao.UsuarioBolaoEAO;
import org.javaee.bolao.entidades.Aposta;
import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.entidades.Placar;
import org.javaee.bolao.entidades.Rodada;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.vo.ApostaVO;

@Stateless
public class ApostaFacade {
	@Inject
	private ApostaEAO apostaEAO;

	@Inject
	private PartidaEAO partidaEAO;

	@Inject
	private UsuarioBolaoEAO usuarioBolaoEAO;
	
	@Inject
	private RodadaEAO rodadaEAO;
	
	public List<Aposta> findApostas(Long idUsuarioBolao, Long idRodada) {
		
		Rodada rodada = rodadaEAO.find(idRodada);
		
		UsuarioBolao usuarioBolao = usuarioBolaoEAO.find(idUsuarioBolao);
		
		return findApostas(usuarioBolao, rodada);
	}
	
	public List<Aposta> findApostas(UsuarioBolao usuarioBolao, Rodada rodada) {
		List<Partida> partidas = partidaEAO.findByRodada(rodada);
		return createApostaList(usuarioBolao, partidas);
	}

	private List<Aposta> createApostaList(UsuarioBolao usuarioBolao, List<Partida> partidas) {
		List<Aposta> apostas = new ArrayList<>();

		for (Partida partida : partidas) {
			Aposta aposta = apostaEAO.findByPartida(usuarioBolao, partida);
			
			if(aposta == null){
				aposta = createAposta(partida, usuarioBolao);
			}
			
			apostas.add(aposta);
		}

		return apostas;
	}

	private Aposta createAposta(Partida partida, UsuarioBolao usuarioBolao) {
		Aposta aposta = new Aposta();
		aposta.setPartida(partida);
		aposta.setPlacar(new Placar());
		aposta.setUsuarioBolao(usuarioBolao);
		
		return aposta;
	}

	public List<ApostaVO> persitApostas(List<ApostaVO> apostaList) {
		return null;
	}
}
