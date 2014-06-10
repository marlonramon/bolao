package org.javaee.bolao.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Campeonato extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idCampeonato;
	
	@NotNull
	@Size(max=80)
	private String descricao;

	@OneToMany(mappedBy="campeonato")
	@XmlTransient
	private Set<Rodada> rodadas = new HashSet<>();
	
	@OneToMany(mappedBy="campeonato")
	@XmlTransient
	private Set<Bolao> boloes = new HashSet<Bolao>(); 
	
	public Long getIdCampeonato() {
		return idCampeonato;
	}

	public void setIdCampeonato(Long idCampeonato) {
		this.idCampeonato = idCampeonato;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Set<Rodada> getRodadas() {
		return rodadas;
	}
	
	public void setRodadas(Set<Rodada> rodadas) {
		this.rodadas = rodadas;
	}
	
	public Set<Bolao> getBoloes() {
		return boloes;
	}
	
	public void setBoloes(Set<Bolao> boloes) {
		this.boloes = boloes;
	}
	
	@Override
	public Long getId() {
		return getIdCampeonato();
	}
}
