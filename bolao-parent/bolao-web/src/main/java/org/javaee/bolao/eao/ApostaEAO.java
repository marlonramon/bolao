package org.javaee.bolao.eao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
		
		from.fetch(Aposta_.usuarioBolao);
		
		Join<Aposta, Partida> joinPartida = from.join(Aposta_.partida);
		Join<Aposta, UsuarioBolao> joinUsuarioBolao = from.join(Aposta_.usuarioBolao);
		
		Predicate equalsPartida = getCriteriaBuilder().equal(joinPartida, partida);
		Predicate equalsUsuarioBolao = getCriteriaBuilder().equal(joinUsuarioBolao, usuarioBolao);
		
		cq.where(equalsPartida, equalsUsuarioBolao);
		
		return super.getSingleResult(cq);
	}
	
    public List<Aposta> findByPartida(Partida partida){
		
		
		CriteriaQuery<Aposta> cq = super.createCriteriaQuery();
		
		Root<Aposta> from = cq.from(Aposta.class);
		
		from.fetch(Aposta_.usuarioBolao);
		
		Join<Aposta, Partida> joinPartida = from.join(Aposta_.partida);
		
		Predicate equalsPartida = getCriteriaBuilder().equal(joinPartida, partida);
		
		cq.where(equalsPartida);
		
		return getEntityManager().createQuery(cq).getResultList();
	}
    
    public List<Aposta> findApostaFinalizada(UsuarioBolao usuarioBolao){
		
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("select aposta from Aposta aposta ");
    	sb.append(" join fetch aposta.usuarioBolao usuarioBolao ");
    	sb.append(" join fetch aposta.partida partida ");
    	sb.append(" join fetch partida.clubeMandante ");
    	sb.append(" join fetch partida.clubeVisitante ");
    	sb.append(" where usuarioBolao = :usuarioBolao ");
    	sb.append(" and partida.dataPartida < :dataAtual ");
    	
    	TypedQuery<Aposta> tQuery = getEntityManager().createQuery(sb.toString(),Aposta.class);
    	
    	tQuery.setParameter("usuarioBolao", usuarioBolao);
    	tQuery.setParameter("dataAtual", new Date());
    	
		return tQuery.getResultList();
	}
    
    
	
}
