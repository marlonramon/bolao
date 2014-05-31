package org.javaee.bolao.vo;

import java.io.Serializable;
import java.util.Date;

public class PartidaVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long idPartida;
	private ClubeVO clubeMandante;
	private ClubeVO clubeVistitante;
	private Date dataPartida;
	private Short placarEfetivoMandante;
	private Short placarEfetivoVistante;

	public Long getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(Long idPartida) {
		this.idPartida = idPartida;
	}

	public ClubeVO getClubeMandante() {
		return clubeMandante;
	}

	public void setClubeMandante(ClubeVO clubeMandante) {
		this.clubeMandante = clubeMandante;
	}

	public ClubeVO getClubeVistitante() {
		return clubeVistitante;
	}

	public void setClubeVistitante(ClubeVO clubeVistitante) {
		this.clubeVistitante = clubeVistitante;
	}

	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	public Short getPlacarEfetivoMandante() {
		return placarEfetivoMandante;
	}

	public void setPlacarEfetivoMandante(Short placarEfetivoMandante) {
		this.placarEfetivoMandante = placarEfetivoMandante;
	}

	public Short getPlacarEfetivoVistante() {
		return placarEfetivoVistante;
	}

	public void setPlacarEfetivoVistante(Short placarEfetivoVistante) {
		this.placarEfetivoVistante = placarEfetivoVistante;
	}
}
