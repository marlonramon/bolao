package org.javaee.bolao.rodada;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.javaee.bolao.eao.RodadaEAO;
import org.javaee.bolao.entidades.Campeonato;
import org.javaee.bolao.entidades.Rodada;
import org.javaee.bolao.exception.BolaoWebApplicationException;
import org.javaee.bolao.vo.RankingRodadaVO;

@Stateless
public class RodadaFacade {

	@Inject
	private RodadaEAO rodadaEAO;

	public Rodada insertOrUpdate(Rodada rodada) {

		validar(rodada);
		
		if (!rodada.hasId())
			rodadaEAO.insert(rodada);
		else {
			rodada = rodadaEAO.update(rodada);
		}

		atualizarRodadaAtual(rodada);
		
		return rodada;
	}

	private void atualizarRodadaAtual(Rodada rodada) {
		if(rodada.getRodadaAtual()){
			List<Rodada> rodadasAtuais = rodadaEAO.findRodadaAtualByCampeoanto(rodada.getCampeonato());
			
			for (Rodada rodadaAtual : rodadasAtuais) {
				if(!rodadaAtual.equals(rodada)){
					rodadaAtual.setRodadaAtual(Boolean.FALSE);
				}
			}
		}
	}

	private void validar(Rodada rodada) {
		validarNumeroDuplicado(rodada);
	}

	private void validarNumeroDuplicado(Rodada rodada) {
		
		Short numero = rodada.getNumero();
		
		Campeonato campeonato = rodada.getCampeonato();
		
		Rodada rodadaDB = rodadaEAO.findByCampeonatoAndNumero(campeonato, numero);
		
		if(rodadaDB != null && !rodadaDB.equals(rodada)){
			throw new BolaoWebApplicationException("Rodada número {0} já cadastrada para o campeonato {1}.", numero, campeonato.getDescricao());
		}
	}

	public void delete(Long id) {
		rodadaEAO.delete(id);
	}

	public Rodada find(Long id) {
		return rodadaEAO.find(id);
	}

	public List<Rodada> findAll() {
		return rodadaEAO.findAll();
	}

	public RankingRodadaVO ranking(Long id, int limite) {
		Rodada rodada = find(id);
		return rodadaEAO.ranking(rodada, limite);
	}
}