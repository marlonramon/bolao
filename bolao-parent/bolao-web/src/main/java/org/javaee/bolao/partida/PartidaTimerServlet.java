package org.javaee.bolao.partida;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.ejb.Timer;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaee.bolao.entidades.Partida;
import org.javaee.bolao.util.DateUtil;

@WebServlet(urlPatterns="/PartidaTimer")
public class PartidaTimerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	@Inject
	private PartidaTimer partidaTimer;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter writer = resp.getWriter();
		
		writer.println("Timers das Partidas");
		
		Collection<Timer> timers = partidaTimer.findAllTimers();
		
		for (Timer timer : timers) {
			
			Partida partida = (Partida) timer.getInfo();
			
			writer.println(DateUtil.dd_MM_YYYY_HH_mm_ss.format(timer.getNextTimeout())+" | "+partida.getDescricao());
		}
		
		writer.flush();
		writer.close();
	}
	
}
