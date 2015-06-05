package org.javaee.bolao.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SessaoUsuarioVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UsuarioVO usuario;
	
	private String token;
	
	private String code;
	
	public SessaoUsuarioVO() {
	}
	
	public SessaoUsuarioVO(UsuarioVO usuarioVO, String token, String code) {
		this.usuario = usuarioVO;
		this.token = token;
		this.code = code;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	

}
