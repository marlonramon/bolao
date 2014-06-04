package org.javaee.bolao.eao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.javaee.bolao.entidades.Aposta;
import org.javaee.bolao.entidades.Aposta_;
import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.entidades.UsuarioBolao;

public class ApostaEAO extends AbstractEAO<Aposta>
{
	public ApostaEAO() {
		super(Aposta.class);
	}
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public Aposta findByPartida(UsuarioBolao usuarioBolao, Partida partida){
		
		
		CriteriaQuery<Aposta> cq = super.createCriteriaQuery();
		
		Root<Aposta> from = cq.from(Aposta.class);
		
		Join<Aposta, Partida> joinPartida = from.join(Aposta_.partida);
		
		Join<Aposta, UsuarioBolao> joinUsuarioBolao = from.join(Aposta_.usuarioBolao);
		
		Predicate equalsPartida = getCriteriaBuilder().equal(joinPartida, partida);
		Predicate equalsUsuarioBolao = getCriteriaBuilder().equal(joinUsuarioBolao, usuarioBolao);
		
		cq.where(equalsPartida, equalsUsuarioBolao);
		
		return super.getSingleResult(cq);
	}
	
}
