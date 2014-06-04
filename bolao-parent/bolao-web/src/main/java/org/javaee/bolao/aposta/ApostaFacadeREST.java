package org.javaee.bolao.aposta;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javaee.bolao.entidades.Aposta;

@Path("apostas")
public class ApostaFacadeREST {

	@Inject
	private ApostaFacade apostaFacade;

//	@POST
//	@Consumes({ "application/xml", "application/json" })
//	public Aposta insertOrUpdate(Aposta aposta) {
//		return apostaFacade.insertOrUpdate(aposta);
//	}

//	@DELETE
//	@Path("{id}")
//	public void delete(@PathParam("id") Long id) {
//		apostaFacade.delete(id);
//	}

	@GET
	@Path("usuariobolao/{idUsuarioBolao}/rodada/{idRodada}")
	@Produces({ "application/xml", "application/json" })
	public List<Aposta> findByUsuarioAndRodada(@PathParam("idUsuarioBolao") Long idUsuarioBolao, @PathParam("idRodada") Long idRodada) {
		return apostaFacade.findApostas(idUsuarioBolao, idRodada);
	}

//	@GET
//	@Produces({ "application/xml", "application/json" })
//	public List<Aposta> findAll() {
//		return apostaFacade.findAll();
//	}
}