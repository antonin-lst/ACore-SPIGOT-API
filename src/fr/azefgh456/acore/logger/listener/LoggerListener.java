package fr.azefgh456.acore.logger.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.azefgh456.acore.logger.event.LogDispatchEvent;
import fr.azefgh456.acore.logger.manager.LoggerManager;
import fr.azefgh456.acore.runnable.ARunnable;

public class LoggerListener implements Listener, ARunnable{

	private LoggerManager manager;
	
	private boolean isRegistered;
	private List<LogDispatchEvent<?>> logDispatched;
	
	public LoggerListener(LoggerManager manager) {
		this.manager = manager;
		logDispatched = new ArrayList<>();
	}
	
	@EventHandler
	public void onLogDispatchEvent(LogDispatchEvent<?> event) {
		logDispatched.add(event);
		if(!isRegistered) {
			try {
				manager.registerAsyncRunnable(this);
				this.isRegistered = true;
			}catch(NullPointerException e) {
			}
		}
		
	}

	@Override
	public void ticks() {

		Iterator<LogDispatchEvent<?>> logIterator = logDispatched.iterator();
		
		while(logIterator.hasNext()) {
			LogDispatchEvent<?> log = logIterator.next();
			try {
				log.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logIterator.remove();
		}
		
	}
}
