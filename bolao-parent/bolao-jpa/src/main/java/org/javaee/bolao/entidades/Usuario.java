package org.javaee.bolao.entidades;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Usuario extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	
	public enum PerfilUsuario {
		ADMINISTRADOR,
		USUARIO;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idUsuario;
	
	@NotNull
	@Size(min = 1, max = 80)
	private String senha;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimoAcesso;
	
	@NotNull
	@Size(min = 1, max = 80)
	private String nome;
	
	@Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "E-mail inválido")	
	@Size(max = 120)
	private String email;

	@NotNull
	@Enumerated(EnumType.STRING)
	private PerfilUsuario perfil = PerfilUsuario.USUARIO;

	public Usuario() {
	}

	@PrePersist
	private void prePersist() {
		Date dataAtual = new Date();
		setDataCadastro(dataAtual);
		setDataUltimoAcesso(dataAtual);
	}

	@Override
	public Long getId() {
		return getIdUsuario();
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PerfilUsuario getPerfil() {
		return perfil;
	}
	
	public void setPerfil(PerfilUsuario perfil) {
		this.perfil = perfil;
	}
}