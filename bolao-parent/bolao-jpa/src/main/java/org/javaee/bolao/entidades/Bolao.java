package org.javaee.bolao.entidades;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Bolao extends AbstractEntity
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idBolao;

	@NotNull
	@Size(max=80)
	private String descricao;
	
	@NotNull
	private BigDecimal contribuicaoPorRodada;
	
	@NotNull
	private Short pontosPlacarExato;
	
	@NotNull
	private Short pontosResultadoEPlacar;
	
	@NotNull
	private Short pontosResultado;
	
	@NotNull
	private Short pontosPlacar;
	
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

	public Short getPontosPlacarExato() {
		return pontosPlacarExato;
	}

	public void setPontosPlacarExato(Short pontosPlacarExato) {
		this.pontosPlacarExato = pontosPlacarExato;
	}

	public Short getPontosResultadoEPlacar() {
		return pontosResultadoEPlacar;
	}

	public void setPontosResultadoEPlacar(Short pontosResultadoEPlacar) {
		this.pontosResultadoEPlacar = pontosResultadoEPlacar;
	}

	public Short getPontosResultado() {
		return pontosResultado;
	}

	public void setPontosResultado(Short pontosResultado) {
		this.pontosResultado = pontosResultado;
	}

	public Short getPontosPlacar() {
		return pontosPlacar;
	}

	public void setPontosPlacar(Short pontosPlacar) {
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
		return getIdBolao();
	}
}
