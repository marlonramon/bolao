package org.javaee.bolao.eao;


import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javaee.bolao.entidades.Bolao;


@ManagedBean
public class BolaoEAO extends AbstractEAO<Bolao> {
	private static final long serialVersionUID = 1L;
        
	public BolaoEAO() {
		super(Bolao.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
