package org.javaee.bolao.eao;

import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.entidades.Partida_;
import org.javaee.bolao.entidades.Rodada;

@ManagedBean
public class PartidaEAO extends AbstractEAO<Partida> {

	public PartidaEAO() {
		super(Partida.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public List<Partida> findByRodada(Rodada rodada) {
		
		CriteriaQuery<Partida> cq = super.createCriteriaQuery();
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		
		Root<Partida> from = cq.from(Partida.class);
		
		Join<Partida, Rodada> joinRodada = from.join(Partida_.rodada);
		
		cq.where(criteriaBuilder.equal(joinRodada, rodada));
		cq.orderBy(criteriaBuilder.asc(from.get(Partida_.dataPartida)));
		
		return createQuery(cq).getResultList();
	}
	
	public List<Partida> findPartidasPosteriores(Date data){
		
		CriteriaQuery<Partida> cq = super.createCriteriaQuery();
		
		Root<Partida> from = cq.from(Partida.class);
		
		cq.where(getCriteriaBuilder().greaterThanOrEqualTo(from.get(Partida_.dataPartida), data));
		
		return createQuery(cq).getResultList();
		
	}

}
