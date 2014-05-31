package org.javaee.bolao.eao;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.entidades.Rodada;
import org.javaee.bolao.entidades.UsuarioBolao;

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

	public List<Partida> findPartida(UsuarioBolao usuario, Rodada rodada)
	{
		TypedQuery<Partida> query = createQueryPartidaAposta();
		query.setParameter("usuario", usuario);
		query.setParameter("rodada", rodada);
		return query.getResultList();
	}

	private TypedQuery<Partida> createQueryPartidaAposta() {
		String sql = " select ";
		sql += "         p ";
		sql += "       from ";
		sql += "         Partida p ";
		sql += "         left join fetch p.apostas apostas ";
		sql += "       where ";
		sql += "         p.rodada = :rodada ";
		sql += "         and apostas.usuarioBolao = :usuario ";

		return createTypedQuery(sql);
	}
}
