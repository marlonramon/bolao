package org.javaee.bolao.aposta;

import java.util.ArrayList;
import java.util.Date;
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
import org.javaee.bolao.exception.BolaoWebApplicationException;
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

	public void gravar(List<Aposta> apostas) {
		
		for (Aposta aposta : apostas) {
			
			if(isApostaComAomEnosUmPlacarPreenchido(aposta)){
				inserirOuAtualizar(aposta);
			}else{
				excluirSeExistir(aposta);
			}
		}
		
	}

	private void excluirSeExistir(Aposta aposta) {
		if(aposta.hasId()){
			apostaEAO.delete(aposta);
		}
	}

	private void inserirOuAtualizar(Aposta aposta) {
		Partida partida = findPartida(aposta);
		
		validarPlacar(partida, aposta.getPlacar());
		
		if(!aposta.hasId()){
			aposta.setDataAposta(new Date());
			validarApostaNova(partida, aposta);
			apostaEAO.insert(aposta);
		}else{
			apostaEAO.update(aposta);
		}
	}
	
	private boolean isApostaComAomEnosUmPlacarPreenchido(Aposta aposta){
		Placar placar = aposta.getPlacar();
		return placar != null && placar.isAoMenosUmPlacarPreenchido();
	}
	
	private void validarPlacar(Partida partida, Placar placar){
		if(placar.getPlacarMandante() == null){
			throw new BolaoWebApplicationException("O Placar do Time mandante da Partida {0} não foi preenchido.", partida.getDescricao());
		}
		
		if(placar.getPlacarVisitante() == null){
			throw new BolaoWebApplicationException("O Placar do Time visitante da Partida {0} não foi preenchido.", partida.getDescricao());
		}
	}
	
	private Partida findPartida(Aposta aposta){
		return partidaEAO.find(aposta.getPartida());
	}
	
	private void validarApostaNova(Partida partida, Aposta aposta) {
		Date dataPartida = partida.getDataPartida();
		Date dataAposta = aposta.getDataAposta();
		if(dataPartida.compareTo(dataAposta) < 0){
			throw new BolaoWebApplicationException("Não foi possível cadastar a Aposta pois a data da Aposta {0,date} é supeior a data da Partida {1,date}", dataAposta, dataPartida);
		}
		
	}
}
