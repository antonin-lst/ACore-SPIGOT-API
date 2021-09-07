package fr.acore.spigot.player.online;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import fr.acore.spigot.api.nms.INMSPacket;
import fr.acore.spigot.player.online.board.ABoard;
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

@Table(name = "onlinePlayerStorage")
public class OnlineCorePlayerStorage implements CorePlayer<CommandStorage>{

	@Column(primary = true)
	private String uuid;
	
	@OneToMany
	private List<CommandStorage> commandCooldowns;
	
	private OfflineCorePlayerStorage offlineCorePlayer;
	
	private Player player;
	private IFactionPlayer factionPlayer;

	//instance du scoreboard
	private ABoard board;
	
	private INetMinecraftServer nmsM;
	
	public OnlineCorePlayerStorage() {
		this.commandCooldowns = new ArrayList<>();
		nmsM = plugin.getInternalManager(NMSManager.class).getNMS();
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

	public void initBoard(String name){
		this.board = new ABoard(player, name);
		this.board.refreshBoard();
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
	public double getBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeMoney(double somme) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMoney(int somme) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeMoneySafe(double somme) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeMoneySafe(int somme) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addMoney(double somme) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMoney(int somme) {
		// TODO Auto-generated method stub
		
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
