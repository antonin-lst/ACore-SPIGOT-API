package fr.acore.spigot.player.offline;

import java.util.UUID;

import org.bukkit.OfflinePlayer;

import fr.acore.spigot.api.player.impl.CorePlayer;

public abstract class OfflineCorePlayer implements fr.acore.spigot.api.player.impl.OfflineCorePlayer {

	protected OfflinePlayer offlinePlayer;

	public OfflineCorePlayer() {
		
	}
	
	@Override
	public OfflinePlayer getPlayer() {
		return this.offlinePlayer;
	}
	
	public OfflineCorePlayer setOfflinePlayer(OfflinePlayer offlinePlayer) {
		this.offlinePlayer = offlinePlayer;
		return this;
	}

	@Override
	public String getName() {
		return this.offlinePlayer.getName();
	}

	@Override
	public UUID getUuid() {
		return this.offlinePlayer.getUniqueId();
	}

	@Override
	public long getCurrentTimePlayed() {
		return this.offlinePlayer.getLastPlayed() - this.offlinePlayer.getFirstPlayed();
	}

	@Override
	public boolean isOnline() {
		return this instanceof CorePlayer;
	}
}