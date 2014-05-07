package org.javaee.bolao.eao;


import java.util.Arrays;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.javaee.bolao.entities.User;
import org.javaee.bolao.entities.UserRole;
import org.javaee.bolao.entities.UserRole_;


@ManagedBean
public class UserRoleEAO extends AbstractEAO<UserRole> {
	private static final long serialVersionUID = 1L;
        
	public UserRoleEAO() {
		super(UserRole.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public boolean isUserInRoles(User user, String userRole[]){
		
		CriteriaQuery<UserRole> criteriaQuery = createCriteriaQuery();
		
		Root<UserRole> from = criteriaQuery.from(UserRole.class);
		
		Predicate equalUser = getCriteriaBuilder().equal(from.get(UserRole_.user), user);
		
		Predicate inUserRole = from.get(UserRole_.role).in(Arrays.asList(userRole));
		
		criteriaQuery.where(equalUser, inUserRole);
		
		TypedQuery<UserRole> typedQuery = createQuery(criteriaQuery);
		
		return super.getSingleResult(typedQuery) != null;
	}

}
