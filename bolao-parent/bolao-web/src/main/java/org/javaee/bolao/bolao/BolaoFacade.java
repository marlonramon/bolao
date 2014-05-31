package org.javaee.bolao.bolao;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.javaee.bolao.eao.BolaoEAO;
import org.javaee.bolao.entidades.Bolao;

@ManagedBean
public class BolaoFacade
{

  @Inject
  private BolaoEAO bolaoEAO;

  public Bolao insertOrUpdate(Bolao bolao)
  {

    if (!bolao.hasId())
      this.bolaoEAO.insert(bolao);
    else {
      this.bolaoEAO.update(bolao);
    }

    return bolao;
  }

  
  public void delete(Long id)
  {
    this.bolaoEAO.delete(id);
  }

  public Bolao find(Long id) {
    return this.bolaoEAO.find(id);
  }

  public List<Bolao> findAll() {
    return this.bolaoEAO.findAll();
  }  
}