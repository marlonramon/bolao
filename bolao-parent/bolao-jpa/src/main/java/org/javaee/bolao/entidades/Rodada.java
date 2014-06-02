package org.javaee.bolao.entidades;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Rodada extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRodada;

	@NotNull
	private Short numero;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "idCampeonato")
	private Campeonato campeonato;

	@OneToMany(mappedBy = "rodada")
	private Set<Partida> partidas = new HashSet<>();

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

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	public Set<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(Set<Partida> partidas) {
		this.partidas = partidas;
	}

	@Override
	public Long getId() {
		return getIdRodada();
	}
}
