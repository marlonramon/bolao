package org.javaee.bolao.eao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.javaee.bolao.entidades.Aposta;

public class ApostaEAO extends AbstractEAO<Aposta>
{
	public ApostaEAO() {
		super(Aposta.class);
	}
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
}
