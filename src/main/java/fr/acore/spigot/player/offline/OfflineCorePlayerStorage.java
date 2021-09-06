package fr.acore.spigot.player.offline;

import org.bukkit.OfflinePlayer;

import fr.acore.spigot.api.storage.column.Column;
import fr.acore.spigot.api.storage.table.Table;

@Table(name = "playerStorage")
public class OfflineCorePlayerStorage extends OfflineCorePlayer{


	@Column(primary = true, isUnique = true)
	private String uuid;
	
	@Column()
	private String name;
	
	@Column()
	private int kills;
	
	@Column()
	private int morts;
	
	@Column()
	private String encryptedPassword;

	@Column()
	private boolean premium;

	@Column()
	private String premiumUuid;
	
	public OfflineCorePlayerStorage(OfflinePlayer offlinePlayer) {
		setOfflinePlayer(offlinePlayer);
		this.uuid = offlinePlayer.getUniqueId().toString();
		this.name = offlinePlayer.getName();
	}
	
	public OfflineCorePlayerStorage() {
		
	}

	@Override
	public OfflineCorePlayer setOfflinePlayer(OfflinePlayer offlinePlayer) {
		this.uuid = offlinePlayer.getUniqueId().toString();
		return super.setOfflinePlayer(offlinePlayer);
	}
	
	@Override
	public int getKills() {
		return this.kills;
	}

	@Override
	public int getMorts() {
		return this.morts;
	}
	
	@Override
	public void addKill() {
		this.kills++;
	}
	
	@Override
	public void addDeath() {
		this.morts++;
	}

	@Override
	public double getRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getFormatedRatio() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	@Override
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	@Override
	public boolean isPremium() {
		return premium;
	}

	public String getPremiumUuid() {
		return premiumUuid;
	}
}
