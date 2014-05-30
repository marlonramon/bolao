package org.javaee.bolao.campeonato;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.javaee.bolao.eao.CampeonatoEAO;
import org.javaee.bolao.entidades.Campeonato;

@ManagedBean
public class CampeonatoFacade
{

  @Inject
  private CampeonatoEAO campeonatoEAO;

  public Campeonato insertOrUpdate(Campeonato campeonato)
  {

    if (!campeonato.hasId())
      this.campeonatoEAO.insert(campeonato);
    else {
      this.campeonatoEAO.update(campeonato);
    }

    return campeonato;
  }

  
  public void delete(Long id)
  {
    this.campeonatoEAO.delete(id);
  }

  public Campeonato find(Long id) {
    return this.campeonatoEAO.find(id);
  }

  public List<Campeonato> findAll() {
    return this.campeonatoEAO.findAll();
  }  
}