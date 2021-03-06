package org.javaee.bolao.entidades;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Partida extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPartida;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPartida;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idClubeMandante")
    private Clube clubeMandante;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "idClubeVisitante")
    private Clube clubeVisitante;

    @NotNull
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idRodada")
    private Rodada rodada;

    @Embedded
    private Placar placar;

    @OneToMany(mappedBy = "partida", fetch = FetchType.LAZY)
    @XmlTransient
    private Set<Aposta> apostas = new HashSet<>();
    
    @Transient    
    private boolean partidaFinalizada; 
    
    public Long getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(Long idPartida) {
        this.idPartida = idPartida;
    }

    public Date getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(Date dataPartida) {
        this.dataPartida = dataPartida;
    }

    public Clube getClubeMandante() {
        return clubeMandante;
    }

    public void setClubeMandante(Clube clubeMandante) {
        this.clubeMandante = clubeMandante;
    }

    public Clube getClubeVisitante() {
        return clubeVisitante;
    }

    public void setClubeVisitante(Clube clubeVisitante) {
        this.clubeVisitante = clubeVisitante;
    }

    public Rodada getRodada() {
        return rodada;
    }

    public void setRodada(Rodada rodada) {
        this.rodada = rodada;
    }

    public Set<Aposta> getApostas() {
        return apostas;
    }

    public void setApostas(Set<Aposta> apostas) {
        this.apostas = apostas;
    }
    
    public Placar getPlacar() {
		return placar;
	}
    
    public void setPlacar(Placar placar) {
		this.placar = placar;
	}
    
    public boolean isEncerrada(){
    	return getPlacar() != null && getPlacar().isResultadoDefinido();
    }

    @Override
    public Long getId() {
        return getIdPartida();
    }

    public String getDescricao() {
    	return getClubeMandante().getNome() + " X "+getClubeVisitante().getNome();
    }
        
    @PostLoad
	public void setPartidaFinalizada() {
		partidaFinalizada =  dataPartida != null ? dataPartida.compareTo(new Date()) < 0 : false;
	}

	
}
