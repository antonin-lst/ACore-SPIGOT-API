package fr.azefgh456.acore.nms;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import fr.azefgh456.acore.nms.utils.ObjectiveMode;
import fr.azefgh456.acore.packets.utils.APacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

public interface INetMinecraftServer {
	
	default public void sendPacket(Player player, APacket<?> packet) {
		packet.sendPacket(player);
	}
	
	public int getPing(Player player);
	
	public void sendTimePacket(Player player, int fadin, int delay, int fadout);
	public void sendTitlePacket(Player player, String title);
	public void sendSubTitlePacket(Player player, String subTitle);
	
	public void sendPacketPlayOutEntityVelocity(Player player, int playerid, double x, double y, double z);
	public void sendPacketPlayOutPlayerListHeaderFooter(Player player, String header, String footer) throws Exception;

	public void sendPacketPlayOutScoreboardObjective(Player player, Objective obj, ObjectiveMode mode) throws Exception;
	public void sendPacketPlayOutScoreboardDisplayObjective(Player player, Objective obj) throws Exception;
	public void sendPacketPlayOutScoreboardScore(Player player, Scoreboard scoreboard, String name, int score, boolean remove);
	public void sendScore(Player player);

	public ChannelPipeline getNettyPipeline(Player player);
	public <T extends Event> T convertPacketInboundToEvent(Object packet, Player p);
	public <T extends Event> T convertPacketOutboundToEvent(Object packet, Player p);

	public Channel getNettyChannel(Player player);
}
