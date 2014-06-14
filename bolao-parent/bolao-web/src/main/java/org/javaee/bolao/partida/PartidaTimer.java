package org.javaee.bolao.partida;

import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.javaee.bolao.entidades.Partida;

@Stateless
public class PartidaTimer {

	@Resource
    private TimerService timerService;
	
	@Inject
	private NotificadorPartida notificadorPartida;
	
	@Timeout
	public void comunicarInicioPartida(Timer timer){
		notificadorPartida.notificar(timer);
	}
	
	private Timer findTimerByPartida(Partida partida){
		Collection<Timer> allTimers = timerService.getAllTimers();
		
		for (Timer timer : allTimers) {
			Partida p = (Partida) timer.getInfo();
			if(p.equals(partida)){
				return timer;
			}
		}
		
		return null;
	}
	
	public Collection<Timer> findAllTimers(Partida partida){
		return timerService.getAllTimers();
	}
	
	public void agendar(Partida partida){
		cancelarTimerSeExistir(partida);
		criarTimer(partida);
	}

	private Timer criarTimer(Partida partida) {
		return timerService.createTimer(partida.getDataPartida(), partida);
	}
	
	public boolean cancelarTimerSeExistir(Partida partida){
		Timer timer = findTimerByPartida(partida);
		if(timer != null){
			timer.cancel();
			return true;
		}
		return false;
	}
	
}
