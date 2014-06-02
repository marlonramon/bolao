package org.javaee.bolao.partida;

import java.util.List;
import java.util.Set;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.javaee.bolao.eao.PartidaEAO;
import org.javaee.bolao.entidades.Aposta;
import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.entidades.Partida;

@ManagedBean
public class PartidaFacade {

    @Inject
    private PartidaEAO partidaEAO;

    public Partida insertOrUpdate(Partida partida) {

        if (!partida.hasId()) {
            this.partidaEAO.insert(partida);
        } else {
            this.partidaEAO.update(partida);
        }/*
         /
         */

        return partida;
    }

    public void delete(Long id) {
        this.partidaEAO.delete(id);
    }

    public Partida find(Long id) {
        return this.partidaEAO.find(id);
    }

    public List<Partida> findAll() {
        return this.partidaEAO.findAll();
    }

    public void encerrarPartida(Partida partida) {

        partida = partidaEAO.find(partida.getIdPartida());

        Set<Aposta> listApostas = partida.getApostas();

        for (Aposta aposta : listApostas) {

            Bolao bolao = aposta.getUsuarioBolao().getBolao();

            // if(aposta.getPlacarMandante().equals(partida.getPlacarMandante()))
        }

    }

    private Integer getPontuacaoAposta(Aposta aposta, Partida partida, Bolao bolao) {
        Integer totalAposta = 0;
        
        if (isApostaCerteira(partida, aposta)) {
            totalAposta += bolao.getPontosPlacarExato();
        } else if(isResultadoCerteiro(partida, aposta)) {
            totalAposta += bolao.getPontosResultado();
        }

        return 0;
    }

    private boolean isApostaCerteira(Partida partida, Aposta aposta) {
        return partida.getPlacarMandante().equals(aposta.getPlacarMandante()) && partida.getPlacarVisitante().equals(aposta.getPlacarVisitante());
    }
    
    private boolean isResultadoCerteiro(Partida partida, Aposta aposta) {
        return partida.getResultado().equals(aposta.getResultado());
    }
    

}
