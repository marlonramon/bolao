package org.javaee.bolao.entidades;

import java.util.Date;

import javax.persistence.Embedded;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Aposta extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idAposta;
	
	@Embedded
	@XmlElement(nillable=true)
	private Placar placar;
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idPartida")
	private Partida partida;
	
	@NotNull
	private Integer pontuacao = 0;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idUsuarioBolao")
	private UsuarioBolao usuarioBolao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAposta;
	
	public Long getIdAposta() {
		return idAposta;
	}

	public void setIdAposta(Long idAposta) {
		this.idAposta = idAposta;
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
	
	public Placar getPlacar() {
		return placar;
	}
	
	public void setPlacar(Placar placar) {
		this.placar = placar;
	}
	
	public Date getDataAposta() {
		return dataAposta;
	}
	
	public void setDataAposta(Date dataAposta) {
		this.dataAposta = dataAposta;
	}
	
	@Override
	public Long getId() {
		return getIdAposta();
	}
	
	@Override
	public String toString() {
		return getPartida().getClubeMandante() + " X " +getPartida().getClubeVisitante();
	}
        
}
