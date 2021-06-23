package fr.acore.spigot.api.player;

import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.player.impl.OfflineCorePlayer;

public interface IPlayerManager extends IManager{

	public List<IPlayer<?>> getPlayers();
	public void addPlayer(OfflineCorePlayer offPlayer);
	
	public void setPlayerOnline(OfflineCorePlayer offPlayer, Player player);
	public void setPlayerOffline(CorePlayer<?> player);
	
	public List<CorePlayer<?>> getOnlineCorePlayers();
	
	public CorePlayer<?> getOnlinePlayer(String name);
	public CorePlayer<?> getOnlinePlayer(UUID uuid);
	public CorePlayer<?> getOnlinePlayer(Player offPlayer);
	
	
	public List<OfflineCorePlayer> getOfflineCorePlayers();
	
	public OfflineCorePlayer getOfflineCorePlayer(String name);
	public OfflineCorePlayer getOfflineCorePlayer(UUID uuid);
	public OfflineCorePlayer getOfflineCorePlayer(OfflinePlayer offPlayer);
	
}
