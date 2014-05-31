package org.javaee.bolao.eao;


import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javaee.bolao.entidades.Clube;


@ManagedBean
public class ClubeEAO extends AbstractEAO<Clube> {
        
	public ClubeEAO() {
		super(Clube.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
