package org.javaee.bolao.vo;

public class RankingRodadaVO {

	private Long idRodada;

	private Short numeroRodada;

	private Long idUsuario;

	private String nomeUsuario;

	private Integer pontuacao;

	public RankingRodadaVO() {
	}

	public RankingRodadaVO(Long idRodada, Short numeroRodada, Long idUsuario, String nomeUsuario, Integer pontuacao) {
		super();
		this.idRodada = idRodada;
		this.numeroRodada = numeroRodada;
		this.idUsuario = idUsuario;
		this.nomeUsuario = nomeUsuario;
		this.pontuacao = pontuacao;
	}

	public Long getIdRodada() {
		return idRodada;
	}

	public void setIdRodada(Long idRodada) {
		this.idRodada = idRodada;
	}

	public Short getNumeroRodada() {
		return numeroRodada;
	}

	public void setNumeroRodada(Short numeroRodada) {
		this.numeroRodada = numeroRodada;
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
