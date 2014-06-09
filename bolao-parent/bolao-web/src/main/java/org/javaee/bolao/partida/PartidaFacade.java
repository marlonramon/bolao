package org.javaee.bolao.partida;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.javaee.bolao.eao.ApostaEAO;
import org.javaee.bolao.eao.PartidaEAO;
import org.javaee.bolao.entidades.Aposta;
import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.entidades.Partida;

@Stateless
public class PartidaFacade {

	@Inject
	private PartidaEAO partidaEAO;
	
	@Inject
	private ApostaEAO apostaEAO;
	

	public Partida insertOrUpdate(Partida partida) {

		if (!partida.hasId()) {
			this.partidaEAO.insert(partida);
		} else {
			this.partidaEAO.update(partida);
		}

		encerrarPartida(partida);

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

		if (partida.hasId()) {
			partida = partidaEAO.find(partida.getIdPartida());
			System.out.println( partida.isEncerrada());

			if (partida.isEncerrada()) {

				List<Aposta> listApostas = apostaEAO.findByPartida(partida);

				for (Aposta aposta : listApostas) {
					
					System.out.println("entrei");

					Bolao bolao = aposta.getUsuarioBolao().getBolao();

					aposta.setPontuacao(getPontuacaoAposta(aposta, partida, bolao));
					
					System.out.println("entrei: " + aposta.getPontuacao());
					apostaEAO.update(aposta);
					
				}
			}
		}

	}

	private Integer getPontuacaoAposta(Aposta aposta, Partida partida, Bolao bolao) {
		Integer totalAposta = 0;

		if (isPlacarCerteiro(partida, aposta)) {
			totalAposta += bolao.getPontosAcertoDoisPlacares();
		} else {
			if (isResultadoCerteiro(partida, aposta)) {
				totalAposta += bolao.getPontosAcertoResultado();
			}

			if (isUmPlacarCerteiro(partida, aposta)) {
				totalAposta += bolao.getPontosAcertoUmPlacar();
			}
		}

		return totalAposta;
	}

	private boolean isUmPlacarCerteiro(Partida partida, Aposta aposta) {
		return partida.getPlacar().isUmPlacarIgual(aposta.getPlacar());
	}

	private boolean isPlacarCerteiro(Partida partida, Aposta aposta) {
		return partida.getPlacar().isIgual(aposta.getPlacar());
	}

	private boolean isResultadoCerteiro(Partida partida, Aposta aposta) {
		return partida.getPlacar().getResultado().equals(aposta.getPlacar().getResultado());
	}

}
