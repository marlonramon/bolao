package org.javaee.bolao.entidades;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Bolao extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idBolao;

	@NotNull
	@Size(max = 80)
	private String descricao;

	@NotNull
	private BigDecimal contribuicaoPorRodada;

	@NotNull
	private Short pontosAcertoDoisPlacares;

	@NotNull
	private Short pontosAcertoUmPlacar;

	@NotNull
	private Short pontosAcertoResultado;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "idCampeonato")
	private Campeonato campeonato;

	@OneToMany(mappedBy="bolao")
	@XmlTransient
	private Set<UsuarioBolao> usuariosBolao = new HashSet<>();
	
	@Transient
	private boolean isUsuarioVinculado;
	
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

	public Short getPontosAcertoDoisPlacares() {
		return pontosAcertoDoisPlacares;
	}

	public void setPontosAcertoDoisPlacares(Short pontosPlacarExato) {
		this.pontosAcertoDoisPlacares = pontosPlacarExato;
	}

	public Short getPontosAcertoUmPlacar() {
		return pontosAcertoUmPlacar;
	}

	public void setPontosAcertoUmPlacar(Short pontosResultadoEPlacar) {
		this.pontosAcertoUmPlacar = pontosResultadoEPlacar;
	}

	public Short getPontosAcertoResultado() {
		return pontosAcertoResultado;
	}

	public void setPontosAcertoResultado(Short pontosResultado) {
		this.pontosAcertoResultado = pontosResultado;
	}

	public Campeonato getCampeonato() {
		return campeonato;
	}

	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}
	
	public Set<UsuarioBolao> getUsuariosBolao() {
		return usuariosBolao;
	}
	
	public void setUsuariosBolao(Set<UsuarioBolao> usuariosBolao) {
		this.usuariosBolao = usuariosBolao;
	}
	
	public boolean isUsuarioVinculado() {
		return isUsuarioVinculado;
	}
	
	public void setUsuarioVinculado(boolean isUsuarioVinculado) {
		this.isUsuarioVinculado = isUsuarioVinculado;
	}


	@Override
	public Long getId() {
		return getIdBolao();
	}
}
