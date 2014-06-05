package org.javaee.bolao.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.javaee.bolao.vo.ClubeVO;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Clube extends AbstractEntity
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idClube;
	
	@NotNull
	@Size(max=80)
	private String nome;
	
	@NotNull
	@Size(max=80)
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
	
	@Override
	public Long getId() {
		return getIdClube();
	}
	
	public ClubeVO getClubeVO()
	{
		ClubeVO clubeVO = new ClubeVO();
		
		clubeVO.setIdClube(getId());
		clubeVO.setNome(getNome());
		clubeVO.setBandeira(getBandeira());
		
		return clubeVO;
	}
}
