package fr.acore.spigot.nms;

import fr.acore.spigot.api.nms.INMSPacket;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import fr.acore.spigot.api.packet.IPacket;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.nms.utils.ObjectiveMode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

public interface INetMinecraftServer {

	default public void sendPacket(CorePlayer<?> player, INMSPacket<?> packet) {
		packet.sendPacket(player);
	}
	
	public int getPing(CorePlayer<?> player);
	
	public void sendTimePacket(CorePlayer<?> player, int fadin, int delay, int fadout);
	public void sendTitlePacket(CorePlayer<?> player, String title);
	public void sendSubTitlePacket(CorePlayer<?> player, String subTitle);
	
	public void sendPacketPlayOutEntityVelocity(CorePlayer<?> player, int playerid, double x, double y, double z);
	public void sendPacketPlayOutPlayerListHeaderFooter(CorePlayer<?> player, String header, String footer) throws Exception;

	public void sendPacketPlayOutScoreboardObjective(CorePlayer<?> player, Objective obj, ObjectiveMode mode) throws Exception;
	public void sendPacketPlayOutScoreboardDisplayObjective(CorePlayer<?> player, Objective obj) throws Exception;
	public void sendPacketPlayOutScoreboardScore(CorePlayer<?> player, Scoreboard scoreboard, String name, int score, boolean remove);
	public void sendScore(CorePlayer<?> player);

	public ChannelPipeline getNettyPipeline(CorePlayer<?> player);
	public Channel getNettyChannel(CorePlayer<?> player);
	
	public <T extends Event> T convertPacketInboundToEvent(Object packet, CorePlayer<?> p);
	public <T extends Event> T convertPacketOutboundToEvent(Object packet, CorePlayer<?> p);
}
