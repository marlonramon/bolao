package org.javaee.bolao.entidades;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Entity
public class Bolao extends AbstractEntity
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idBolao;

	@NotNull
	@Max(80)
	private String descricao;
	
	@NotNull
	private BigDecimal contribuicaoPorRodada;
	
	@NotNull
	private int pontosPlacarExato;
	
	@NotNull
	private int pontosResultadoEPlacar;
	
	@NotNull
	private int pontosResultado;
	
	@NotNull
	private int pontosPlacar;
	
	@NotNull
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idCampeonato")
	private Campeonato campeonato;
	
	public Long getIdBolao() {
		return idBolao;
	}

	public void setIdBolao(Long idBolao) {
		this.idBolao = idBolao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getContribuicaoPorRodada() {
		return contribuicaoPorRodada;
	}

	public void setContribuicaoPorRodada(BigDecimal contribuicaoPorRodada) {
		this.contribuicaoPorRodada = contribuicaoPorRodada;
	}

	public int getPontosPlacarExato() {
		return pontosPlacarExato;
	}

	public void setPontosPlacarExato(int pontosPlacarExato) {
		this.pontosPlacarExato = pontosPlacarExato;
	}

	public int getPontosResultadoEPlacar() {
		return pontosResultadoEPlacar;
	}

	public void setPontosResultadoEPlacar(int pontosResultadoEPlacar) {
		this.pontosResultadoEPlacar = pontosResultadoEPlacar;
	}

	public int getPontosResultado() {
		return pontosResultado;
	}

	public void setPontosResultado(int pontosResultado) {
		this.pontosResultado = pontosResultado;
	}

	public int getPontosPlacar() {
		return pontosPlacar;
	}

	public void setPontosPlacar(int pontosPlacar) {
		this.pontosPlacar = pontosPlacar;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}
	
	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}
	
	@Override
	public Long getId() {
		return getId();
	}
}
