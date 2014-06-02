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
import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.entidades.Bolao_;
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
import org.javaee.bolao.vo.RankingBolaoVO;

@ManagedBean
public class BolaoEAO extends AbstractEAO<Bolao> {

	public BolaoEAO() {
		super(Bolao.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public List<RankingBolaoVO> ranking(Bolao bolao, int limite) {

		CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
		CriteriaQuery<RankingBolaoVO> criteriaQuery = criteriaBuilder.createQuery(RankingBolaoVO.class);

		Root<Bolao> from = criteriaQuery.from(Bolao.class);

		Join<Bolao, Campeonato> joinCampeonato = from.join(Bolao_.campeonato);

		SetJoin<Campeonato, Rodada> joinRodada = joinCampeonato.join(Campeonato_.rodadas);

		SetJoin<Rodada, Partida> joinPartidas = joinRodada.join(Rodada_.partidas);

		Join<Partida, Placar> joinPlacarPartida = joinPartidas.join(Partida_.placar);

		SetJoin<Partida, Aposta> joinApostas = joinPartidas.join(Partida_.apostas);

		Join<Aposta, UsuarioBolao> joinUsuarioBolao = joinApostas.join(Aposta_.usuarioBolao);

		Join<UsuarioBolao, Usuario> joinUsuario = joinUsuarioBolao.join(UsuarioBolao_.usuario);

		Expression<Integer> sumPontuacao = criteriaBuilder.sum(joinApostas.get(Aposta_.pontuacao));

		CompoundSelection<RankingBolaoVO> compoundSelection = criteriaBuilder.construct(RankingBolaoVO.class, 
				from.get(Bolao_.idBolao),
				joinUsuario.get(Usuario_.idUsuario), 
				joinUsuario.get(Usuario_.nome), 
				sumPontuacao);

		criteriaQuery.select(compoundSelection);

		Predicate equalBolao = criteriaBuilder.equal(from, bolao);

		Predicate placarMandanteNotNull = criteriaBuilder.isNotNull(joinPlacarPartida.get(Placar_.placarMandante));
		Predicate placarVisitanteNotNull = criteriaBuilder.isNotNull(joinPlacarPartida.get(Placar_.placarVisitante));

		criteriaQuery.where(equalBolao, placarMandanteNotNull, placarVisitanteNotNull);

		criteriaQuery.groupBy(joinUsuario.get(Usuario_.idUsuario));

		criteriaQuery.orderBy(getCriteriaBuilder().desc(sumPontuacao));

		TypedQuery<RankingBolaoVO> typedQuery = getEntityManager().createQuery(criteriaQuery);

		if (limite > 0) {
			typedQuery.setMaxResults(limite);
		}

		return typedQuery.getResultList();

	}

}
