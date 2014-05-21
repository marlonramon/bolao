package org.javaee.bolao.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class SessaoUsuario extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idSessaoUsuario;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "idUsuario")
	private Usuario usuario;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataExpiracao;

	@NotNull
	@Size(min = 36, max = 36)
	private String token;

	public Long getIdSessaoUsuario() {
		return idSessaoUsuario;
	}

	public void setIdSessaoUsuario(Long idSessaoUsuario) {
		this.idSessaoUsuario = idSessaoUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date criacao) {
		this.dataCadastro = criacao;
	}

	public Date getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(Date expiracao) {
		this.dataExpiracao = expiracao;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public Long getId() {
		return getIdSessaoUsuario();
	}

	public boolean isExpirado(Date dataAtual) {
		return getDataExpiracao().after(dataAtual);
	}

}
