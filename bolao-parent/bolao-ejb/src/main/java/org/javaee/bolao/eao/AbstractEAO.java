package org.javaee.bolao.eao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.javaee.bolao.entities.AbstractEntity;

public abstract class AbstractEAO<E extends AbstractEntity> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Logger logger = Logger.getLogger(getClass().getName());

    protected Class<E> entityClass;

    public AbstractEAO(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract EntityManager getEntityManager();

    public void insert(E entity) {
    	logger.log(Level.INFO, "Insert: {0}", entity);
    	getEntityManager().persist(entity);
    }

    public void delete(Long id) {
    	logger.log(Level.INFO, "Delete: {0}", id);
    	if (id != null) {
            E entity = getEntityManager().getReference(entityClass, id);
            getEntityManager().remove(entity);
        }
    }

    public void delete(E entity) {
        delete(entity.getId());
    }

    public E update(E entity) {
    	logger.log(Level.INFO, "Update: {0}", entity);
        return getEntityManager().merge(entity);
    }

    public E find(Long id) {
        logger.log(Level.INFO, "Find: {0}", id);
        if (id == null) {
            return null;
        }
        return getEntityManager().find(entityClass, id);
    }

    public E find(E entity) {
        if (entity == null || entity.getId() == null) {
            return null;
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
        
        logger.log(Level.INFO, "FindAll: {0}", resultList.size());
        
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

    public E createIntance() {
        try {
            return entityClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao instanciar a classe: " + entityClass.getSimpleName(), e);
        }
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
}
