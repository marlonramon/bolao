package org.javaee.bolao.entidades;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.javaee.bolao.enuns.EnumResultado;

@Embeddable
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Placar implements IResultado, Serializable {
	private static final long serialVersionUID = 1L;

	@NotNull
	@XmlElement(nillable=true)
	private Short placarMandante = null;

	@NotNull
	@XmlElement(nillable=true)
	private Short placarVisitante = null;

	public EnumResultado getResultado() {

		if(getPlacarMandante() != null && getPlacarVisitante() != null){
			if (isResultadoMandante()) {
				return EnumResultado.MANDANTE;
			} else if (isResultadoVisitante()) {
				return EnumResultado.VISITANTE;
			} else {
				return EnumResultado.EMPATE;
			}
		}
		
		return EnumResultado.NAO_DEFINIDO;
	}

	public boolean isResultadoMandante() {
		return getPlacarMandante().compareTo(getPlacarVisitante()) > 0;
	}

	public boolean isResultadoVisitante() {
		return getPlacarVisitante().compareTo(getPlacarMandante()) > 0;
	}

	public boolean isResultadoEmpate() {
		return getPlacarVisitante().compareTo(getPlacarMandante()) == 0;
	}

	public Short getPlacarMandante() {
		return placarMandante;
	}

	public void setPlacarMandante(Short placarMandante) {
		this.placarMandante = placarMandante;
	}

	public Short getPlacarVisitante() {
		return placarVisitante;
	}

	public void setPlacarVisitante(Short placarVisitante) {
		this.placarVisitante = placarVisitante;
	}
	
	public boolean isResultadoDefinido(){
		return getResultado() != EnumResultado.NAO_DEFINIDO;
	}
	
	public boolean isIgual(Placar placar){
		boolean resultadoIgual = false;
		
		if(isResultadoDefinido() && placar.isResultadoDefinido()){
			resultadoIgual = getPlacarMandante().equals(placar.getPlacarMandante()) &&
					getPlacarVisitante().equals(placar.getPlacarVisitante());
		}
		
		return resultadoIgual;
	}
	
	public boolean isUmPlacarIgual(Placar placar){
		boolean resultadoIgual = false;
		
		if(isResultadoDefinido() && placar.isResultadoDefinido()){
			resultadoIgual = getPlacarMandante().equals(placar.getPlacarMandante()) ||
					getPlacarVisitante().equals(placar.getPlacarVisitante());
		}
		
		return resultadoIgual;
	}
	
	public boolean isAoMenosUmPlacarPreenchido(){
		return getPlacarMandante() != null || getPlacarVisitante() != null;
	}
	
	@Override
	public String toString() {
		return getPlacarMandante() + " X "+getPlacarVisitante();
	}
	
}
