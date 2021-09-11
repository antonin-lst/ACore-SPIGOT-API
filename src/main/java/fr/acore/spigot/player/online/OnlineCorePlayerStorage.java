package fr.acore.spigot.player.online;

import java.util.*;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.nms.INMSPacket;
import fr.acore.spigot.hook.hooks.*;
import fr.acore.spigot.player.online.board.ABoard;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import fr.acore.spigot.api.faction.IFaction;
import fr.acore.spigot.api.faction.IFactionPlayer;
import fr.acore.spigot.api.packet.IPacket;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.player.impl.OfflineCorePlayer;
import fr.acore.spigot.api.storage.column.Column;
import fr.acore.spigot.api.storage.column.foreign.OneToMany;
import fr.acore.spigot.api.storage.table.Table;
import fr.acore.spigot.commands.data.CommandStorage;
import fr.acore.spigot.nms.INetMinecraftServer;
import fr.acore.spigot.nms.manager.NMSManager;
import fr.acore.spigot.player.offline.OfflineCorePlayerStorage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

@Table(name = "onlinePlayerStorage")
public class OnlineCorePlayerStorage implements CorePlayer<CommandStorage>{

	@Column(primary = true)
	private String uuid;
	
	@OneToMany
	private List<CommandStorage> commandCooldowns;
	
	private OfflineCorePlayerStorage offlineCorePlayer;
	
	private Player player;
	private IFactionPlayer factionPlayer;

	private boolean firstJoin;

	//instance du scoreboard
	private ABoard board;
	
	private INetMinecraftServer nmsM;

	//hooks
	private VaultEcoHook vaultEcoHook;
	private LuckPermHook luckPermHook;
	
	public OnlineCorePlayerStorage() {
		this.commandCooldowns = new ArrayList<>();
		nmsM = plugin.getInternalManager(NMSManager.class).getNMS();
		vaultEcoHook = plugin.getHook(VaultEcoHook.class);
		luckPermHook = plugin.getHook(LuckPermHook.class);
	}
	
	/*
	 * 
	 * OfflineCorePlayer gestion
	 * 
	 */
	
	@Override
	public OfflineCorePlayer getOfflinePlayer() {
		return this.offlineCorePlayer;
	}
	
	public OnlineCorePlayerStorage setOfflinePlayer(OfflineCorePlayerStorage offPlayer) {
		this.offlineCorePlayer = offPlayer;
		uuid = offlineCorePlayer.getUuid().toString();
		return this;
	}
	
	/*
	 * 
	 * OffllinePlayer information Accesseur
	 * 
	 */
	
	@Override
	public long getCurrentTimePlayed() {
		return offlineCorePlayer.getCurrentTimePlayed();
	}

	@Override
	public int getKills() {
		return offlineCorePlayer.getKills();
	}
	
	
	@Override
	public void addKill() {
		offlineCorePlayer.addKill();
	}
	
	@Override
	public void addDeath() {
		offlineCorePlayer.addDeath();
	}

	@Override
	public int getMorts() {
		return offlineCorePlayer.getMorts();
	}

	@Override
	public double getRatio() {
		return offlineCorePlayer.getRatio();
	}

	@Override
	public String getFormatedRatio() {
		return offlineCorePlayer.getFormatedRatio();
	}

	@Override
	public boolean isOnline() {
		return true;
	}

	@Override
	public String getPremiumUuid() {
		return offlineCorePlayer.getPremiumUuid();
	}

	@Override
	public String getName() {
		return offlineCorePlayer.getName();
	}

	@Override
	public UUID getUuid() {
		return offlineCorePlayer.getUuid();
	}

	@Override
	public String getEncryptedPassword() {
		return offlineCorePlayer.getEncryptedPassword();
	}

	@Override
	public void setEncryptedPassword(String password) {
		offlineCorePlayer.setEncryptedPassword(password);
	}

	@Override
	public boolean isPremium() {
		return offlineCorePlayer.isPremium();
	}


	/*


	Mapping CommandSender interface


	 */

	@Override
	public boolean isPermissionSet(String name) {
		return player.isPermissionSet(name);
	}

	@Override
	public boolean isPermissionSet(Permission perm) {
		return player.isPermissionSet(perm);
	}

	@Override
	public boolean hasPermission(String name) {
		return player.hasPermission(name);
	}

	@Override
	public boolean hasPermission(Permission perm) {
		return player.hasPermission(perm);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
		return player.addAttachment(plugin, name, value);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin) {
		return player.addAttachment(plugin);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
		return player.addAttachment(plugin, name, value, ticks);
	}

	@Override
	public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
		return player.addAttachment(plugin, ticks);
	}

	@Override
	public void removeAttachment(PermissionAttachment attachment) {
		player.removeAttachment(attachment);
	}

	@Override
	public void recalculatePermissions() {
		player.recalculatePermissions();
	}

	@Override
	public Set<PermissionAttachmentInfo> getEffectivePermissions() {
		return player.getEffectivePermissions();
	}

	@Override
	public boolean isOp() {
		return player.isOp();
	}

	@Override
	public void setOp(boolean value) {
		player.setOp(value);
	}



	/*
	 * 
	 * Gestion de l'instance du joueur
	 * 
	 */

	@Override
	public Player getPlayer() {
		return this.player;
	}
	
	public OnlineCorePlayerStorage setPlayer(Player player) {
		this.player = player;
		return this;
	}
	
	/*
	 * 
	 * Gestion de l'envoie d'un message
	 * 
	 */

	@Override
	public void sendMessage(String message) {
		player.sendMessage(message);
	}

	@Override
	public void sendMessage(String... message) {
		player.sendMessage(message);
	}

	@Override
	public Server getServer() {
		return player.getServer();
	}

	@Override
	public void sendMessage(Object... message) {
		for(Object m : message)
			player.sendMessage(m.toString());
	}
	
	/*
	 * 
	 * Gestion des cooldowns des commandes
	 * 
	 */

	@Override
	public List<CommandStorage> getCommandsCooldowns() {
		return this.commandCooldowns;
	}

	/*

	Gestion premiere connection au serveur

	 */

	@Override
	public boolean isFirstJoin() {
		return firstJoin;
	}

	@Override
	public void setFirstJoin(boolean firstJoin) {
		this.firstJoin = firstJoin;
	}

	/*
	 * 
	 * Integration NMS
	 * 
	 */
	
	
	@Override
	public int getPing() {
		return nmsM.getPing(this);
	}
	
	@Override
	public void sendPacket(INMSPacket<?> packet) {
		packet.sendPacket(this);
	}

	@Override
	public void sendTitle(String message) {
		nmsM.sendTitlePacket(this, message);
	}
	
	@Override
	public void sendTitle(String message, int fadin, int delay, int fadout) {
		sendTitle(message);
		sendTimesForTitle(fadin, delay, fadout);
	}
	
	@Override
	public void sendSubTitle(String message) {
		nmsM.sendSubTitlePacket(this, message);
	}
	
	@Override
	public void sendSubTitle(String message, int fadin, int delay, int fadout) {
		sendSubTitle(message);
		sendTimesForTitle(fadin, delay, fadout);
	}
	
	@Override
	public void sendTimesForTitle(int fadin, int delay, int fadout) {
		nmsM.sendTimePacket(this, fadin, delay, fadout);
	}
	
	@Override
	public void sendPacketPlayOutEntityVelocity(int playerid, double x, double y, double z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendPacketPlayOutPlayerListHeaderFooter(String header, String footer) {
		try {
			nmsM.sendPacketPlayOutPlayerListHeaderFooter(this, header, footer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clearTitle() {
		sendTitle(" ");
		sendTimesForTitle(1, 1, 1);
		sendSubTitle(" ");
		sendTimesForTitle(1, 1, 1);
	}
	
	//Netty Integration avec les NMS
	public ChannelPipeline getPipeline() {
		return nmsM.getNettyPipeline(this);
	}
	
	public Channel getChannel() {
		return nmsM.getNettyChannel(this);
	}

	/*

	Gestion du scorboard d'un joueur

	 */

	@Override
	public void initBoard(String name) {
		initBoard(name, new ArrayList<>());
	}

	public void initBoard(String name, List<String> lines){
		initBoard(name, lines, false);
	}

	@Override
	public void initBoard(String name, List<String> lines, boolean healthBare) {
		this.board = new ABoard(player, name);
		if(lines != null && !lines.isEmpty()){
			int i = 1;
			for(String line : lines){
				this.board.addLine(i, line);
				i++;
			}
		}

		if(healthBare) this.board.addHealthBare();

	}


	public ABoard getBoard(){ return this.board;}



	/*
	 * 
	 * Integration du FactionHook
	 * 
	 */
	
	@Override
	public CorePlayer<?> setIFactionPlayer(IFactionPlayer factionPlayer) {
		this.factionPlayer = factionPlayer;
		return this;
	}
	
	@Override
	public IFaction getFaction() {
		return factionPlayer.getFaction();
	}

	@Override
	public double getPower() {
		return factionPlayer.getPower();
	}

	@Override
	public String getChatTag(CorePlayer<?> player) {
		return factionPlayer.getChatTag(player);
	}

	@Override
	public String getFactionGrade() {
		return factionPlayer.getFactionGrade();
	}
	
	/*
	 * 
	 * Integration de VaultHook
	 * 
	 */

	@Override
	public void addMoney(double somme) {
		if(!vaultEcoHook.isHooked()) {
			plugin.log("Vault n'est pas disponible");
			return;
		}
		vaultEcoHook.getHook().depositPlayer(player, somme);
	}

	@Override
	public void addMoney(int somme) {
		if(!vaultEcoHook.isHooked()) {
			plugin.log("Vault n'est pas disponible");
			return;
		}
		vaultEcoHook.getHook().depositPlayer(player, somme);
	}

	@Override
	public double getBalance() {
		if(!vaultEcoHook.isHooked()) {
			plugin.log("Vault n'est pas disponible");
			return 0;
		}
		return vaultEcoHook.getHook().getBalance(player);
	}

	@Override
	public void removeMoney(double somme) {
		if(!vaultEcoHook.isHooked()) {
			plugin.log("Vault n'est pas disponible");
			return;
		}

		vaultEcoHook.getHook().withdrawPlayer(player, somme);
	}

	@Override
	public void removeMoney(int somme) {
		if(!vaultEcoHook.isHooked()) {
			plugin.log("Vault n'est pas disponible");
			return;
		}
		vaultEcoHook.getHook().withdrawPlayer(player, somme);
	}

	@Override
	public boolean removeMoneySafe(double somme) {
		if(!vaultEcoHook.isHooked()) {
			plugin.log("Vault n'est pas disponible");
			return false;
		}

		if(getBalance() < somme) return false;

		removeMoney(somme);
		return true;
	}

	@Override
	public boolean removeMoneySafe(int somme) {
		if(!vaultEcoHook.isHooked()) {
			plugin.log("Vault n'est pas disponible");
			return false;
		}

		if(getBalance() < somme) return false;

		removeMoney(somme);
		return true;
	}

	/*

	Integration de LuckPerm

	 */

	@Override
	public String getPermissionPrefix() {
		if(!luckPermHook.isHooked()) {
			plugin.log("LuckPerms n'est pas disponible");
			return null;
		}
		User user = luckPermHook.getHook().getUserManager().getUser(player.getUniqueId());
		Optional<QueryOptions> options = LuckPermsProvider.get().getContextManager().getQueryOptions(user);
		if (!options.isPresent())
			return "";
		String prefix = user.getCachedData().getMetaData(options.get()).getPrefix();
		return (prefix == null) ? "" : prefix;
	}

	/*
	 * 
	 * Integration de WorldGuard Hook
	 * 
	 */

	@Override
	public boolean isInRegion(String name) {
		// TODO Auto-generated method stub
		return false;
	}


}
