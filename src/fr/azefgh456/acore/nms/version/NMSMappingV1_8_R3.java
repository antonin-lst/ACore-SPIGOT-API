package fr.azefgh456.acore.nms.version;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import fr.azefgh456.acore.nms.INetMinecraftServer;
import fr.azefgh456.acore.nms.utils.ObjectiveMode;
import fr.azefgh456.acore.packets.event.PlayerTabCompleteEvent;
import fr.azefgh456.acore.packets.utils.APacket;
import fr.azefgh456.acore.utils.ReflexionUtils;
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
	public int getPing(Player player) {
		return getEntityPlayer(player).ping;
	}
	
	public EntityPlayer getEntityPlayer(Player player) {
		return ((CraftPlayer) player).getHandle();
	}
	
	@Override
	public void sendTimePacket(Player player, int fadin, int delay, int fadout) {
		V1_8_R3Packet<PacketPlayOutTitle> packetPlayOutTitle = new V1_8_R3Packet<PacketPlayOutTitle>(new PacketPlayOutTitle(fadin, delay, fadout));
		sendPacket(player, packetPlayOutTitle);
	}
	
	@Override
	public void sendTitlePacket(Player player, String title) {
		V1_8_R3Packet<PacketPlayOutTitle> packetPlayOutTitle = new V1_8_R3Packet<>(new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"" + title + "\"}")));
		sendPacket(player, packetPlayOutTitle);
	} 
	
	@Override
	public void sendSubTitlePacket(Player player, String subTitle) {
		V1_8_R3Packet<PacketPlayOutChat> packetPlayOutChat = new V1_8_R3Packet<>(new PacketPlayOutChat(ChatSerializer.a("{\"text\":\"" + subTitle + "\"}"), (byte)2));
		sendPacket(player, packetPlayOutChat);
	}
	
	@Override
	public void sendPacketPlayOutEntityVelocity(Player player, int playerid, double x, double y, double z) {
		V1_8_R3Packet<PacketPlayOutEntityVelocity> packetPlayOutEntityVelocity = new V1_8_R3Packet<>(new PacketPlayOutEntityVelocity(playerid, x, y, z));
		sendPacket(player, packetPlayOutEntityVelocity);
	}
	
	@Override
	public void sendPacketPlayOutPlayerListHeaderFooter(Player player, String header, String footer) throws Exception {
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
	public void sendPacketPlayOutScoreboardObjective(Player player, Objective obj, ObjectiveMode mode) throws Exception{
		ScoreboardObjective sbOjective = (ScoreboardObjective) ReflexionUtils.getMethod(obj, "getHandle").invoke(obj);
		V1_8_R3Packet<PacketPlayOutScoreboardObjective> packetPlayOutScoreboardObjective = new V1_8_R3Packet<>(new PacketPlayOutScoreboardObjective( sbOjective, mode.ordinal()));
		sendPacket(player, packetPlayOutScoreboardObjective);
	}

	@Override
	public void sendPacketPlayOutScoreboardDisplayObjective(Player player, Objective obj) throws Exception{
		ScoreboardObjective sbOjective = (ScoreboardObjective) ReflexionUtils.getMethod(obj, "getHandle").invoke(obj);
		V1_8_R3Packet<PacketPlayOutScoreboardDisplayObjective> packetPlayOutScoreboardDisplayObjective = new V1_8_R3Packet<>(new PacketPlayOutScoreboardDisplayObjective(1, sbOjective));
		sendPacket(player, packetPlayOutScoreboardDisplayObjective);
	}

	@Override
	public void sendPacketPlayOutScoreboardScore(Player player, Scoreboard scoreboard, String name, int score, boolean remove) {
		
	}
	
	@Override
	public ChannelPipeline getNettyPipeline(Player player) {
		return getNettyChannel(player).pipeline();
	}
	
	@Override
	public Channel getNettyChannel(Player player) {
		return getEntityPlayer(player).playerConnection.networkManager.channel;
	}
	

	@Override
	public <T extends Event> T convertPacketInboundToEvent(Object packet, Player p) {
		try {
			if(packet instanceof PacketPlayInTabComplete) {
				PacketPlayInTabComplete packetPlayInTabComplete = (PacketPlayInTabComplete) packet;
				String message = packetPlayInTabComplete.a();
				return (T) new PlayerTabCompleteEvent(p, message);
				//String[] content = (String[]) ReflexionUtils.getField(PacketPlayInTabComplete.class, "a").get(packetPlayInTabComplete);
				/*
				for(String s : content) {
					System.out.println("ContentDebug : " + s);
				}
				return (T) new PlayerTabCompleteEvent(p, content[0]);*/
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public <T extends Event> T convertPacketOutboundToEvent(Object packet, Player p) {
		try {
			/*
			if(packet instanceof PacketPlayOutTabComplete) {
				PacketPlayOutTabComplete packetPlayOutTabComplete = (PacketPlayOutTabComplete) packet;
				String[] content = (String[]) ReflexionUtils.getField(PacketPlayOutTabComplete.class, "a").get(packetPlayOutTabComplete);
				
				for(String s : content) {
					System.out.println("ContentDebug : " + s);
				}
				return (T) new PlayerTabCompleteEvent(p, content[0]);
			}*/
		}catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	public static class V1_8_R3Packet<T extends Packet<?>> extends APacket<T>{

		
		public V1_8_R3Packet(T packet) {
			super(packet);
		}

		@Override
		public void sendPacket(Player player) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(getPacket());
		}
		
	}
	
	public class APacketPlayInTabComplete extends V1_8_R3Packet<PacketPlayInTabComplete>{

		
		public APacketPlayInTabComplete(PacketPlayInTabComplete packet) {
			super(packet);
		}
		
		
	}



	@Override
	public void sendScore(Player player) {
		// TODO Auto-generated method stub
		
	}

}
