package fr.acore.spigot.runnable.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.api.runnable.IRunnable;
import fr.acore.spigot.api.runnable.RunnableUsage;
import fr.acore.spigot.runnable.runnables.AsyncRunnable;
import fr.acore.spigot.runnable.runnables.SyncRunnable;

public class RunnableManager implements IManager{

	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin(){ return this.plugin;}
	
	private SyncRunnable runnable;
	private Map<RunnableUsage, AsyncRunnable> asyncRunnables;
	
	public RunnableManager(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		runnable = new SyncRunnable(plugin);
		asyncRunnables = new HashMap<>();
		asyncRunnables.put(RunnableUsage.RENDER, new AsyncRunnable(plugin));
		asyncRunnables.put(RunnableUsage.SECURITY, new AsyncRunnable(plugin));
		asyncRunnables.put(RunnableUsage.LONG_TASK, new AsyncRunnable(plugin));
	}

	public void addTickable(IRunnable tickable) {
		this.runnable.addTickable(tickable);
	}
	
	public void addAsyncTickable(RunnableUsage usage, IRunnable tickable) {
		this.asyncRunnables.get(usage).addTickable(tickable);
	}
	
	public void removeAsyncTickable(RunnableUsage usage, IRunnable tickable) {
		this.asyncRunnables.get(usage).removeTickable(tickable);
	}

	public void disable() {
		Bukkit.getScheduler().cancelTasks(plugin);
	}

	/*
	 * 
	 * Gestion des logs
	 * 
	 */
	
	@Override
	public void log(String... args) {
		plugin.log(args);
	}
	
	@Override
	public void log(Object... args) {
		plugin.log(args);
	}

	@Override
	public void logWarn(String... args) {
		plugin.logWarn(args);
	}
	
	@Override
	public void logWarn(Object... args) {
		plugin.logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		plugin.logErr(args);
	}

	@Override
	public void logErr(Object... args) {
		plugin.logErr(args);
	}
}
