package org.javaee.bolao.eao;


import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javaee.bolao.entidades.Clube;


@ManagedBean
public class ClubeEAO extends AbstractEAO<Clube> {
	private static final long serialVersionUID = 1L;
        
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
