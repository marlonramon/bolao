package org.javaee.bolao.vo;

public class RankingBolaoVO {

	private Long idBolao;

	private Long idUsuario;

	private String nomeUsuario;

	private Integer pontuacao;

	public RankingBolaoVO() {
	}

	public RankingBolaoVO(Long idBolao, Long idUsuario, String nomeUsuario, Integer pontuacao) {
		super();
		this.idBolao = idBolao;
		this.idUsuario = idUsuario;
		this.nomeUsuario = nomeUsuario;
		this.pontuacao = pontuacao;
	}

	public Long getIdBolao() {
		return idBolao;
	}

	public void setIdBolao(Long idBolao) {
		this.idBolao = idBolao;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

}
