package org.javaee.bolao.vo;

public class RankingUsuarioVO {

	private Long idUsuario;

	private String nome;

	private Integer pontuacao;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
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
