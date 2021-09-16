package fr.acore.spigot.player.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.player.IPlayer;
import fr.acore.spigot.api.player.IPlayerManager;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.player.impl.OfflineCorePlayer;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.player.factory.OfflinePlayerFactory;
import fr.acore.spigot.player.factory.OnlinePlayerFactory;
import fr.acore.spigot.player.listener.PlayerListener;
import fr.acore.spigot.player.offline.OfflineCorePlayerStorage;
import fr.acore.spigot.player.online.OnlineCorePlayerStorage;

public class PlayerManager implements IPlayerManager{
	
	/*
	 * 
	 * Instance du plugin au quelle apartient le manager
	 * 
	 */
	
	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin(){ return this.plugin;}
	
	/*
	 * 
	 * liste des joueurs
	 * 
	 */
	
	private List<IPlayer<?>> players;
	
	/*
	 * 
	 * instance des factory
	 * 
	 */
	
	private OfflinePlayerFactory offlinePlayerFactory;
	private OnlinePlayerFactory onlinePlayerFactory;
	
	public PlayerManager(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		this.players = new ArrayList<>();
		getPlugin().registerDataFactory(this.offlinePlayerFactory = new OfflinePlayerFactory(this));
		getPlugin().registerDataFactory(this.onlinePlayerFactory = new OnlinePlayerFactory(this));
		plugin.registerListener(new PlayerListener(this));
	}
	
	
	/*
	 * 
	 * gestion des joueurs de tout type
	 * 
	 */
	
	@Override
	public List<IPlayer<?>> getPlayers() {
		return this.players;
	}
	
	@Override
	public void addPlayer(OfflineCorePlayer offPlayer) {
		this.players.add(offPlayer);
	}
	
	/*
	 * 
	 * gestion de l'etat d'un joueur (offline/online)
	 * 
	 */
	
	@Override
	public void setPlayerOnline(OfflineCorePlayer offPlayer, Player player) {
		CorePlayer<?> onlinePlayer;
		boolean firstJoin;
		if(offPlayer == null){
			offPlayer = new OfflineCorePlayerStorage(player);
			firstJoin = true;
		}else{
			firstJoin = false;
		}
		if(onlinePlayerFactory == null){
			System.out.println("wtffffffffffffffffffff");
			return;
		}
		onlinePlayer = onlinePlayerFactory.load(offPlayer.getUuid().toString());
		if(onlinePlayer == null){
			System.out.println("Debug onlinePlayer == null ifneigeibg");
			return;
		}
		((OnlineCorePlayerStorage) onlinePlayer).setOfflinePlayer((OfflineCorePlayerStorage) offPlayer).setPlayer(player);
		onlinePlayer.setFirstJoin(firstJoin);
		players.remove(offPlayer);
		players.add(onlinePlayer);
	}
	
	@Override
	public void setPlayerOffline(CorePlayer<?> player) {
		OfflineCorePlayer offPlayer = player.getOfflinePlayer();
		onlinePlayerFactory.save((OnlineCorePlayerStorage) player);
		players.remove(player);
		offlinePlayerFactory.save((OfflineCorePlayerStorage) offPlayer);
		players.add(offPlayer);
	}
	
	/*
	 * 
	 * Getter pour OnlinePlayer
	 * 
	 */

	@Override
	public List<CorePlayer<?>> getOnlineCorePlayers() {
		List<CorePlayer<?>> corePlayers = new ArrayList<>();
		players.stream().filter(player -> player.getPlayer() instanceof Player).forEach(player -> corePlayers.add((CorePlayer<?>) player));
		return corePlayers;
	}

	@Override
	public CorePlayer<?> getOnlinePlayer(String name) {
		for(IPlayer<?> p : players) {
			if(p.getName().equals(name) && p.getPlayer() instanceof Player) return (CorePlayer<?>) p;
		}
		return null;
	}

	@Override
	public CorePlayer<?> getOnlinePlayer(UUID uuid) {
		for(IPlayer<?> p : players) {
			if(p.getUuid().equals(uuid) && p.getPlayer() instanceof Player) return (CorePlayer<?>) p;
		}
		return null;
	}

	@Override
	public CorePlayer<?> getOnlinePlayer(Player player) {
		for(IPlayer<?> p : players) {
			if(p.getPlayer() instanceof Player && p.getPlayer().equals(player)) return (CorePlayer<?>) p;
		}
		return null;
	}
	
	/*
	 * 
	 * Getter pour OfflinePlayer
	 * 
	 */

	@Override
	public List<OfflineCorePlayer> getOfflineCorePlayers() {
		List<OfflineCorePlayer> corePlayers = new ArrayList<>();
		players.stream().filter(player -> player.getPlayer() instanceof OfflinePlayer).forEach(player -> corePlayers.add((OfflineCorePlayer) player));
		return corePlayers;
	}

	@Override
	public OfflineCorePlayer getOfflineCorePlayer(String name) {
		for(IPlayer<?> p : players) {
			if(p.getName().equals(name) && p.getPlayer() instanceof Player) return (CorePlayer<?>) p;
		}
		return null;
	}

	@Override
	public OfflineCorePlayer getOfflineCorePlayer(UUID uuid) {
		for(IPlayer<?> p : players) {
			if(p.getUuid().equals(uuid) && p.getPlayer() instanceof OfflinePlayer) return (OfflineCorePlayer) p;
		}
		return null;
	}

	@Override
	public OfflineCorePlayer getOfflineCorePlayer(OfflinePlayer offPlayer) {
		for(IPlayer<?> p : players) {
			if(p.getPlayer() instanceof OfflinePlayer && p.getPlayer().equals(offPlayer)) return (OfflineCorePlayer) p;
		}
		return null;
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