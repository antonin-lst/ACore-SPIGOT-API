package fr.acore.spigot.packet.converter;

import org.bukkit.event.Event;

import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.packet.PacketType;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.event.manager.EventWrapperManager;
import fr.acore.spigot.nms.INetMinecraftServer;
import fr.acore.spigot.nms.manager.NMSManager;

public class PacketConverter {

	private IPlugin<IManager> plugin;
	private INetMinecraftServer nmsM;
	
	public PacketConverter(IPlugin<IManager> plugin) {
		this.plugin = plugin;
		nmsM = ((NMSManager) plugin.getManager(NMSManager.class)).getNMS();
	}
	
	public <T extends Event> T convertPacketToEvent(PacketType packetType, Object packet, CorePlayer<?> player) {
		T packetToEvent = packetType == PacketType.INBOUND ?  nmsM.convertPacketInboundToEvent(packet, player) : nmsM.convertPacketOutboundToEvent(packet, player);
		if(packetToEvent != null) plugin.getInternalManager(EventWrapperManager.class).call(packetToEvent);
		
		return packetToEvent;
	}
	
}
