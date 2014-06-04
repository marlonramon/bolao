package org.javaee.bolao.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RodadaApostaVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idRodada;

	private Short numero;

	private String idCampeonato;

	private String descricaoCampeonato;

	private List<ApostaVO> apostas = new ArrayList<>();

	public Long getIdRodada() {
		return idRodada;
	}

	public void setIdRodada(Long idRodada) {
		this.idRodada = idRodada;
	}

	public Short getNumero() {
		return numero;
	}

	public void setNumero(Short numero) {
		this.numero = numero;
	}

	public String getIdCampeonato() {
		return idCampeonato;
	}

	public void setIdCampeonato(String idCampeonato) {
		this.idCampeonato = idCampeonato;
	}

	public String getDescricaoCampeonato() {
		return descricaoCampeonato;
	}

	public void setDescricaoCampeonato(String descricaoCampeonato) {
		this.descricaoCampeonato = descricaoCampeonato;
	}

	public List<ApostaVO> getApostas() {
		return apostas;
	}

	public void setApostas(List<ApostaVO> apostas) {
		this.apostas = apostas;
	}

}
