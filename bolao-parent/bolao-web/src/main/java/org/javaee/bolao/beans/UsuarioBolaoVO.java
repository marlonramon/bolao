package org.javaee.bolao.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.javaee.bolao.entidades.Bolao;

@XmlRootElement
public class UsuarioBolaoVO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long idUsuarioBolao;
	
	private Bolao bolao;
	
	
	public UsuarioBolaoVO() {
	
	}
	
	public UsuarioBolaoVO(Long idUsuarioBolaoVO, Bolao bolao) {
		this.idUsuarioBolao = idUsuarioBolaoVO;
		this.bolao = bolao;
	}

	public Long getIdUsuarioBolao() {
		return idUsuarioBolao;
	}


	public void setIdUsuarioBolao(Long idUsuarioBolao) {
		this.idUsuarioBolao = idUsuarioBolao;
	}

	public Bolao getBolao() {
		return bolao;
	}


	public void setBolao(Bolao bolao) {
		this.bolao = bolao;
	}
	
	
	
	
	

}
