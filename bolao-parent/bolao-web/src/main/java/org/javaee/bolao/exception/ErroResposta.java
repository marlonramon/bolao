package org.javaee.bolao.exception;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ErroResposta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mensagem;
	
	private List<String> mensagens = new ArrayList<>();
	
	public ErroResposta() {
	}
	
	public ErroResposta(String message) {
		this.mensagem = message;			
	}
	
	public ErroResposta(String message, Object ... arguments) {
		this.mensagem = MessageFormat.format(message, arguments);			
	}
	
	public String getMensagem() {
		return mensagem;
	}
	
	public void setMensagem(String message) {
		this.mensagem = message;
	}

	public boolean addMensagem(String e) {
		return mensagens.add(e);
	}
	
	public List<String> getMensagens() {
		return mensagens;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Erro Principal: "+getMensagem());
		
		if(!mensagens.isEmpty()){
			for (int i = 0; i < mensagens.size(); i++) {
				String m = mensagens.get(i);
				sb.append("\n"+m);
			}
		}
		
		return sb.toString();
	}
	
}
