package org.javaee.bolao.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class Partida extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idPartida;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPartida;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idClubeMandante")
	private Clube clubeMandante;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idClubeVisitante")
	private Clube clubeVisitante;

	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idRodada")
	private Rodada rodada;
	
	@NotNull
	private Short placarMandante;
	
	@NotNull
	private Short placarVisitante;

	public Long getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(Long idPartida) {
		this.idPartida = idPartida;
	}

	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}

	public Clube getClubeMandante() {
		return clubeMandante;
	}

	public void setClubeMandante(Clube clubeMandante) {
		this.clubeMandante = clubeMandante;
	}

	public Clube getClubeVisitante() {
		return clubeVisitante;
	}

	public void setClubeVisitante(Clube clubeVisitante) {
		this.clubeVisitante = clubeVisitante;
	}

	public Rodada getRodada() {
		return rodada;
	}

	public void setRodada(Rodada rodada) {
		this.rodada = rodada;
	}

	public Short getPlacarMandante() {
		return placarMandante;
	}

	public void setPlacarMandante(Short placarMandante) {
		this.placarMandante = placarMandante;
	}

	public Short getPlacarVisitante() {
		return placarVisitante;
	}

	public void setPlacarVisitante(Short placarVisitante) {
		this.placarVisitante = placarVisitante;
	}

	@Override
	public Long getId() {
		return getIdPartida();
	}
}
