package org.javaee.bolao.usuariobolao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.javaee.bolao.beans.UsuarioBolaoVO;
import org.javaee.bolao.entidades.UsuarioBolao;
import org.javaee.bolao.security.Acesso;
import org.javaee.bolao.security.RestricaoAcesso;

@Path("usuarioboloes")
public class UsuarioBolaoFacadeREST {

	@Inject
	private UsuarioBolaoFacade usuarioFacade;

	@POST
	@Consumes({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso = Acesso.USUARIO)
	public void insertOrUpdate(UsuarioBolao usuarioBolao) {
		usuarioFacade.vincularUsuarioBolao(usuarioBolao.getUsuario().getIdUsuario(), usuarioBolao.getBolao().getIdBolao());
	}

	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") Long id) {
		this.usuarioFacade.delete(id);
	}

	@GET
	@Path("usuarios/{idUsuario}")
	@Produces({ "application/xml", "application/json" })
	@RestricaoAcesso(acesso=Acesso.USUARIO)
	public List<UsuarioBolaoVO> findAll(@PathParam("idUsuario") Long idUsuario) {

		List<UsuarioBolao> usuarioBoloes = this.usuarioFacade.findBoloesByUsuario(idUsuario);

		List<UsuarioBolaoVO> usuarioBoloesVO = new ArrayList<UsuarioBolaoVO>();

		for (UsuarioBolao usuarioBolao : usuarioBoloes) {
			usuarioBoloesVO.add(new UsuarioBolaoVO(usuarioBolao.getIdUsuarioBolao(), usuarioBolao.getBolao()));
		}

		return usuarioBoloesVO;
	}

}