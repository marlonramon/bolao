
package org.javaee.bolao.util;

public class ZipBean {

	private String nome;
	private byte[] conteudo;

	public ZipBean(String nome, byte[] conteudo) {
		this.nome = nome;
		this.conteudo = conteudo;
	}

	public String getNome() {
		return nome;
	}

	public byte[] getConteudo() {
		return conteudo;
	}

}