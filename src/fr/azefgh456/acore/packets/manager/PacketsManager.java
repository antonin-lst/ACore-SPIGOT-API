package fr.azefgh456.acore.packets.manager;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import fr.azefgh456.acore.manager.IManager;
import fr.azefgh456.acore.packets.converter.PacketConverter;
import fr.azefgh456.acore.packets.utils.PacketType;
import fr.azefgh456.acore.plugin.IPlugin;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;

public class PacketsManager implements IManager {

	private IPlugin plugin;
	
	private PacketConverter packetConverter;
	
	public PacketsManager(IPlugin plugin) {
		this.plugin = plugin;
		this.packetConverter = new PacketConverter(plugin);
	}
	
	public void injectPlayer(Player player) {
		ChannelDuplexHandler chanelDuplexHandler = new ChannelDuplexHandler() {
			
			//inbound
			@Override
			public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
				Event packetToEvent = packetConverter.convertPacketToEvent(PacketType.INBOUND, packet, player);
				if(packetToEvent != null && packetToEvent instanceof Cancellable && ((Cancellable) packetToEvent).isCancelled()) {
					System.out.println("PacketManager : canceledPacketInBound");
					return;
				}
				
				/*
				if(packet instanceof PacketPlayInTabComplete) {
					System.out.println("test");
				}*/
				super.channelRead(context, packet);
			}
			
			
			//outbound
			@Override
			public void write(ChannelHandlerContext context, Object packet, ChannelPromise channel) throws Exception {
				Event packetToEvent = packetConverter.convertPacketToEvent(PacketType.OUTBOUND, packet, player);
				if(packetToEvent != null && packetToEvent instanceof Cancellable && ((Cancellable) packetToEvent).isCancelled()) return;
				
				super.write(context, packet, channel);
			}
			
		};
		
		ChannelPipeline pipeline = plugin.getCorePlayer(player).getPipeline();
		pipeline.addBefore("packet_handler", player.getName(), chanelDuplexHandler);
	}
	
	public void removePlayer(Player player) {
		Channel channel = plugin.getCorePlayer(player).getChannel();
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(player.getName());
			return null;
		});
	}
	
}
