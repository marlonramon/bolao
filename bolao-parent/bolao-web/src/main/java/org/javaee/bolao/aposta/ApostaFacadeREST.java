package org.javaee.bolao.aposta;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javaee.bolao.entidades.Aposta;
import org.javaee.bolao.entidades.Placar;
import org.javaee.bolao.security.Acesso;
import org.javaee.bolao.security.RestricaoAcesso;
import org.javaee.rest.common.XmlUtil;

@Path("apostas")
public class ApostaFacadeREST {

	@Inject
	private ApostaFacade apostaFacade;

	@POST
	@Consumes({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.USUARIO)
	public void insertOrUpdate(List<Aposta> apostas) {
		apostaFacade.gravar(apostas);
	}



	@GET
	@Path("usuariobolao/{idUsuarioBolao}/rodada/{idRodada}")
	@Produces({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.USUARIO)
	public List<Aposta> findByUsuarioAndRodada(@PathParam("idUsuarioBolao") Long idUsuarioBolao, @PathParam("idRodada") Long idRodada) {
		return apostaFacade.findApostas(idUsuarioBolao, idRodada);
	}
	
	
	public static void main(String[] args) {
		
		Aposta aposta = new Aposta();
		aposta.setIdAposta(10L);
		aposta.setPlacar(new Placar());
		
		System.out.println(XmlUtil.toString(aposta, false));
		
	}

}