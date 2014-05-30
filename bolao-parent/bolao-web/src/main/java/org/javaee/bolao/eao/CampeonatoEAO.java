package org.javaee.bolao.eao;


import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javaee.bolao.entidades.Campeonato;


@ManagedBean
public class CampeonatoEAO extends AbstractEAO<Campeonato> {
	private static final long serialVersionUID = 1L;
        
	public CampeonatoEAO() {
		super(Campeonato.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

}
