package org.javaee.bolao.eao;


import java.util.List;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.javaee.bolao.entidades.Aposta;
import org.javaee.bolao.entidades.Aposta_;
import org.javaee.bolao.entidades.Campeonato;
import org.javaee.bolao.entidades.Campeonato_;
import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.entidades.Partida_;
import org.javaee.bolao.entidades.Placar;
import org.javaee.bolao.entidades.Placar_;
import org.javaee.bolao.entidades.Rodada;
import org.javaee.bolao.entidades.Rodada_;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.entidades.UsuarioBolao_;
import org.javaee.bolao.entidades.Usuario_;
import org.javaee.bolao.vo.RankingRodadaVO;


@ManagedBean
public class RodadaEAO extends AbstractEAO<Rodada> {
        
	public RodadaEAO() {
		super(Rodada.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public List<RankingRodadaVO> ranking(Rodada rodada, int limite){
		
		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<RankingRodadaVO> criteriaQuery = criteriaBuilder.createQuery(RankingRodadaVO.class);
		
		Root<Rodada> from = criteriaQuery.from(Rodada.class);
		
		SetJoin<Rodada, Partida> joinPartidas = from.join(Rodada_.partidas);
		
		Join<Partida, Placar> joinPlacarPartida = joinPartidas.join(Partida_.placar);
		
		SetJoin<Partida, Aposta> joinApostas = joinPartidas.join(Partida_.apostas);
		
		Join<Aposta, UsuarioBolao> joinUsuarioBolao = joinApostas.join(Aposta_.usuarioBolao);
		
		Join<UsuarioBolao, Usuario> joinUsuario = joinUsuarioBolao.join(UsuarioBolao_.usuario);
		
		Expression<Integer> sumPontuacao = criteriaBuilder.sum(joinApostas.get(Aposta_.pontuacao));
		
		CompoundSelection<RankingRodadaVO> compoundSelection = criteriaBuilder.construct(RankingRodadaVO.class, 
				from.get(Rodada_.idRodada), 
				from.get(Rodada_.numero), 
				joinUsuario.get(Usuario_.idUsuario),
				joinUsuario.get(Usuario_.nome),
				sumPontuacao);
		
		criteriaQuery.select(compoundSelection);
		
		Predicate equalRodada = criteriaBuilder.equal(from, rodada);
		
		Predicate placarMandanteNotNull = criteriaBuilder.isNotNull(joinPlacarPartida.get(Placar_.placarMandante));
		Predicate placarVisitanteNotNull = criteriaBuilder.isNotNull(joinPlacarPartida.get(Placar_.placarVisitante));
		
		criteriaQuery.where(equalRodada, placarMandanteNotNull, placarVisitanteNotNull);
		
		criteriaQuery.groupBy(joinUsuario.get(Usuario_.idUsuario));
		
		criteriaQuery.orderBy(getCriteriaBuilder().desc(sumPontuacao));
		
		TypedQuery<RankingRodadaVO> typedQuery = getEntityManager().createQuery(criteriaQuery);
		
		if(limite > 0){
			typedQuery.setMaxResults(limite);
		}
		
		return typedQuery.getResultList();
		
	}
	
	public Rodada findByCampeonatoAndNumero(Campeonato campeonato, Short numero){
		CriteriaQuery<Rodada> criteriaQuery = super.createCriteriaQuery();
		
		Root<Rodada> from = criteriaQuery.from(Rodada.class);
		
		Join<Rodada, Campeonato> joinCampeonato = from.join(Rodada_.campeonato);
		
		Predicate equalCampeonato = getCriteriaBuilder().equal(joinCampeonato, campeonato);
		
		Predicate equalNumero = getCriteriaBuilder().equal(from.get(Rodada_.numero), numero);
		
		criteriaQuery.where(equalCampeonato, equalNumero);
		
		return super.getSingleResult(criteriaQuery);
		
	}

	public List<Rodada> findByCampeonato(Long idCampeonato) {
		
		CriteriaQuery<Rodada> criteriaQuery = super.createCriteriaQuery();
		
		Root<Rodada> from = criteriaQuery.from(Rodada.class);
		
		Join<Rodada, Campeonato> joinCampeonato = from.join(Rodada_.campeonato);
		
		Predicate equalIdCampeonato = getCriteriaBuilder().equal(joinCampeonato.get(Campeonato_.idCampeonato), idCampeonato);
		
		criteriaQuery.where(equalIdCampeonato);
		
		criteriaQuery.orderBy(getCriteriaBuilder().asc(from.get(Rodada_.numero)));
		
		return super.createQuery(criteriaQuery).getResultList();
	}
	
}
