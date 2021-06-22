package fr.azefgh456.acore.packets.converter;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import fr.azefgh456.acore.event.EventWrapper;
import fr.azefgh456.acore.nms.INetMinecraftServer;
import fr.azefgh456.acore.nms.manager.NMSManager;
import fr.azefgh456.acore.packets.utils.PacketType;
import fr.azefgh456.acore.plugin.IPlugin;

public class PacketConverter {

	private IPlugin plugin;
	private INetMinecraftServer nmsM;
	
	public PacketConverter(IPlugin plugin) {
		this.plugin = plugin;
		nmsM = ((NMSManager) plugin.getManager(NMSManager.class)).getNMS();
	}
	
	public <T extends Event> T convertPacketToEvent(PacketType packetType, Object packet, Player player) {
		T packetToEvent = packetType == PacketType.INBOUND ? nmsM.convertPacketInboundToEvent(packet, player) : nmsM.convertPacketOutboundToEvent(packet, player);
		if(packetToEvent != null) EventWrapper.callEvent(plugin, packetToEvent);
		
		return packetToEvent;
	}
	
}
