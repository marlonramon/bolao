

package org.javaee.bolao.entidades;

import org.javaee.bolao.enuns.EnumResultado;

public abstract class AbstractResultado extends AbstractEntity implements IResultado {
    
    
    public EnumResultado getResultado() {

        if (isResultadoMandante()) {
            return EnumResultado.MANDANTE;
        } else if (isResultadoVisitante()) {
            return EnumResultado.VISITANTE;
        } else {
            return EnumResultado.EMPATE;
        }

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
    
}
