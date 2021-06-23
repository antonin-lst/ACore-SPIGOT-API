package fr.acore.spigot.event.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.acore.spigot.event.events.player.PlayerKillPlayerEvent;
import fr.acore.spigot.event.manager.EventWrapperManager;

public class EventWrapperListener implements Listener{

	private EventWrapperManager manager;
	
	public EventWrapperListener(EventWrapperManager manager) {
		this.manager = manager;
	}
	
	@EventHandler
	public void onPlayerKillPlayer(PlayerDeathEvent event) {
		Player target = event.getEntity();
		if(target.getKiller() != null && target instanceof Player) {
			manager.call(new PlayerKillPlayerEvent(manager.getPlugin().getCorePlayer(target), manager.getPlugin().getCorePlayer(target.getKiller())));
		}
	}
	
	
}
