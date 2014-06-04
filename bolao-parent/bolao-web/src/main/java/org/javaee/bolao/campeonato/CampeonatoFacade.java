package org.javaee.bolao.campeonato;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.javaee.bolao.eao.CampeonatoEAO;
import org.javaee.bolao.eao.RodadaEAO;
import org.javaee.bolao.entidades.Campeonato;
import org.javaee.bolao.entidades.Rodada;

@Stateless
public class CampeonatoFacade {

	@Inject
	private CampeonatoEAO campeonatoEAO;

	@Inject
	private RodadaEAO rodadaEAO;
	
	public Campeonato insertOrUpdate(Campeonato campeonato) {

		if (!campeonato.hasId())
			campeonatoEAO.insert(campeonato);
		else {
			campeonatoEAO.update(campeonato);
		}

		return campeonato;
	}

	public void delete(Long id) {
		campeonatoEAO.delete(id);
	}

	public Campeonato find(Long id) {
		return campeonatoEAO.find(id);
	}

	public List<Campeonato> findAll() {
		return campeonatoEAO.findAll();
	}

	public List<Rodada> findRodadas(Long idCampeonato) {
		return rodadaEAO.findByCampeonato(idCampeonato);
	}

	public int count() {
		return campeonatoEAO.count();
	}
}