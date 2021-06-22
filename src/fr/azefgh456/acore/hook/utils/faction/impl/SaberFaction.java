package fr.azefgh456.acore.hook.utils.faction.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.FactionRenameEvent;

import fr.azefgh456.acore.hook.event.AFactionCreateEvent;
import fr.azefgh456.acore.hook.event.AFactionDisbandEvent;
import fr.azefgh456.acore.hook.event.AFactionNameChangeEvent;
import fr.azefgh456.acore.hook.utils.faction.IFaction;
import fr.azefgh456.acore.hook.utils.faction.IFactions;
import fr.azefgh456.acore.hook.utils.faction.IPlayer;
import fr.azefgh456.acore.hook.utils.faction.impl.faction.SFaction;
import fr.azefgh456.acore.hook.utils.faction.impl.player.SPlayer;
import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.utils.ReflexionUtils;

public class SaberFaction implements IFactions<Faction, FPlayer>{

	private AManager manager;
	
	private Factions factionInstance;
	private FPlayers playerInstance;
	
	public SaberFaction(AManager manager) {
		this.manager = manager;
		factionInstance = Factions.getInstance();
		playerInstance = FPlayers.getInstance();
		registerFactionListener();
	}
	
	public static void setChatHandled(boolean handled) {
		try {
			Field chatTagHandled = ReflexionUtils.getField(Class.forName("com.massivecraft.factions.Conf"), "chatTagHandledByAnotherPlugin");
			chatTagHandled.setAccessible(true);
			chatTagHandled.setBoolean(null, handled);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<IFaction<?, ?>> getFactions() {
		List<IFaction<?, ?>> factions = new ArrayList<>();
		factionInstance.getAllFactions().forEach(faction -> {
			factions.add(new SFaction(manager, faction));
		});
		return factions;
	}
	
	@Override
	public IFaction<Faction, FPlayer> getFactionByName(String name) {
		return new SFaction(manager, factionInstance.getByTag(name));
	}

	@Override
	public IFaction<Faction, FPlayer> getFactionById(String id) {
		return new SFaction(manager, factionInstance.getFactionById(id));
	}

	@Override
	public IPlayer<Faction, FPlayer> getFactionPlayer(String name) {
		return new SPlayer(manager, playerInstance.getByPlayer(Bukkit.getPlayer(name)));
	}

	@Override
	public IPlayer<Faction, FPlayer> getFactionPlayer(UUID uuid) {
		return new SPlayer(manager, playerInstance.getById(uuid.toString()));
	}

	@Override
	public IPlayer<Faction, FPlayer> getFactionPlayer(Player player) {
		return new SPlayer(manager, playerInstance.getByPlayer(player));
	}
	
	public FPlayer getFactionPlayer(OfflinePlayer offPlayer) {
		return playerInstance.getByOfflinePlayer(offPlayer);
	}

	@Override
	public boolean hasFaction(Player player) {
		return hisFaction(getFactionPlayer(player).getIFaction());
	}

	@Override
	public boolean hisFaction(IFaction<?,?> faction) {
		if(faction == null || 
				Factions.getInstance().getWarZone().equals((Faction)faction.getFaction()) || 
				Factions.getInstance().getSafeZone().equals((Faction)faction.getFaction()) || 
				Factions.getInstance().getWilderness().equals((Faction)faction.getFaction())) return false;
				
				return true;
	}
	
	@Override
	public ChatColor getRelationColor(Player player, Player target) {
		return getFactionPlayer(player).getIFaction().getRelationColor(getFactionPlayer(target).getIFaction());
	}
	
	@Override
	public String getColoredFactionName(Player player, Player target) {
		return getRelationColor(player, target) + getFactionPlayer(player).getIFaction().getFactionName();
	}

	@Override
	public void registerFactionListener() {
		manager.registerListener(new Listener() {
			
			@EventHandler
			public void factionCreateEvent(FactionCreateEvent event) {
				manager.callEvent(new AFactionCreateEvent(event.getFactionTag(), event.getFPlayer().getFaction().getId(), new SPlayer(manager, event.getFPlayer()), null));
			}
			
			@EventHandler
			public void factionDisbandEvent(FactionDisbandEvent event) {
				manager.callEvent(new AFactionDisbandEvent(new SFaction(manager, event.getFaction())));
			}
			
			@EventHandler
			public void factionNameChangeEvent(FactionRenameEvent event) {
				manager.callEvent(new AFactionNameChangeEvent(new SFaction(manager, event.getFaction()), event.getFactionTag()));
			}
			
		});
	}	

}
