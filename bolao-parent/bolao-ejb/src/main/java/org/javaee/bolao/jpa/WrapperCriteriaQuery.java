package org.javaee.bolao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.javaee.bolao.entities.AbstractEntity;

public class WrapperCriteriaQuery<E extends AbstractEntity> {
	
	private CriteriaBuilder criteriaBuilder;
	
	private CriteriaQuery<E> criteriaQuery;
	
	private Root<E> root; 

	private EntityManager em;
	
	public WrapperCriteriaQuery(EntityManager em, Class<E> entityClass) {		
		this.criteriaBuilder = em.getCriteriaBuilder();
		this.em = em;
		
		criteriaQuery = criteriaBuilder.createQuery(entityClass);
		
		root = criteriaQuery.from(entityClass);
	}
	
	public <T> Predicate equal(SingularAttribute<E, T> attribute, T value){
		return criteriaBuilder.equal(root.get(attribute), value);
	}
	
//	public <T> Predicate equal(PluralAttribute<E, ?, T> attribute, T value){
//		return criteriaBuilder.equal(root.get(attribute), value);
//	}
	
	public List<E> getResultList(){
		return em.createQuery(criteriaQuery).getResultList();
	}
	
	public E getSingleList(){
		try{
			return em.createQuery(criteriaQuery).getSingleResult();
		}catch(NoResultException nre){
			return null;
		}
	}

	public CriteriaQuery<E> where(Predicate... restrictions) {
		return criteriaQuery.where(restrictions);
	}

	
	
}
