package org.javaee.bolao.vo;

import java.util.ArrayList;
import java.util.List;

public class RankingBolaoVO {

	private Long idBolao;

	private String descricao;
	
	private List<RankingUsuarioVO> usuarios = new ArrayList<RankingUsuarioVO>();

	public Long getIdBolao() {
		return idBolao;
	}

	public void setIdBolao(Long idBolao) {
		this.idBolao = idBolao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricaoBolao) {
		this.descricao = descricaoBolao;
	}

	public List<RankingUsuarioVO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<RankingUsuarioVO> rankingUsuarios) {
		this.usuarios = rankingUsuarios;
	}
	
	public void addUsuario(RankingUsuarioVO rankingUsuarioVO){
		usuarios.add(rankingUsuarioVO);
	}
	
	
	

}
