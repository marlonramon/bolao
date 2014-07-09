package org.javaee.bolao.eao;

import java.util.Date;
import java.util.UUID;

import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.SessaoUsuario_;
import org.javaee.bolao.entidades.Usuario;

@ManagedBean
public class SessaoUsuarioEAO extends AbstractEAO<SessaoUsuario>
{
  @PersistenceContext
  private EntityManager entityManager;

  public SessaoUsuarioEAO()
  {
    super(SessaoUsuario.class);
  }

  public EntityManager getEntityManager()
  {
    return this.entityManager;
  }

  public SessaoUsuario findByToken(String token) {
    CriteriaQuery<SessaoUsuario> criteriaQuery = createCriteriaQuery();

    Root<SessaoUsuario> from = criteriaQuery.from(SessaoUsuario.class);

    criteriaQuery.where(getCriteriaBuilder().equal(from.get(SessaoUsuario_.token), token));

    return super.getSingleResult(criteriaQuery);
  }

  public SessaoUsuario create(Usuario user, long expirationTimeMillis) {
    SessaoUsuario userSession = new SessaoUsuario();
    userSession.setToken(createToken());

    userSession.setUsuario(user);

    long currentTimeMillis = System.currentTimeMillis();

    userSession.setDataCadastro(new Date(currentTimeMillis));
    userSession.setDataExpiracao(new Date(currentTimeMillis + expirationTimeMillis));

    super.insert(userSession);

    return userSession;
  }

  public String createToken() {
    return UUID.randomUUID().toString();
  }

  public void apagarSessoesDoUsuario(Usuario user) {
    CriteriaDelete<SessaoUsuario> criteriaDelete = super.createCriteriaDelete();

    Root<SessaoUsuario> root = criteriaDelete.from(SessaoUsuario.class);

    Predicate userEqual = getCriteriaBuilder().equal(root.get(SessaoUsuario_.usuario), user);

    criteriaDelete.where(userEqual);

    Query deleteQuery = super.createQuery(criteriaDelete);

    int deleted = deleteQuery.executeUpdate();

//    this.logger.info(deleted + " sessões do usuário: " + user.getEmail() +" removidas");
  }
}