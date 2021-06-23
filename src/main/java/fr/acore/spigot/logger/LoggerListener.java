package fr.acore.spigot.logger;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.acore.spigot.api.runnable.IRunnable;

public class LoggerListener implements Listener, IRunnable{

	@SuppressWarnings("unused")
	private LoggerManager manager;
	
	private Queue<LogDispatchEvent<?>> logDispatched;
	
	public LoggerListener(LoggerManager manager) {
		this.manager = manager;
		logDispatched = new LinkedList<>();
	}
	
	@EventHandler
	public void onLogDispatchEvent(LogDispatchEvent<?> event) {
		logDispatched.add(event);
	}

	@Override
	public void ticks() {
		
		while(!logDispatched.isEmpty()) {
			LogDispatchEvent<?> log = logDispatched.poll();
			try {
				log.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

		
	}
}
