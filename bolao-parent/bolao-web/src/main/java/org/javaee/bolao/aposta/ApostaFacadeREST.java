package org.javaee.bolao.aposta;

import javax.inject.Inject;

//@Path("apostas")
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

//	@GET
//	@Path("{id}")
//	@Produces({ "application/xml", "application/json" })
//	public Aposta find(@PathParam("id") Long id) {
//		return apostaFacade.find(id);
//	}

//	@GET
//	@Produces({ "application/xml", "application/json" })
//	public List<Aposta> findAll() {
//		return apostaFacade.findAll();
//	}
}