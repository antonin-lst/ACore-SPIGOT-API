package fr.acore.spigot.runnable.runnables;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.runnable.IRunnable;

public class SyncRunnable extends BukkitRunnable{

	private ACoreSpigotAPI plugin;
	
	private List<IRunnable> tickables = new ArrayList<>();
	public void addTickable(IRunnable tickable) { this.tickables.add(tickable);}
	
	public SyncRunnable(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		runTaskTimer(plugin, 0L, 20L);
	}
	
	@Override
	public void run() {
		for(IRunnable tickable : tickables) {
			try {
				tickable.ticks();
			//	logger.log(tickable.getClass().getName() + " ticks()");
			}catch(Exception e) {
				plugin.logErr(e);
			}
		}
	}

}
