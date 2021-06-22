package fr.azefgh456.acore.runnable;

import org.bukkit.Bukkit;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.manager.IManager;

public class CoreRunnable implements IManager{
	
	private ACore plugin;
	
	private SyncRunnable runnable;
	private AsyncRunnable asyncRunnable;
	
	public CoreRunnable(ACore plugin) {
		this.plugin = plugin;
		runnable = new SyncRunnable(plugin);
		asyncRunnable = new AsyncRunnable(plugin);
	}
	
	public ACore getACore() {
		return this.plugin;
	}

	public void addTickable(ARunnable tickable) {
		this.runnable.addTickable(tickable);
	}
	
	public void addAsyncTickable(ARunnable tickable) {
		this.asyncRunnable.addTickable(tickable);
	}

	public void disable() {
		Bukkit.getScheduler().cancelTasks(plugin);
	}

	public double getTps() {
		return asyncRunnable.getTps();
	}
	
	
}
