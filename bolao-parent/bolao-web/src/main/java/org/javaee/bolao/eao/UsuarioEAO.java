package org.javaee.bolao.eao;


import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.Usuario_;
import org.javaee.rest.common.Encryptor;


@ManagedBean
public class UsuarioEAO extends AbstractEAO<Usuario> {
	private static final long serialVersionUID = 1L;
        
	public UsuarioEAO() {
		super(Usuario.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public boolean checkPassword(Usuario user, String senha) {
		user = find(user);
		return Encryptor.checkPassword(senha, user.getSenha());
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
	
//	public List<User> findAdminActive(User notIn) {
//		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
//
//		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
//
//		Root<User> root = query.from(User.class);
//
//		Predicate different = criteriaBuilder.notEqual(root, notIn);
//		Predicate adminProfile = criteriaBuilder.equal(root.get(Usuario_.profile), Profile.ADMIN);
//		
//		query = query.where(active, different, adminProfile);
//
//		TypedQuery<User> q = getEntityManager().createQuery(query);
//
//		return q.getResultList();
//	}
}
