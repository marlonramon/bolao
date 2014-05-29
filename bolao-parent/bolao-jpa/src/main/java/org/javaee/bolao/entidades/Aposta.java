package org.javaee.bolao.entidades;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Aposta extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idAposta;
	
	@NotNull
	private int placarMandante;
	
	@NotNull
	private int placarVisitante;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idPartida")
	private Partida partida;
	
	@NotNull
	private int pontuacao;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuarioBolao")
	private UsuarioBolao usuarioBolao;

	public Long getIdAposta() {
		return idAposta;
	}

	public void setIdAposta(Long idAposta) {
		this.idAposta = idAposta;
	}

	public int getPlacarMandante() {
		return placarMandante;
	}

	public void setPlacarMandante(int placarMandante) {
		this.placarMandante = placarMandante;
	}

	public int getPlacarVisitante() {
		return placarVisitante;
	}

	public void setPlacarVisitante(int placarVisitante) {
		this.placarVisitante = placarVisitante;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	public UsuarioBolao getUsuarioBolao() {
		return usuarioBolao;
	}

	public void setUsuarioBolao(UsuarioBolao usuarioBolao) {
		this.usuarioBolao = usuarioBolao;
	}
	
	@Override
	public Long getId() {
		return getIdAposta();
	}
}
