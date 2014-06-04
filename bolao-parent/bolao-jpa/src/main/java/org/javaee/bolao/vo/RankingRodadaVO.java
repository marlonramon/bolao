package org.javaee.bolao.vo;

import java.util.ArrayList;
import java.util.List;

public class RankingRodadaVO {

	private Long idRodada;

	private Short numero;
	
	private String campeonato;

	private List<RankingUsuarioVO> usuarios = new ArrayList<RankingUsuarioVO>();
	
	public RankingRodadaVO() {
	}

	public Long getIdRodada() {
		return idRodada;
	}

	public void setIdRodada(Long idRodada) {
		this.idRodada = idRodada;
	}

	public Short getNumero() {
		return numero;
	}

	public void setNumero(Short numeroRodada) {
		this.numero = numeroRodada;
	}

	public List<RankingUsuarioVO> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(List<RankingUsuarioVO> usuarios) {
		this.usuarios = usuarios;
	}
	
	public void addUsuario(RankingUsuarioVO rankingUsuarioVO){
		usuarios.add(rankingUsuarioVO);
	}
	
	public String getCampeonato() {
		return campeonato;
	}
	
	public void setCampeonato(String campeonato) {
		this.campeonato = campeonato;
	}
	
}
