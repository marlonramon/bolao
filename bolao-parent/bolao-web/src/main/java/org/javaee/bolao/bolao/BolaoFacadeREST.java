package org.javaee.bolao.bolao;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javaee.bolao.entidades.Bolao;
import org.javaee.bolao.security.Acesso;
import org.javaee.bolao.security.RestricaoAcesso;
import org.javaee.bolao.vo.RankingBolaoVO;

@Path("boloes")
public class BolaoFacadeREST {

	@Inject
	private BolaoFacade bolaoFacade;

	@POST
	@Consumes({ "application/xml", "application/json" })
	public Bolao insertOrUpdate(Bolao bolao) {
		return bolaoFacade.insertOrUpdate(bolao);
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") Long id) {
		bolaoFacade.delete(id);
	}

	@GET
	@Path("{id}")
	@Produces({ "application/xml", "application/json" })
	public Bolao find(@PathParam("id") Long id) {
		return bolaoFacade.find(id);
	}

	@GET
	@RestricaoAcesso(acesso=Acesso.ANONIMO)
	@Produces({ "application/xml", "application/json" })
	public List<Bolao> findAll() {
		return bolaoFacade.findAll();
	}
	
	
	@GET
	@Path("/usuario/{id}")
	@RestricaoAcesso(acesso=Acesso.USUARIO)
	@Produces({ "application/xml", "application/json" })
	public List<Bolao> findByUsuario(@PathParam("id") Long idUsuario) {
		return null;
	}

	@GET
	@Path("{id}/ranking/{limite}")
	@Produces({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.USUARIO)
	public RankingBolaoVO ranking(@PathParam("id") Long id, @PathParam("limite") Integer limite) {
		return bolaoFacade.ranking(id, limite);
	}
}