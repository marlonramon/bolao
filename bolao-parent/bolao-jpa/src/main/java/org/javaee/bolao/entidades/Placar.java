package org.javaee.bolao.entidades;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.javaee.bolao.enuns.EnumResultado;

@Embeddable
public class Placar implements IResultado {

	@NotNull
	private Short placarMandante;

	@NotNull
	private Short placarVisitante;

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

}
