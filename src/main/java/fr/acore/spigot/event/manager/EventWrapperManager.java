package fr.acore.spigot.event.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.manager.Informable;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.api.runnable.IRunnable;
import fr.acore.spigot.api.runnable.RunnableUsage;
import fr.acore.spigot.api.timer.ITimer;
import fr.acore.spigot.config.utils.Conf;
import fr.acore.spigot.event.events.player.PlayerTabRefreshEvent;
import fr.acore.spigot.event.listener.EventWrapperListener;
import fr.acore.spigot.utils.time.TimerBuilder;

public class EventWrapperManager implements IManager, IRunnable, Informable{

	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin(){ return this.plugin;}
	
	private ITimer timer;
	private void resetTimer(){ timer = new TimerBuilder(3000L);}

	
	public EventWrapperManager(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		plugin.registerListener(new EventWrapperListener(this));
		plugin.registerAsyncRunnable(RunnableUsage.RENDER, this);
	}

	public void informe() {
		if(Conf.isVerbose()) {
			log(ChatColor.YELLOW + "PlayerKillPlayerEvent " + ChatColor.GREEN + "Wrapped");
			log(ChatColor.YELLOW + "PlayerTabRefreshEvent " + ChatColor.GREEN + "Wrapped");
			/*if(protocolHook.hisHooked())
				log(ChatColor.YELLOW + "PlayerTabCompeteEvent " + ChatColor.GREEN + "Wrapped");*/
		}
	}
	
	public void call(Event e) {
		plugin.getServer().getPluginManager().callEvent(e);
		if(Conf.isVerbose() && Conf.isVerboseEvent())
			plugin.log("CallEvent : " + ChatColor.GREEN + e.getEventName());
	}
	
	@Override
	public void ticks() {
		
		if(timer != null && !timer.isFinish()) return;
		
		if(timer == null || timer.isFinish()) resetTimer();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			call(new PlayerTabRefreshEvent(player));
		}	
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
