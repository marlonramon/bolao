package org.javaee.bolao.eao;


import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.javaee.bolao.entities.User;
import org.javaee.bolao.entities.User_;
import org.javaee.rest.common.Encryptor;


@ManagedBean
public class UserEAO extends AbstractEAO<User> {
	private static final long serialVersionUID = 1L;
        
	public UserEAO() {
		super(User.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public User isVallid(String login, String password) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<User> cq = cb.createQuery(User.class);

		Root<User> root = cq.from(User.class);

		Predicate predicateLogin = cb.equal(root.get(User_.login), login);

		cq = cq.where(predicateLogin);

		TypedQuery<User> query = getEntityManager().createQuery(cq);

		try {
			User userDB = query.getSingleResult();
			if (checkPassword(userDB, password)) {
				return userDB;
			}

			return null;
		} catch (NoResultException e) {
			return null;
		}

	}

	public boolean checkPassword(User user, String password) {
		user = find(user);
		return Encryptor.checkPassword(password, user.getPassword());
	}

	public User findByLogin(String login) {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();

		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

		Root<User> root = query.from(User.class);

		query = query.where(criteriaBuilder.equal(root.get(User_.login), login));

		TypedQuery<User> q = getEntityManager().createQuery(query);

		return super.getSingleResult(q);
	}

	public User findByAuthorization(String authorization) {
		//TODO
		return null;
	}
	
//	public List<User> findAdminActive(User notIn) {
//		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
//
//		CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
//
//		Root<User> root = query.from(User.class);
//
//		Predicate different = criteriaBuilder.notEqual(root, notIn);
//		Predicate adminProfile = criteriaBuilder.equal(root.get(User_.profile), Profile.ADMIN);
//		
//		query = query.where(active, different, adminProfile);
//
//		TypedQuery<User> q = getEntityManager().createQuery(query);
//
//		return q.getResultList();
//	}
}
