package fr.azefgh456.acore.event;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.config.utils.Conf;
import fr.azefgh456.acore.hook.hooks.ProtocolLibHook;
import fr.azefgh456.acore.manager.IManager;
import fr.azefgh456.acore.manager.utils.Informable;
import fr.azefgh456.acore.runnable.ARunnable;
import fr.azefgh456.acore.utils.time.TimerBuilder;

public class EventWrapper implements Listener, ARunnable, IManager, Informable{
	
	private ACore plugin;
	
	private ProtocolLibHook protocolHook;
	
	public EventWrapper(ACore plugin) {
		this.plugin = plugin;
		plugin.registerListener(this);
		plugin.registerAsyncRunnable(this);
		protocolHook = plugin.getHook("ProtocolLib");
		if(protocolHook.hisHooked()) PLibEventWrapper.registerProtocolLibEvent(plugin, this, protocolHook);
	}

	public void logs() {
		if(Conf.isVerbose()) {
			plugin.log(ChatColor.YELLOW + "PlayerKillPlayerEvent " + ChatColor.GREEN + "Wrapped");
			plugin.log(ChatColor.YELLOW + "PlayerTabRefreshEvent " + ChatColor.GREEN + "Wrapped");
			if(protocolHook.hisHooked())
				plugin.log(ChatColor.YELLOW + "PlayerTabCompeteEvent " + ChatColor.GREEN + "Wrapped");
		}
			
	}
	
	public static void callEvent(JavaPlugin plugin, Event event) {
		plugin.getServer().getPluginManager().callEvent(event);
	}
	
	public void call(Event e) {
		plugin.getServer().getPluginManager().callEvent(e);
		if(Conf.isVerbose() && Conf.isVerboseEvent())
			plugin.log("CallEvent : " + ChatColor.GREEN + e.getEventName());
	}
	
	@EventHandler
	public void onPlayerKillPlayer(PlayerDeathEvent event) {
		Player target = event.getEntity();
		if(target.getKiller() != null && target instanceof Player) {
			call(new PlayerKillPlayerEvent(target, target.getKiller()));
		}
	}
	
	private TimerBuilder timer;
	private void resetTimer(){ timer = new TimerBuilder(System.currentTimeMillis(), 3000L);}

	@Override
	public void ticks() {
		
		if(timer != null && !timer.hisFished()) return;
		
		if(timer == null || timer.hisFished()) resetTimer();
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			call(new PlayerTabRefreshEvent(player));
		}	
	}

}
