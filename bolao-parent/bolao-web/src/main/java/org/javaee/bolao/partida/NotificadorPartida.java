package org.javaee.bolao.partida;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.javaee.bolao.config.BolaoConfig;
import org.javaee.bolao.eao.PartidaEAO;
import org.javaee.bolao.eao.UsuarioEAO;
import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.entidades.Usuario;
import org.javaee.bolao.exception.BolaoRuntimeException;
import org.javaee.bolao.mail.NotificadorEmail;

@Stateless
public class NotificadorPartida {

	@Inject
	private PartidaEAO partidaEAO;
	
	@Inject
	private UsuarioEAO usuarioEAO;
	
	@Inject 
	private RelatorioApostas relatorioApostas;
	
	@Inject
	private NotificadorEmail notificadorEmail;
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void notificar(Date dataTimer, Partida partida){
	
		Partida partidaDB = partidaEAO.find(partida);
		
		if(partidaDB != null){
		
			byte[] pdf = relatorioApostas.gerarRelatorioPDFApostasPorPartida(partidaDB);
			
			notificarAdmins(pdf, partidaDB);
			
		}
	}
	
	private String getNomeArquivoPartida(Partida partida){
		return partida.getClubeMandante().getNome()+"X"+partida.getClubeVisitante().getNome()+"_"+System.currentTimeMillis()+".pdf";
	}
	
	private File salvarPdf(byte[] pdf, Partida partida){
		File pdfDir = BolaoConfig.getDiretorioTemporario();
		
		String nomeArquivoPartida = getNomeArquivoPartida(partida);
		
		File pdfFile = new File(pdfDir, nomeArquivoPartida);
		
		gravarArquivo(pdf, pdfFile);
		
		return pdfFile;
	}

	private void gravarArquivo(byte[] pdf, File pdfFile) {
		try {
			Files.write(pdfFile.toPath(), pdf);
		} catch (IOException e) {
			throw new BolaoRuntimeException(e);
		}
	}

	private void notificarAdmins(byte[] pdf, Partida partida){
	
		List<Usuario> usuariosAdmins = findAdmins();
		
		for (Usuario usuario : usuariosAdmins) {
			
			File pdfFile = salvarPdf(pdf, partida);
			
			notificadorEmail.enviar("Palpites do Jogo "+partida.getDescricao(), usuario.getEmail(), "Segue em anexo.", new File[]{pdfFile});
			
		}
	}

	private List<Usuario> findUsuariosByPartida(Partida partidaDB) {
		return usuarioEAO.findByPartida(partidaDB);
	}

	public List<Usuario> findAdmins() {
		return usuarioEAO.findAdmins();
	}
	
	
}
