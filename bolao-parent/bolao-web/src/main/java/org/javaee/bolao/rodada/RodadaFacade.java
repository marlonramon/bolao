package org.javaee.bolao.rodada;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.javaee.bolao.eao.RodadaEAO;
import org.javaee.bolao.entidades.Rodada;
import org.javaee.bolao.vo.RankingRodadaVO;

@ManagedBean
public class RodadaFacade {

	@Inject
	private RodadaEAO rodadaEAO;

	public Rodada insertOrUpdate(Rodada rodada) {

		if (!rodada.hasId())
			rodadaEAO.insert(rodada);
		else {
			rodadaEAO.update(rodada);
		}

		return rodada;
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