package org.javaee.bolao.aposta;

import java.util.Collections;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import org.javaee.bolao.eao.ApostaEAO;
import org.javaee.bolao.eao.PartidaEAO;
import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.entidades.Rodada;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.vo.ApostaVO;

@ManagedBean
public class ApostaFacade {
	@Inject
	private ApostaEAO apostaEAO;

	@Inject
	private PartidaEAO partidaEAO;

	public List<ApostaVO> findApostas(UsuarioBolao usuario, Rodada rodada) {
		List<Partida> partidas = partidaEAO.findPartida(usuario, rodada);
		return createApostaVOList(partidas);
	}

	private List<ApostaVO> createApostaVOList(List<Partida> partidas) {
		List<ApostaVO> result = Collections.emptyList();

		for (Partida partida : partidas) {
			result.add(createApostaVO(partida));
		}

		return result;
	}

	private ApostaVO createApostaVO(Partida partida) {
		ApostaVO apostaVO = new ApostaVO();

		
		
		
	//	apostaVO.setIdAposta();
	//	apostaVO.setPartida(partida);
	//	apostaVO.setPlacarMandante(placarMandante);
	//	apostaVO.setPlacarVistante(placarVistante);
	//	apostaVO.setPontuacao(pontuacao);
	//  glassfish 4.0.1 night build.
		
		return apostaVO;
	}

	public List<ApostaVO> persitApostas(List<ApostaVO> apostaList) {
		return null;
	}
}
