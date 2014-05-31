package org.javaee.bolao.partida;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.javaee.bolao.eao.PartidaEAO;
import org.javaee.bolao.entidades.Partida;

@ManagedBean
public class PartidaFacade
{

  @Inject
  private PartidaEAO partidaEAO;

  public Partida insertOrUpdate(Partida partida)
  {

    if (!partida.hasId())
      this.partidaEAO.insert(partida);
    else {
      this.partidaEAO.update(partida);
    }

    return partida;
  }

  
  public void delete(Long id)
  {
    this.partidaEAO.delete(id);
  }

  public Partida find(Long id) {
    return this.partidaEAO.find(id);
  }

  public List<Partida> findAll() {
    return this.partidaEAO.findAll();
  }  
}