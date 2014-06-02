package org.javaee.bolao.bolao;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.javaee.bolao.eao.BolaoEAO;
import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.vo.RankingBolaoVO;

@ManagedBean
public class BolaoFacade
{

  @Inject
  private BolaoEAO bolaoEAO;

  public Bolao insertOrUpdate(Bolao bolao)
  {

    if (!bolao.hasId())
      bolaoEAO.insert(bolao);
    else {
      bolaoEAO.update(bolao);
    }

    return bolao;
  }

  
  public void delete(Long id)
  {
    bolaoEAO.delete(id);
  }

  public Bolao find(Long id) {
    return bolaoEAO.find(id);
  }

  public List<Bolao> findAll() {
    return bolaoEAO.findAll();
  }


public List<RankingBolaoVO> ranking(Long id, Integer limite) {
	Bolao bolao = bolaoEAO.find(id);
	return bolaoEAO.ranking(bolao, limite);
}  
}