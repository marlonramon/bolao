package org.javaee.bolao.vo;

import java.io.Serializable;

public class ClubeVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long idClube;
	private String nome;
	private String bandeira;

	public Long getIdClube() {
		return idClube;
	}

	public void setIdClube(Long idClube) {
		this.idClube = idClube;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
}
