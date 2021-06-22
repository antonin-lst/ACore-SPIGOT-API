package fr.azefgh456.acore.runnable;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import fr.azefgh456.acore.ACore;

public class SyncRunnable extends BukkitRunnable{

	private ACore plugin;
	
	private List<ARunnable> tickables = new ArrayList<>();
	public void addTickable(ARunnable tickable) { this.tickables.add(tickable);}
	
	public SyncRunnable(ACore plugin) {
		this.plugin = plugin;
		runTaskTimer(plugin, 0L, 20L);
	}
	
	@Override
	public void run() {
		for(ARunnable tickable : tickables) {
			try {
				tickable.ticks();
			//	logger.log(tickable.getClass().getName() + " ticks()");
			}catch(Exception e) {
				e.printStackTrace();
				plugin.logErr(e.getMessage());
			}
		}
	}

}
