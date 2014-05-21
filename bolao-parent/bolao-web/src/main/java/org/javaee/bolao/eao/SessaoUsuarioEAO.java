package org.javaee.bolao.eao;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.SessaoUsuario_;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.Usuario_;

public class SessaoUsuarioEAO extends AbstractEAO<SessaoUsuario>{
	private static final long serialVersionUID = 1L;

	public SessaoUsuarioEAO() {
		super(SessaoUsuario.class);
	}

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public SessaoUsuario findByToken(String token){
		CriteriaQuery<SessaoUsuario> criteriaQuery = createCriteriaQuery();
		
		Root<SessaoUsuario> from = criteriaQuery.from(SessaoUsuario.class);
		
		criteriaQuery.where(getCriteriaBuilder().equal(from.get(SessaoUsuario_.token), token));
		
		return createQuery(criteriaQuery).getSingleResult();
	}
	
	public SessaoUsuario findSessaoValida(String email, Date date){
		
		TypedQuery<SessaoUsuario> query = super.getNamedQuery("SessaoUsuario.findSessaoValida");
		
		if(query == null){
		
			CriteriaQuery<SessaoUsuario> criteriaQuery = createCriteriaQuery();
			
			Root<SessaoUsuario> from = criteriaQuery.from(SessaoUsuario.class);
			
			Join<SessaoUsuario, Usuario> joinUser = from.join(SessaoUsuario_.usuario);
			
			Predicate equalLogin = getCriteriaBuilder().equal(joinUser.get(Usuario_.email), email);
			
			ParameterExpression<Date> dateParameter = getCriteriaBuilder().parameter(Date.class, "date");
			
			Predicate between = getCriteriaBuilder().between(dateParameter, from.get(SessaoUsuario_.dataCadastro), from.get(SessaoUsuario_.dataExpiracao));
			
			criteriaQuery.where(equalLogin, between);
			
			query = createQuery(criteriaQuery);
			
			super.addNamedQuery("SessaoUsuario.findSessaoValida", query);
		
		}
		
		query.setParameter("date", date);
		
		return super.getSingleResult(query);
	}
	
	public SessaoUsuario create(Usuario user,  long expirationTimeMillis){
		SessaoUsuario userSession = new SessaoUsuario();
		userSession.setToken(createToken());
		
		userSession.setUsuario(user);
		
		long currentTimeMillis = System.currentTimeMillis();
		
		userSession.setDataCadastro(new Date(currentTimeMillis));
		userSession.setDataExpiracao(new Date(currentTimeMillis + expirationTimeMillis));
		
		super.insert(userSession);
		
		return userSession;
	}
	
	public String createToken() {
		return UUID.randomUUID().toString();
	}

}
