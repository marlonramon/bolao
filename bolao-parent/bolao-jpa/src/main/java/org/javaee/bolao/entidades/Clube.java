package org.javaee.bolao.entidades;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

public class Clube
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idClube;
	
	@NotNull
	@Max(value=80)
	private String nome;
	
	@NotNull
	@Max(value=80)
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
