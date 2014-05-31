package org.javaee.bolao.eao;


import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javaee.bolao.entidades.Rodada;


@ManagedBean
public class RodadaEAO extends AbstractEAO<Rodada> {
	private static final long serialVersionUID = 1L;
        
	public RodadaEAO() {
		super(Rodada.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
