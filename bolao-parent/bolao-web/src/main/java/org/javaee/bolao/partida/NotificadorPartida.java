package org.javaee.bolao.partida;

import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.javaee.bolao.eao.PartidaEAO;
import org.javaee.bolao.eao.UsuarioEAO;
import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.entidades.Usuario;

@Stateless
public class NotificadorPartida {

	@Inject
	private PartidaEAO partidaEAO;
	
	@Inject
	private UsuarioEAO usuarioEAO;
	
	@Inject 
	private RelatorioApostas relatorioApostas;
	
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void notificar(Timer timer){
	
		Partida partida = (Partida) timer.getInfo();
		
		Partida partidaDB = partidaEAO.find(partida);
		
		if(partidaDB != null){
			
			List<Usuario> usuarios = findUsuariosByPartida(partidaDB);  
			
			for (Usuario usuario : usuarios) {
				notificar(usuario, partidaDB);
			}
			
		}
	}

	private void notificar(Usuario usuario, Partida partidaDB){
	
		byte[] pdf = relatorioApostas.gerarRelatorioPDFApostasPorPartida(partidaDB);
		
		
	}

	private List<Usuario> findUsuariosByPartida(Partida partidaDB) {
		return usuarioEAO.findByPartida(partidaDB);
	}
	
}
