package org.javaee.bolao.eao;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.javaee.bolao.entidades.IEntity;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.exception.BolaoRuntimeException;
import org.javaee.rest.common.XmlUtil;

public abstract class AbstractEAO<E extends IEntity>
{
	protected Logger logger = Logger.getLogger(getClass().getName());

	protected Class<E> entityClass;

	public AbstractEAO(Class<E> entityClass) {
		this.entityClass = entityClass;
	}

	public abstract EntityManager getEntityManager();

	public void insert(E entity) {

		logger.log(Level.INFO, "Insert {0}: {1}", new Object[]{entityClass.getSimpleName(), XmlUtil.toString(entity, true)});
		try{
			getEntityManager().persist(entity);
		}catch(ConstraintViolationException e){
			for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
				StringBuilder sb = new StringBuilder();
				sb.append("ConstraintViolationException");
				sb.append("\nInvalidValue: "+constraintViolation.getInvalidValue());
				sb.append("\nProperty: "+constraintViolation.getPropertyPath());
				sb.append("\nMessage: "+constraintViolation.getMessage());
				logger.severe(sb.toString());
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new BolaoRuntimeException(e);
		}
	}

//	private RuntimeException convertException(Exception e) {
//		
//		RuntimeException re = new RuntimeException(e);
//		
//		if(e instanceof ConstraintViolationException){
//			ConstraintViolationException  cve = (ConstraintViolationException) e;
//			Set<ConstraintViolation<?>> constraintViolations = cve.getConstraintViolations();
//			for (ConstraintViolation<?> constraintViolation : constraintViolations) {
//				StringBuilder sb = new StringBuilder();
//				sb.append("ConstraintViolationException");
//				sb.append("\nInvalidValue: "+constraintViolation.getInvalidValue());
//				sb.append("\nProperty: "+constraintViolation.getPropertyPath());
//				sb.append("\nMessage: "+constraintViolation.getMessage());
//				logger.severe(sb.toString());
//			}
//		}
//		
//		
//		return re;
//	}

	public void delete(Long id) {
		logger.log(Level.INFO, "Delete {0} Id: {1}", new Object[]{entityClass.getSimpleName(), id});
		if (id != null) {
			E entity = getEntityManager().getReference(entityClass, id);
			getEntityManager().remove(entity);
		}
	}

	public void delete(E entity) {
		delete(entity.getId());
	}

	public E update(E entity) {
		logger.log(Level.INFO, "Update {0}: {1}", new Object[]{entityClass.getSimpleName(), XmlUtil.toString(entity, true)});
		try{
			System.out.println("akiiiiiiiiiiiiiiiiiiiiiiiii");
			
			E merge = getEntityManager().merge(entity);
			getEntityManager().flush();
			return merge;
		}catch(ConstraintViolationException e){
			e.printStackTrace();
			System.out.println(" CONTRANINSFASFASDFA  " + e.getConstraintViolations().size());
			for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
				StringBuilder sb = new StringBuilder();
				sb.append("ConstraintViolationException");
				sb.append("\nInvalidValue: "+constraintViolation.getInvalidValue());
				sb.append("\nProperty: "+constraintViolation.getPropertyPath());
				sb.append("\nMessage: "+constraintViolation.getMessage());
				logger.severe(sb.toString());
			}
			//throw new BolaoRuntimeException(e);
			
			return null;
		}catch(Exception e){
			System.out.println("tretaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			e.printStackTrace();
			throw new BolaoRuntimeException(e);
		}
	}

	public E find(Long id) {
		logger.log(Level.INFO, "Find {0} Id: {1}", new Object[]{entityClass.getSimpleName(), id});
		if (id == null) {
			throw new BolaoRuntimeException("ID não pode ser NULL");
		}
		
		E entity = getEntityManager().find(entityClass, id);
		
		if(entity == null){
			throw new BolaoRuntimeException("Não foi possível localizar a entidade {0} para o ID {1}", entityClass.getSimpleName(), id);
		}
		
		return entity;
	}

	public E find(E entity) {
		if (entity == null) {
			throw new BolaoRuntimeException("Entidade não pode ser NULL");
		}
		return find(entity.getId());
	}

	protected TypedQuery<E> createTypedQuery(String sql) {
		return getEntityManager().createQuery(sql, entityClass);
	}

	protected TypedQuery<E> createQuery(CriteriaQuery<E> criteriaQuery) {
		return getEntityManager().createQuery(criteriaQuery);
	}

	protected Query createQuery(CriteriaUpdate<E> criteriaUpdate) {
		return getEntityManager().createQuery(criteriaUpdate);
	}

	public List<E> createQuery(String sql, Object... params) {
		TypedQuery<E> query = getEntityManager().createQuery(sql, entityClass);

		int i = 1;

		if (params != null) {
			for (Object param : params) {
				query.setParameter(i++, param);
			}
		}

		return query.getResultList();
	}

	public List<E> findAll() {
		CriteriaQuery<E> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(entityClass);

		Root<E> root = criteriaQuery.from(entityClass);
		criteriaQuery = criteriaQuery.select(root);
		TypedQuery<E> query = getEntityManager().createQuery(criteriaQuery);

		List<E> resultList = query.getResultList();

		logger.log(Level.INFO, "FindAll {0} Total: {1}", new Object[]{entityClass.getSimpleName(), resultList.size()});

		return resultList;
	}

	public List<E> findRange(int[] range) {
		CriteriaQuery<E> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
		cq.select(cq.from(entityClass));
		TypedQuery<E> q = createQuery(cq);
		q.setMaxResults(range[1] - range[0] + 1);
		q.setFirstResult(range[0]);
		return q.getResultList();
	}

	public List<E> findBetween(SingularAttribute<E, ? extends Date> dateAttribute, Date startDate, Date endDate) {

		CriteriaQuery<E> criteriaQuery = getEntityManager().getCriteriaBuilder().createQuery(entityClass);

		Root<E> root = criteriaQuery.from(entityClass);
		criteriaQuery = criteriaQuery.select(root);

		Path<? extends Date> pathDate = root.get(dateAttribute);

		Predicate betweenDate = getCriteriaBuilder().between(pathDate, startDate, endDate);

		criteriaQuery.where(betweenDate);

		TypedQuery<E> query = getEntityManager().createQuery(criteriaQuery);

		return query.getResultList();
	}

	protected CriteriaQuery<E> createCriteriaQuery() {
		return getCriteriaBuilder().createQuery(entityClass);
	}

	protected CriteriaUpdate<E> createCriteriaUpdateQuery() {
		return getCriteriaBuilder().createCriteriaUpdate(entityClass);
	}

	protected CriteriaBuilder getCriteriaBuilder() {
		return getEntityManager().getCriteriaBuilder();
	}

	public int count() {

		CriteriaBuilder qb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(entityClass)));

		int count = getEntityManager().createQuery(cq).getSingleResult().intValue();

		logger.log(Level.INFO, "Count: {0}", count);

		return count;
	}

	public List<E> paginator(int firstResult, int maxResults) {
		return paginator(firstResult, maxResults, null, null);
	}

	public List<E> paginator(int firstResult, int maxResults, SingularAttribute<E, ?> attributeOrder, Boolean sortOrder) {

		CriteriaQuery<E> cq = createCriteriaQuery();
		Root<E> from = cq.from(entityClass);
		cq = cq.select(from);

		if (sortOrder != null) {
			Order order;
			if (sortOrder) {
				order = getCriteriaBuilder().asc(from.get(attributeOrder));
			} else {
				order = getCriteriaBuilder().desc(from.get(attributeOrder));
			}

			cq = cq.orderBy(order);
		}

		TypedQuery<E> query = createQuery(cq);

		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);

		return query.getResultList();
	}

	public List<E> paginatorLike(int firstResult, int maxResult, Map<String, String> filters, Boolean ascending, String sortField) {

		CriteriaQuery<E> criteriaQuery = createCriteriaQuery();

		Root<E> from = criteriaQuery.from(entityClass);

		if (filters != null) {

			Predicate[] predicate = new Predicate[filters.size()];

			int i = 0;

			for (Entry<String, String> filter : filters.entrySet()) {

				Expression<String> expression = from.get(filter.getKey()).as(String.class);

				predicate[i++] = getCriteriaBuilder().like(getCriteriaBuilder().lower(expression), "%" + filter.getValue().toString().toLowerCase() + "%");

			}

			if (predicate.length > 0) {
				criteriaQuery.where(getCriteriaBuilder().and(predicate));
			}
		}

		if (sortField != null) {
			if (ascending != null) {
				criteriaQuery.orderBy(ascending ? getCriteriaBuilder().asc(from.get(sortField)) : getCriteriaBuilder().desc(from.get(sortField)));
			}
		}

		return createQuery(criteriaQuery).setMaxResults(maxResult).setFirstResult(firstResult).getResultList();

	}

	public E getSingleResult(TypedQuery<E> query) {
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public void flush() {
		getEntityManager().flush();
	}

	public TypedQuery<E> getNamedQuery(String nameQuery) {
		try {
			return getEntityManager().createNamedQuery(nameQuery, entityClass);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}

	private EntityManagerFactory getEntityManagerFactory() {
		return getEntityManager().getEntityManagerFactory();
	}

	public void addNamedQuery(String namedQuery, TypedQuery<E> typedQuery) {
		getEntityManagerFactory().addNamedQuery(namedQuery, typedQuery);
	}

	protected Query createQuery(CriteriaDelete<SessaoUsuario> criteriaDelete) {
		return getEntityManager().createQuery(criteriaDelete);
	}

	protected CriteriaDelete<E> createCriteriaDelete() {
		return getCriteriaBuilder().createCriteriaDelete(entityClass);
	}

	public E getSingleResult(CriteriaQuery<E> criteriaQuery) {
		return getSingleResult(createQuery(criteriaQuery));
	}
}
