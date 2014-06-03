package org.javaee.bolao.rodada;

import java.text.MessageFormat;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.javaee.bolao.eao.RodadaEAO;
import org.javaee.bolao.entidades.Campeonato;
import org.javaee.bolao.entidades.Rodada;
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
			rodadaEAO.update(rodada);
		}

		return rodada;
	}

	private void validar(Rodada rodada) {
		validarNumeroDuplicado(rodada);
	}

	private void validarNumeroDuplicado(Rodada rodada) {
		
		Short numero = rodada.getNumero();
		
		Campeonato campeonato = rodada.getCampeonato();
		
		Rodada rodadaDB = rodadaEAO.findByCampeonatoAndNumero(campeonato, numero);
		
		if(rodadaDB != null){
			throw new WebApplicationException(MessageFormat.format("Rodada número {0} já cadastrada para o campeonato {1}.", numero, campeonato.getDescricao()), Response.Status.CONFLICT);
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

	public List<RankingRodadaVO> ranking(Long id, int limite) {
		
		Rodada rodada = find(id);
		
		return rodadaEAO.ranking(rodada, limite);
	}
}