package org.javaee.bolao.clube;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.javaee.bolao.eao.ClubeEAO;
import org.javaee.bolao.entidades.Clube;

@Stateless
public class ClubeFacade
{

  @Inject
  private ClubeEAO clubeEAO;

  public Clube insertOrUpdate(Clube clube)
  {

    if (!clube.hasId())
      this.clubeEAO.insert(clube);
    else {
      this.clubeEAO.update(clube);
    }

    return clube;
  }

  
  public void delete(Long id)
  {
    this.clubeEAO.delete(id);
  }

  public Clube find(Long id) {
    return this.clubeEAO.find(id);
  }

  public List<Clube> findAll() {
    return this.clubeEAO.findAll();
  }  
}