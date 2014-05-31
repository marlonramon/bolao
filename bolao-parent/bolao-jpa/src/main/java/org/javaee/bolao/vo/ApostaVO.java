package org.javaee.bolao.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ApostaVO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long idAposta;
	private Short placarMandante;
	private Short placarVistante;
	private Short pontuacao;

	private PartidaVO partida;

	public Long getIdAposta() {
		return idAposta;
	}

	public void setIdAposta(Long idAposta) {
		this.idAposta = idAposta;
	}

	public Short getPlacarMandante() {
		return placarMandante;
	}

	public void setPlacarMandante(Short placarMandante) {
		this.placarMandante = placarMandante;
	}

	public Short getPlacarVistante() {
		return placarVistante;
	}

	public void setPlacarVistante(Short placarVistante) {
		this.placarVistante = placarVistante;
	}

	public Short getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Short pontuacao) {
		this.pontuacao = pontuacao;
	}

	public PartidaVO getPartida() {
		return partida;
	}

	public void setPartida(PartidaVO partida) {
		this.partida = partida;
	}
}
