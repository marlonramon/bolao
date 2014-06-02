package org.javaee.bolao.entidades;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.javaee.bolao.enuns.EnumResultado;

@Entity
public class Aposta extends AbstractResultado {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idAposta;
	
	@NotNull
	private Short placarMandante;
	
	@NotNull
	private Short placarVisitante;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idPartida")
	private Partida partida;
	
	@NotNull
	private Integer pontuacao;
	
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

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
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
