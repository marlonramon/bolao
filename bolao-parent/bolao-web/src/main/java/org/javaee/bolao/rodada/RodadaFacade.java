package org.javaee.bolao.rodada;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.javaee.bolao.eao.RodadaEAO;
import org.javaee.bolao.entidades.Rodada;

@ManagedBean
public class RodadaFacade
{

  @Inject
  private RodadaEAO rodadaEAO;

  public Rodada insertOrUpdate(Rodada rodada)
  {

    if (!rodada.hasId())
      this.rodadaEAO.insert(rodada);
    else {
      this.rodadaEAO.update(rodada);
    }

    return rodada;
  }

  
  public void delete(Long id)
  {
    this.rodadaEAO.delete(id);
  }

  public Rodada find(Long id) {
    return this.rodadaEAO.find(id);
  }

  public List<Rodada> findAll() {
    return this.rodadaEAO.findAll();
  }  
}