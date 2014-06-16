package org.javaee.bolao.eao;


import java.util.List;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
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
import org.javaee.bolao.vo.RankingUsuarioVO;


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

	public RankingRodadaVO ranking(Rodada rodada, int limite){
		
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = cb.createTupleQuery();
		
		Root<Rodada> from = criteriaQuery.from(Rodada.class);
		
		SetJoin<Rodada, Partida> joinPartidas = from.join(Rodada_.partidas);
		
		Join<Partida, Placar> joinPlacarPartida = joinPartidas.join(Partida_.placar);
		
		SetJoin<Partida, Aposta> joinApostas = joinPartidas.join(Partida_.apostas, JoinType.LEFT);
		
		Join<Aposta, UsuarioBolao> joinUsuarioBolao = joinApostas.join(Aposta_.usuarioBolao, JoinType.LEFT);
		
		Join<UsuarioBolao, Usuario> joinUsuario = joinUsuarioBolao.join(UsuarioBolao_.usuario, JoinType.LEFT);
		
		Expression<Integer> sumPontuacao = cb.sum(joinApostas.get(Aposta_.pontuacao));
		
		Path<Long> idRodadaPath = from.get(Rodada_.idRodada);
		Path<Short> numeroRodadaPath = from.get(Rodada_.numero);
		Path<Long> idUsuarioPath = joinUsuario.get(Usuario_.idUsuario);
		Path<String> nomeUsuarioPath = joinUsuario.get(Usuario_.nome);
		
		criteriaQuery.select(cb.tuple(idRodadaPath, numeroRodadaPath, idUsuarioPath, nomeUsuarioPath, sumPontuacao));
		
		Predicate equalRodada = cb.equal(from, rodada);
		
		Predicate placarMandanteNotNull = cb.isNotNull(joinPlacarPartida.get(Placar_.placarMandante));
		Predicate placarVisitanteNotNull = cb.isNotNull(joinPlacarPartida.get(Placar_.placarVisitante));
		
		criteriaQuery.where(equalRodada, placarMandanteNotNull, placarVisitanteNotNull);
		
		criteriaQuery.groupBy(idUsuarioPath);
		
		criteriaQuery.orderBy(getCriteriaBuilder().desc(sumPontuacao));
		
		TypedQuery<Tuple> typedQuery = getEntityManager().createQuery(criteriaQuery);
		
		if(limite > 0){
			typedQuery.setMaxResults(limite);
		}
		
		RankingRodadaVO rankingRodadaVO = new RankingRodadaVO();
		rankingRodadaVO.setIdRodada(rodada.getIdRodada());
		rankingRodadaVO.setNumero(rodada.getNumero());
		rankingRodadaVO.setCampeonato(rodada.getCampeonato().getDescricao());
		
		List<Tuple> resultList = typedQuery.getResultList();
		
		for (Tuple tuple : resultList) {
			RankingUsuarioVO rankingUsuarioVO = new RankingUsuarioVO();
			rankingUsuarioVO.setIdUsuarioBolao(tuple.get(idUsuarioPath));
			rankingUsuarioVO.setNome(tuple.get(nomeUsuarioPath));
			rankingUsuarioVO.setPontuacao(tuple.get(sumPontuacao));
			
			rankingRodadaVO.addUsuario(rankingUsuarioVO);
		}
		
		return rankingRodadaVO;
		
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
	
	public List<Rodada> findRodadaAtualByCampeoanto(Campeonato campeonato){
		
		CriteriaQuery<Rodada> cq = createCriteriaQuery();
		
		Root<Rodada> from = cq.from(Rodada.class);
		
		CriteriaBuilder cb = getCriteriaBuilder();
		
		Predicate equalCampeonato = cb.equal(from.get(Rodada_.campeonato), campeonato);
		
		cq.where(equalCampeonato);
		
		return super.createQuery(cq).getResultList();
		
	}
	
}
