package org.javaee.bolao.vo;

public class RankingUsuarioVO {

	private Long idUsuarioBolao;

	private String nome;

	private Integer pontuacao;

	public Long getIdUsuarioBolao() {
		return idUsuarioBolao;
	}

	public void setIdUsuarioBolao(Long idUsuario) {
		this.idUsuarioBolao = idUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nomeUsuario) {
		this.nome = nomeUsuario;
	}

	public Integer getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}

}
