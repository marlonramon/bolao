package org.javaee.bolao.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioBolao extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuarioBolao;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "idBolao")
	private Bolao bolao;

	public Long getIdUsuarioBolao() {
		return idUsuarioBolao;
	}

	public void setIdUsuarioBolao(Long idUsuarioBolao) {
		this.idUsuarioBolao = idUsuarioBolao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Bolao getBolao() {
		return bolao;
	}

	public void setBolao(Bolao bolao) {
		this.bolao = bolao;
	}

	@Override
	public Long getId() {
		return getIdUsuarioBolao();
	}
}
