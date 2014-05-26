package org.javaee.bolao.eao;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.javaee.bolao.entidades.SessaoUsuario;
import org.javaee.bolao.entidades.SessaoUsuario_;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.entidades.Usuario_;

@ManagedBean
public class SessaoUsuarioEAO extends AbstractEAO<SessaoUsuario>
{
  private static final long serialVersionUID = 1L;

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
    CriteriaQuery criteriaQuery = createCriteriaQuery();

    Root from = criteriaQuery.from(SessaoUsuario.class);

    criteriaQuery.where(getCriteriaBuilder().equal(from.get(SessaoUsuario_.token), token));

    return (SessaoUsuario)createQuery(criteriaQuery).getSingleResult();
  }

  public SessaoUsuario findSessaoValida(String email, Date date)
  {
    TypedQuery query = super.getNamedQuery("SessaoUsuario.findSessaoValida");

    if (query == null)
    {
      CriteriaQuery criteriaQuery = createCriteriaQuery();

      Root from = criteriaQuery.from(SessaoUsuario.class);

      Join joinUser = from.join(SessaoUsuario_.usuario);

      Predicate equalLogin = getCriteriaBuilder().equal(joinUser.get(Usuario_.email), email);

      ParameterExpression dateParameter = getCriteriaBuilder().parameter(Date.class, "date");

      Predicate between = getCriteriaBuilder().between(dateParameter, from.get(SessaoUsuario_.dataCadastro), from.get(SessaoUsuario_.dataExpiracao));

      criteriaQuery.where(new Predicate[] { equalLogin, between });

      query = createQuery(criteriaQuery);

      super.addNamedQuery("SessaoUsuario.findSessaoValida", query);
    }

    query.setParameter("date", date);

    return (SessaoUsuario)super.getSingleResult(query);
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

  public void deleteExpiratedSessions(Usuario user, Date actualData) {
    CriteriaDelete criteriaDelete = super.createCriteriaDelete();

    Root root = criteriaDelete.from(SessaoUsuario.class);

    Predicate userEqual = getCriteriaBuilder().equal(root.get(SessaoUsuario_.usuario), user);

    Predicate expiratedDate = getCriteriaBuilder().lessThanOrEqualTo(root.get(SessaoUsuario_.dataExpiracao), actualData);

    criteriaDelete.where(new Predicate[] { userEqual, expiratedDate });

    Query deleteQuery = super.createQuery(criteriaDelete);

    int deleted = deleteQuery.executeUpdate();

    this.logger.info(deleted + " sessões inválidas do usuário: " + user.getEmail());
  }
}