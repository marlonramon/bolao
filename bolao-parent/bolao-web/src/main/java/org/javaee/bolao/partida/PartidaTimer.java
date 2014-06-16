package org.javaee.bolao.partida;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.javaee.bolao.eao.PartidaEAO;
import org.javaee.bolao.entidades.Partida;

@Stateless
public class PartidaTimer {

	@Resource
    private TimerService timerService;
	
	@Inject
	private NotificadorPartida notificadorPartida;
	
	@Inject
	private PartidaEAO partidaEAO;
	
	@Timeout
	public void comunicarInicioPartida(Timer timer){
		notificadorPartida.notificar(timer.getNextTimeout(), (Partida)timer.getInfo());
	}
	
	public void restaurarTimers(){
		List<Partida> partidasPosteriores = partidaEAO.findPartidasPosteriores(new Date());
		
		for (Partida partida : partidasPosteriores) {
			agendar(partida);
		}
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
	
	public List<Timer> findAllTimers(){
		List<Timer> allTimers = new ArrayList<>(timerService.getAllTimers());
		
		Collections.sort(allTimers, new Comparator<Timer>() {
			@Override
			public int compare(Timer t1, Timer t2) {
				return t1.getNextTimeout().compareTo(t2.getNextTimeout());
			}
		});
		
		return allTimers;
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
