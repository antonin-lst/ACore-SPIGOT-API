package fr.acore.spigot.packet.manager;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.packet.PacketType;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.packet.converter.PacketConverter;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;

public class PacketsManager implements IManager {

	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin(){ return this.plugin;}
	
	private PacketConverter packetConverter;
	
	public PacketsManager(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		this.packetConverter = new PacketConverter(plugin);
	}
	
	public void injectPlayer(CorePlayer<?> player) {
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
		
		ChannelPipeline pipeline = player.getPipeline();
		pipeline.addBefore("packet_handler", player.getName(), chanelDuplexHandler);
	}
	
	public void removePlayer(CorePlayer<?> player) {
		Channel channel = player.getChannel();
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(player.getName());
			return null;
		});
	}

	/*
	 * 
	 * Gestion des logs
	 * 
	 */
	
	@Override
	public void log(String... args) {
		plugin.log(args);
	}
	
	@Override
	public void log(Object... args) {
		plugin.log(args);
	}

	@Override
	public void logWarn(String... args) {
		plugin.logWarn(args);
	}
	
	@Override
	public void logWarn(Object... args) {
		plugin.logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		plugin.logErr(args);
	}

	@Override
	public void logErr(Object... args) {
		plugin.logErr(args);
	}
}
