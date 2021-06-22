package fr.azefgh456.acore.player.players;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

import fr.azefgh456.acore.nms.INetMinecraftServer;
import fr.azefgh456.acore.nms.manager.NMSManager;
import fr.azefgh456.acore.nms.utils.ObjectiveMode;
import fr.azefgh456.acore.packets.utils.APacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

public abstract class NMSPlayer extends AbstractPlayer {

	private INetMinecraftServer nmsM;
	
	public NMSPlayer(OfflinePlayer player) {
		super(player);
		nmsM = ((NMSManager) plugin.getManager(NMSManager.class)).getNMS();
	}
	
	@Override
	public int getPing() {
		return nmsM.getPing(getPlayer());
	}
	
	@Override
	public void sendPacket(APacket<?> packet) {
		nmsM.sendPacket(getPlayer(), packet);
	}
	
	@Override
	public void sendPacketPlayOutEntityVelocity(int playerid, double x, double y, double z) {
		nmsM.sendPacketPlayOutEntityVelocity(getPlayer(), playerid, x, y, z);
	}
	
	public void sendPacketPlayOutPlayerListHeaderFooter(String header, String footer) {
		try {
			nmsM.sendPacketPlayOutPlayerListHeaderFooter(getPlayer(), header, footer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendPacketPlayOutScoreboardObjective(Objective obj, ObjectiveMode mode) {
		try {
			nmsM.sendPacketPlayOutScoreboardObjective(getPlayer(), obj, mode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendPacketPlayOutScoreboardDisplayObjective(Objective obj) {
		try {
			nmsM.sendPacketPlayOutScoreboardDisplayObjective(getPlayer(), obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendTitle(String message) {
		nmsM.sendTitlePacket(getPlayer(), message);
	}
	
	@Override
	public void sendTitle(String message, int fadin, int delay, int fadout) {
		sendTitle(message);
		sendTimesForTitle(fadin, delay, fadout);
	}
	
	@Override
	public void sendSubTitle(String message) {
		nmsM.sendSubTitlePacket(getPlayer(), message);
	}
	
	@Override
	public void sendSubTitle(String message, int fadin, int delay, int fadout) {
		sendSubTitle(message);
		sendTimesForTitle(fadin, delay, fadout);
	}
	
	@Override
	public void sendTimesForTitle(int fadin, int delay, int fadout) {
		nmsM.sendTimePacket(getPlayer(), fadin, delay, fadout);
	}
	
	public ChannelPipeline getPipeline() {
		return nmsM.getNettyPipeline((Player)player);
	}
	
	public Channel getChannel() {
		return nmsM.getNettyChannel((Player)player);
	}
	
	@Override
	public void clearTitle() {
		sendTitle(" ");
		sendTimesForTitle(1, 1, 1);
		sendSubTitle(" ");
		sendTimesForTitle(1, 1, 1);
	}
}
