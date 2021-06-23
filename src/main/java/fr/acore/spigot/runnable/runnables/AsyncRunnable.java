package fr.acore.spigot.runnable.runnables;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.runnable.IRunnable;

public class AsyncRunnable extends BukkitRunnable{

	private ACoreSpigotAPI plugin;
	
	private List<IRunnable> tickables;
	public void addTickable(IRunnable tickable) { this.tickables.add(tickable);}
	
	public AsyncRunnable(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		this.tickables = new ArrayList<>();
		runTaskTimerAsynchronously(plugin, 0L, 20L);
	}
	
	@Override
	public void run() {
		for(IRunnable tickable : tickables) {
			try {
				//plugin.log(tickable.getClass().getName() + " ticks()");
				tickable.ticks();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void removeTickable(IRunnable tickable) {
		this.tickables.remove(tickable);
	}
}
