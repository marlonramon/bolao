package org.javaee.bolao.eao;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.javaee.bolao.entidades.Bolao_;
import org.javaee.bolao.entidades.Campeonato_;
import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.entidades.Rodada;
import org.javaee.bolao.entidades.Rodada_;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.UsuarioBolao_;
import org.javaee.bolao.entidades.Usuario_;
import org.javaee.bolao.util.Encryptor;

@ManagedBean
public class UsuarioEAO extends AbstractEAO<Usuario> {

	public UsuarioEAO() {
		super(Usuario.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public boolean checkPassword(Usuario usuario, String senha) {
		usuario = find(usuario);
		return Encryptor.checkPassword(senha, usuario.getSenha());
	}

	public Usuario findByEmail(String email) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Usuario> query = criteriaBuilder.createQuery(Usuario.class);

		Root<Usuario> root = query.from(Usuario.class);

		query = query.where(criteriaBuilder.equal(getCriteriaBuilder().upper(root.get(Usuario_.email)), email.toUpperCase()));

		TypedQuery<Usuario> q = getEntityManager().createQuery(query);

		return super.getSingleResult(q);
	}

	public Usuario findByNome(String nome) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Usuario> query = criteriaBuilder.createQuery(Usuario.class);

		Root<Usuario> root = query.from(Usuario.class);

		query = query.where(criteriaBuilder.equal(root.get(Usuario_.nome), nome));

		TypedQuery<Usuario> q = getEntityManager().createQuery(query);

		return super.getSingleResult(q);
	}

	public List<Usuario> findByPartida(Partida partida) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Usuario> query = criteriaBuilder.createQuery(Usuario.class);

		Root<Usuario> root = query.from(Usuario.class);

		SetJoin<Rodada, Partida> joinPartida = root.join(Usuario_.usuarioBoloes).join(UsuarioBolao_.bolao).join(Bolao_.campeonato).join(Campeonato_.rodadas)
				.join(Rodada_.partidas);

		query = query.where(criteriaBuilder.equal(joinPartida, partida));

		TypedQuery<Usuario> q = getEntityManager().createQuery(query);

		return q.getResultList();
	}

	public List<Usuario> findAdmins() {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Usuario> query = criteriaBuilder.createQuery(Usuario.class);

		Root<Usuario> root = query.from(Usuario.class);

		Predicate adminPredicate = criteriaBuilder.equal(root.get(Usuario_.admin), Boolean.TRUE);

		query = query.where(adminPredicate);

		TypedQuery<Usuario> q = getEntityManager().createQuery(query);

		return q.getResultList();
	}
}
