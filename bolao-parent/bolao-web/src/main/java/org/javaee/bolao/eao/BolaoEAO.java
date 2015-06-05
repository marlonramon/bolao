package org.javaee.bolao.eao;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
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
import org.javaee.bolao.entidades.Rodada;
import org.javaee.bolao.entidades.Rodada_;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.entidades.UsuarioBolao_;
import org.javaee.bolao.entidades.Usuario_;
import org.javaee.bolao.vo.RankingBolaoVO;
import org.javaee.bolao.vo.RankingUsuarioVO;

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
	
	public List<Bolao> findByUsuario(Long idUsuario) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select bolao from Bolao bolao where bolao not in (select bolao from Bolao bolao ");
		sb.append(" inner join bolao.usuariosBolao usuariosBolao");
		sb.append(" where usuariosBolao.usuario.idUsuario = :idUsuario)");
		
		
		TypedQuery<Bolao> query = getEntityManager().createQuery(sb.toString(),Bolao.class);
		
		query.setParameter("idUsuario", idUsuario);
		
		return query.getResultList();
		
	}

	public RankingBolaoVO ranking(Bolao bolao, int limite) {

		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = cb.createTupleQuery();

		Root<Bolao> from = criteriaQuery.from(Bolao.class);

		Join<Bolao, Campeonato> joinCampeonato = from.join(Bolao_.campeonato);

		SetJoin<Campeonato, Rodada> joinRodada = joinCampeonato.join(Campeonato_.rodadas);

		SetJoin<Rodada, Partida> joinPartidas = joinRodada.join(Rodada_.partidas);

		Join<Partida, Placar> joinPlacarPartida = joinPartidas.join(Partida_.placar);

		SetJoin<Partida, Aposta> joinApostas = joinPartidas.join(Partida_.apostas);

		Join<Aposta, UsuarioBolao> joinUsuarioBolao = joinApostas.join(Aposta_.usuarioBolao);

		Join<UsuarioBolao, Usuario> joinUsuario = joinUsuarioBolao.join(UsuarioBolao_.usuario);

		Expression<Integer> sumPontuacao = cb.sum(joinApostas.get(Aposta_.pontuacao));

		Path<Long> idUsuarioPath = joinUsuarioBolao.get(UsuarioBolao_.idUsuarioBolao);
		Path<String> nomeUsuarioPath = joinUsuario.get(Usuario_.nome);
		
		CompoundSelection<Tuple> tupleSelection = cb.tuple(idUsuarioPath, nomeUsuarioPath, sumPontuacao);
		
		criteriaQuery.select(tupleSelection);

		Predicate equalBolao = cb.equal(from, bolao);

		criteriaQuery.where(equalBolao);

		criteriaQuery.groupBy(idUsuarioPath);

		criteriaQuery.orderBy(getCriteriaBuilder().desc(sumPontuacao));

		TypedQuery<Tuple> typedQuery = getEntityManager().createQuery(criteriaQuery);

		if (limite > 0) {
			typedQuery.setMaxResults(limite);
		}

		RankingBolaoVO rankingBolaoVO = new RankingBolaoVO();
		
		rankingBolaoVO.setIdBolao(bolao.getIdBolao());
		rankingBolaoVO.setDescricao(bolao.getDescricao());
		
		List<Tuple> resultList = typedQuery.getResultList();
		
		for (Tuple tuple : resultList) {
			RankingUsuarioVO rankingUsuarioVO = new RankingUsuarioVO();
			rankingUsuarioVO.setIdUsuarioBolao(tuple.get(idUsuarioPath));
			rankingUsuarioVO.setNome(tuple.get(nomeUsuarioPath));
			rankingUsuarioVO.setPontuacao(tuple.get(sumPontuacao));
			
			rankingBolaoVO.addUsuario(rankingUsuarioVO);
		}
		
		return rankingBolaoVO;
	}

}
