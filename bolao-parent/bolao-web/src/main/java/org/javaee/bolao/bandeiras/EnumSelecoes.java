package org.javaee.bolao.bandeiras;

public enum EnumSelecoes {

	AficaDoSul("AficaDoSul","AficaDoSul.jpg"),
	Alemanha("Alemanha","Alemanha.jpg"),
	Argelia("Argelia","Argelia.jpg"),
	Argentina("Argentina","Argentina.jpg"),
	Australia("Australia","Australia.jpg"),
	Brasil("Brasil","Brasil.jpg"),
	Camaroes("Camaroes","Camaroes.jpg"),
	Chile("Chile","Chile.jpg"),
	CoreiaDoNorte("CoreiaDoNorte","CoreiaDoNorte.jpg"),
	CoreiaDoSul("CoreiaDoSul","CoreiaDoSul.jpg"),
	CostaDoMarfim("CostaDoMarfim","CostaDoMarfim.jpg"),
	Dinamarca("Dinamarca","Dinamarca.jpg"),
	Eslovaquia("Eslovaquia","Eslovaquia.jpg"),
	Eslovenia("Eslovenia","Eslovenia.jpg"),
	Espanha("Espanha","Espanha.jpg"),
	EstadosUnidos("EstadosUnidos","EstadosUnidos.jpg"),
	Franca("Franca","Franca.jpg"),
	Gana("Gana","Gana.jpg"),
	Grecia("Grecia","Grecia.jpg"),
	Holanda("Holanda","Holanda.jpg"),
	Honduras("Honduras","Honduras.jpg"),
	Inglaterra("Inglaterra","Inglaterra.jpg"),
	Italia("Italia","Italia.jpg"),
	Japao("Japao","Japao.jpg"),
	Mexico("Mexico","Mexico.jpg"),
	Nigeria("Nigeria","Nigeria.jpg"),
	NovaZelandia("NovaZelandia","NovaZelandia.jpg"),
	Paraguai("Paraguai","Paraguai.jpg"),
	Portugal("Portugal","Portugal.jpg"),
	Servia("Servia","Servia.jpg"),
	Suica("Suica","Suica.jpg"),
	Uruguai("Uruguai","Uruguai.jpg"),
	
	;
	
	private String descricao;
	private String nomeArquivo;
	
	EnumSelecoes(String descricao, String nomeArquivo) {
		this.descricao = descricao;
		this.nomeArquivo = nomeArquivo;
	}
	
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
