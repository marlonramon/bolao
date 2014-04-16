package org.javaee.bolao.eao;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.javaee.bolao.entities.User;
import org.javaee.bolao.entities.UserSession;
import org.javaee.bolao.entities.UserSession_;
import org.javaee.bolao.entities.User_;

public class UserSessionEAO extends AbstractEAO<UserSession>{
	private static final long serialVersionUID = 1L;

	public UserSessionEAO() {
		super(UserSession.class);
	}

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public UserSession findByToken(String authorization){
		CriteriaQuery<UserSession> criteriaQuery = createCriteriaQuery();
		
		Root<UserSession> from = criteriaQuery.from(UserSession.class);
		
		criteriaQuery.where(getCriteriaBuilder().equal(from.get(UserSession_.token), authorization));
		
		return createQuery(criteriaQuery).getSingleResult();
	}
	
	public UserSession findValidSession(String userLogin, Date date){
		CriteriaQuery<UserSession> criteriaQuery = createCriteriaQuery();
		
		Root<UserSession> from = criteriaQuery.from(UserSession.class);
		
		Join<UserSession, User> joinUser = from.join(UserSession_.user);
		
		Predicate equalLogin = getCriteriaBuilder().equal(joinUser.get(User_.login), userLogin);
		
		ParameterExpression<Date> dateParameter = getCriteriaBuilder().parameter(Date.class, "date");
		
		Predicate between = getCriteriaBuilder().between(dateParameter, from.get(UserSession_.createDate), from.get(UserSession_.expirateDate));
		
		criteriaQuery.where(equalLogin, between);
		
		return createQuery(criteriaQuery).setParameter("date", date).getSingleResult();
	}
	
	public UserSession create(User user,  long expirationTimeMillis){
		UserSession userSession = new UserSession();
		userSession.setToken(createToken());
		
		userSession.setUser(user);
		
		long currentTimeMillis = System.currentTimeMillis();
		
		userSession.setCreateDate(new Date(currentTimeMillis));
		userSession.setExpirateDate(new Date(currentTimeMillis + expirationTimeMillis));
		
		super.insert(userSession);
		
		return userSession;
	}
	
	public String createToken() {
		return UUID.randomUUID().toString();
	}

}
