package fr.azefgh456.acore.event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;

import fr.azefgh456.acore.hook.hooks.ProtocolLibHook;
import fr.azefgh456.acore.packets.event.PlayerTabCompleteEvent;
import fr.azefgh456.acore.plugin.IPlugin;

public class PLibEventWrapper {

	
	public static void registerProtocolLibEvent(IPlugin plugin, EventWrapper wrapper, ProtocolLibHook hook) {
		registerTabComplete(plugin, wrapper, hook);
		registerServerInfo(plugin, wrapper, hook);
	}
	
	private static void registerTabComplete(IPlugin plugin, EventWrapper wrapper, ProtocolLibHook hook) {
		hook.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL,new PacketType[] { PacketType.Play.Client.TAB_COMPLETE }){
			@EventHandler(priority = EventPriority.HIGHEST)
			public void onPacketReceiving(PacketEvent event) {
				if(event.getPacketType() == PacketType.Play.Client.TAB_COMPLETE) {
					 PacketContainer packet = event.getPacket();
					 String message = ((String)packet.getSpecificModifier(String.class).read(0)).toLowerCase();
					 PlayerTabCompleteEvent playerTabCompletEvent = new PlayerTabCompleteEvent(event.getPlayer(), message);
					 wrapper.call(playerTabCompletEvent);
					 event.setCancelled(playerTabCompletEvent.isCancelled());
				}
			}
		});
	}
	
	private static void registerServerInfo(IPlugin plugin, EventWrapper wrapper, ProtocolLibHook hook) {

		hook.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL,new PacketType[] { PacketType.Status.Server.SERVER_INFO }){
			@EventHandler(priority = EventPriority.HIGHEST)
			public void onPacketSending(PacketEvent event) {
				if(event.getPacketType() == PacketType.Status.Server.SERVER_INFO) {
					WrappedServerPing serverPing = event.getPacket().getServerPings().read(0);
					List<WrappedGameProfile> messages = new ArrayList<>();
					PlayerServerListPingerEvent listPingerEvent = new PlayerServerListPingerEvent(event.getPlayer(), serverPing.getVersionProtocol(), serverPing.getVersionName(), serverPing.getPlayersOnline(), serverPing.getPlayersMaximum(), serverPing.getMotD());
					wrapper.call(listPingerEvent);
					messages.add(new WrappedGameProfile(UUID.randomUUID(), listPingerEvent.getMessage()));
					serverPing.setPlayers(messages);
					serverPing.setVersionProtocol(listPingerEvent.getPingVersion());
					serverPing.setVersionName(listPingerEvent.getPingVersionName());
					serverPing.setMotD(listPingerEvent.getMotd());
					serverPing.setPlayersOnline(listPingerEvent.getOnlinePlayer());
					serverPing.setPlayersMaximum(listPingerEvent.getMaxOnlinePlayer());
				}
			}
		});

	}
	
}
