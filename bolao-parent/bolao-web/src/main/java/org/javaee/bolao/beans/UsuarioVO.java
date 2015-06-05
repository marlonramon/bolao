package org.javaee.bolao.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class UsuarioVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long idUsuario;

	private String nome;
	
	private String email;

	private Boolean admin = Boolean.FALSE;

	public UsuarioVO() {
	
	}
	
	public UsuarioVO(Long idUsuario, String nome, String email, Boolean admin) {
		this.idUsuario = idUsuario;
		this.nome = nome;
		this.email = email;
		this.admin = admin;
	}
	
	public Long getIdUsuario() {
		return idUsuario;
	}
	
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
	
	


}
