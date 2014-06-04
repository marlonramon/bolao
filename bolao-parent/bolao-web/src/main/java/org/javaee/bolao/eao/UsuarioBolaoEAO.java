package org.javaee.bolao.eao;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.entidades.Bolao_;
import org.javaee.bolao.entidades.Campeonato;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.entidades.UsuarioBolao_;

@ManagedBean
public class UsuarioBolaoEAO extends AbstractEAO<UsuarioBolao> {

	public UsuarioBolaoEAO() {
		super(UsuarioBolao.class);
	}

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public List<UsuarioBolao> finBoloes(Usuario usuario){
		
		CriteriaQuery<UsuarioBolao> cq = super.createCriteriaQuery();
		
		Root<UsuarioBolao> from = cq.from(UsuarioBolao.class);
		
		Join<UsuarioBolao, Usuario> joinUsuario = from.join(UsuarioBolao_.usuario);
		
		Join<UsuarioBolao, Bolao> joinBolao = from.join(UsuarioBolao_.bolao);
		
		Join<Bolao, Campeonato> joinCampeonato = joinBolao.join(Bolao_.campeonato);
		
		cq.where(getCriteriaBuilder().equal(joinUsuario, usuario));
		
		return super.createQuery(cq).getResultList();
	}
 	
}
