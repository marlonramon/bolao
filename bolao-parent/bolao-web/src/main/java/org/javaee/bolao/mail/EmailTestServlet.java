package org.javaee.bolao.mail;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * http://localhost:8080/bolao-web/EmailTest?email=thiago.busarello@datacoper.com
 */

@WebServlet(urlPatterns="/EmailTest")
public class EmailTestServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Inject
	private NotificadorEmail notificadorEmail;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String email = req.getParameter("email");
		
		PrintWriter writer = resp.getWriter();
		
		writer.println("Teste de Envio de Email");
		
		if(email != null){
			notificadorEmail.enviar("Teste", email, "teste");	
		}else{
			writer.println("Parametro email nao informado.");	
			
		}
		
		writer.flush();
		writer.close();
	}
	
}
