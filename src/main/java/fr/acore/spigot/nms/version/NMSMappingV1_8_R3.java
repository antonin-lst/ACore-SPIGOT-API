package fr.acore.spigot.nms.version;

import java.lang.reflect.Field;

import fr.acore.spigot.api.nms.INMSPacket;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.nms.INetMinecraftServer;
import fr.acore.spigot.nms.utils.ObjectiveMode;
import fr.acore.spigot.utils.ReflexionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInTabComplete;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;

public class NMSMappingV1_8_R3 implements INetMinecraftServer{
	

	@Override
	public int getPing(CorePlayer<?> player) {
		return getEntityPlayer(player).ping;
	}
	
	public EntityPlayer getEntityPlayer(CorePlayer<?> player) {
		return ((CraftPlayer) player).getHandle();
	}
	
	@Override
	public void sendTimePacket(CorePlayer<?> player, int fadin, int delay, int fadout) {
		V1_8_R3Packet<PacketPlayOutTitle> packetPlayOutTitle = new V1_8_R3Packet<PacketPlayOutTitle>(new PacketPlayOutTitle(fadin, delay, fadout));
		sendPacket(player, packetPlayOutTitle);
	}
	
	@Override
	public void sendTitlePacket(CorePlayer<?> player, String title) {
		V1_8_R3Packet<PacketPlayOutTitle> packetPlayOutTitle = new V1_8_R3Packet<>(new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + title + "\"}")));
		sendPacket(player, packetPlayOutTitle);
	} 
	
	@Override
	public void sendSubTitlePacket(CorePlayer<?> player, String subTitle) {
		V1_8_R3Packet<PacketPlayOutChat> packetPlayOutChat = new V1_8_R3Packet<>(new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + subTitle + "\"}"), (byte)2));
		sendPacket(player, packetPlayOutChat);
	}
	
	@Override
	public void sendPacketPlayOutEntityVelocity(CorePlayer<?> player, int playerid, double x, double y, double z) {
		V1_8_R3Packet<PacketPlayOutEntityVelocity> packetPlayOutEntityVelocity = new V1_8_R3Packet<>(new PacketPlayOutEntityVelocity(playerid, x, y, z));
		sendPacket(player, packetPlayOutEntityVelocity);
	}
	
	@Override
	public void sendPacketPlayOutPlayerListHeaderFooter(CorePlayer<?> player, String header, String footer) throws Exception {
		IChatBaseComponent headerComponent = ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent footerComponent = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(headerComponent);
        Field field = (Field)packet.getClass().getDeclaredField("b");
        field.setAccessible(true);
        field.set(packet, footerComponent);
		V1_8_R3Packet<PacketPlayOutPlayerListHeaderFooter> packetPlayOutListHeaderFooter = new V1_8_R3Packet<>(packet);
		sendPacket(player, packetPlayOutListHeaderFooter);
	}
	
	
	@Override
	public void sendPacketPlayOutScoreboardObjective(CorePlayer<?> player, Objective obj, ObjectiveMode mode) throws Exception{
		ScoreboardObjective sbOjective = (ScoreboardObjective) ReflexionUtils.getMethod(obj, "getHandle").invoke(obj);
		V1_8_R3Packet<PacketPlayOutScoreboardObjective> packetPlayOutScoreboardObjective = new V1_8_R3Packet<>(new PacketPlayOutScoreboardObjective( sbOjective, mode.ordinal()));
		sendPacket(player, packetPlayOutScoreboardObjective);
	}

	@Override
	public void sendPacketPlayOutScoreboardDisplayObjective(CorePlayer<?> player, Objective obj) throws Exception{
		ScoreboardObjective sbOjective = (ScoreboardObjective) ReflexionUtils.getMethod(obj, "getHandle").invoke(obj);
		V1_8_R3Packet<PacketPlayOutScoreboardDisplayObjective> packetPlayOutScoreboardDisplayObjective = new V1_8_R3Packet<>(new PacketPlayOutScoreboardDisplayObjective(1, sbOjective));
		sendPacket(player, (INMSPacket<?>) packetPlayOutScoreboardDisplayObjective);
	}

	@Override
	public void sendPacketPlayOutScoreboardScore(CorePlayer<?> player, Scoreboard scoreboard, String name, int score, boolean remove) {
		
	}
	
	@Override
	public ChannelPipeline getNettyPipeline(CorePlayer<?> player) {
		return getNettyChannel(player).pipeline();
	}
	
	@Override
	public Channel getNettyChannel(CorePlayer<?> player) {
		return getEntityPlayer(player).playerConnection.networkManager.channel;
	}

	@Override
	public void sendScore(CorePlayer<?> player) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public <T extends Event> T convertPacketInboundToEvent(Object packet, CorePlayer<?> p) {
		// TODO Auto-generated method stub
		return null;
	}
		
	@Override
	public <T extends Event> T convertPacketOutboundToEvent(Object packet, CorePlayer<?> p) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static class V1_8_R3Packet<T extends Packet<?>> implements INMSPacket<T> {

		private T packet;
		
		public V1_8_R3Packet(T packet) {
			this.packet = packet;
		}

		@Override
		public void sendPacket(CorePlayer<?> player) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(getEncapsuledPacket());
		}

		@Override
		public T getEncapsuledPacket() {
			return this.packet;
		}

		@Override
		public int getId() {
			return -1;
		}
	}
	
	public class APacketPlayInTabComplete extends V1_8_R3Packet<PacketPlayInTabComplete>{

		
		public APacketPlayInTabComplete(PacketPlayInTabComplete packet) {
			super(packet);
		}
		
		
	}
}
