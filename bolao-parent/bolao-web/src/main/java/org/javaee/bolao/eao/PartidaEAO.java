package org.javaee.bolao.eao;


import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javaee.bolao.entidades.Partida;


@ManagedBean
public class PartidaEAO extends AbstractEAO<Partida> {
	private static final long serialVersionUID = 1L;
        
	public PartidaEAO() {
		super(Partida.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
