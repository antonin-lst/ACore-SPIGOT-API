package fr.azefgh456.acore.hook.utils.faction.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.FactionColl;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.event.EventFactionsCreate;
import com.massivecraft.factions.event.EventFactionsDisband;
import com.massivecraft.factions.event.EventFactionsNameChange;

import fr.azefgh456.acore.hook.event.AFactionCreateEvent;
import fr.azefgh456.acore.hook.event.AFactionDisbandEvent;
import fr.azefgh456.acore.hook.event.AFactionNameChangeEvent;
import fr.azefgh456.acore.hook.utils.faction.IFaction;
import fr.azefgh456.acore.hook.utils.faction.IFactions;
import fr.azefgh456.acore.hook.utils.faction.IPlayer;
import fr.azefgh456.acore.hook.utils.faction.data.MassiveFactionData;
import fr.azefgh456.acore.hook.utils.faction.impl.faction.MFaction;
import fr.azefgh456.acore.hook.utils.faction.impl.faction.stats.MassiveFactionStats;
import fr.azefgh456.acore.hook.utils.faction.impl.player.MassivePlayer;
import fr.azefgh456.acore.manager.AManager;

public class MassiveFaction extends AManager implements IFactions<Faction, MPlayer>{

	private static MassiveFaction instance;
	private FactionColl factionInstance;
	
	private Map<String, MassiveFactionStats> factionsStats;
	
	public void loadNewFaction(String uuid) { factionsStats.put(uuid, new MassiveFactionStats(0,0));}
	public MassiveFactionStats getMassiveStats(String id) {
		MassiveFactionStats factionStats = factionsStats.get(id);
		if(factionStats != null) return factionStats;
		
		loadNewFaction(id);
		return factionsStats.get(id);
		
	}
	public Map<String, MassiveFactionStats> getFactionsStats(){ return this.factionsStats;}
	public void setFactionsStats(Map<String, MassiveFactionStats> factionsStats) { this.factionsStats = factionsStats;}
	
	public MassiveFaction(AManager manager) {
		super(manager.getPlugin(), false);
		factionInstance = FactionColl.get();
		instance = this;
		factionsStats = new HashMap<>();
		manager.registerData(new MassiveFactionData(this));
		registerFactionListener();
	}
	
	@Override
	public List<IFaction<?, ?>> getFactions() {
		List<IFaction<?, ?>> factions = new ArrayList<>();
		factionInstance.getAll().forEach(faction -> {
			factions.add(new MFaction(this, faction));
		});
		return factions;
	}
	
	@Override
	public IFaction<Faction, MPlayer> getFactionByName(String name) {
		Faction f = factionInstance.getByName(name);
		return new MFaction(this, f);
	}

	@Override
	public IFaction<Faction, MPlayer> getFactionById(String id) {
		return new MFaction(this, factionInstance.get(id));
	}

	@Override
	public IPlayer<Faction, MPlayer> getFactionPlayer(String name) {
		return new MassivePlayer(this, MPlayer.get(name));
	}

	@Override
	public IPlayer<Faction, MPlayer> getFactionPlayer(UUID uuid) {
		return new MassivePlayer(this, MPlayer.get(uuid));
	}
	
	@Override
	public IPlayer<Faction, MPlayer> getFactionPlayer(Player player) {
		return new MassivePlayer(this, MPlayer.get(player));
	}
	
	@Override
	public boolean hasFaction(Player player) {
		return hisFaction(getFactionPlayer(player).getIFaction());
	}
	
	@Override
	public boolean hisFaction(IFaction<?,?> faction) {
		if(faction == null || factionInstance.getWarzone().equals((Faction)faction.getFaction()) || factionInstance.getSafezone().equals((Faction)faction.getFaction()) || factionInstance.getNone().equals((Faction)faction.getFaction())) return false;	
			
		return true;
	}
	
	@Override
	public ChatColor getRelationColor(Player player, Player target) {
		MFaction faction = (MFaction) getFactionPlayer(player).getIFaction();
		return faction.getRelationColor(getFactionPlayer(target).getIFaction());
	}
	
	@Override
	public String getColoredFactionName(Player player, Player target) {
		return getRelationColor(player, target) + getFactionPlayer(player).getIFaction().getFactionName();
	}
	
	@Override
	public void registerFactionListener() {
		registerListener(new Listener() {
			
			@EventHandler
			public void factionCreateEvent(EventFactionsCreate event) {
				loadNewFaction(event.getFactionId());
				callEvent(new AFactionCreateEvent(event.getFactionName(), event.getFactionId(), new MassivePlayer(instance, event.getMPlayer()), event.getSender()));
			}
			
			@EventHandler
			public void factionDisbandEvent(EventFactionsDisband event) {
				callEvent(new AFactionDisbandEvent(new MFaction(instance, event.getFaction())));
			}
			
			@EventHandler
			public void factionNameChangeEvent(EventFactionsNameChange event) {
				callEvent(new AFactionNameChangeEvent(new MFaction(instance, event.getFaction()), event.getNewName()));
			}
		});
	}
}
