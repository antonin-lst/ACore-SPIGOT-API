package fr.acore.spigot.faction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;

import com.massivecraft.factions.entity.FactionColl;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.faction.FactionType;
import fr.acore.spigot.api.faction.IFaction;
import fr.acore.spigot.api.faction.IFactionManager;
import fr.acore.spigot.api.faction.IFactionPlayer;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.faction.faction.MassiveFaction;

public class MassiveFactionManager implements IFactionManager<MassiveFaction> {

	private FactionColl factionInstance;
	
	private List<MassiveFaction> factions;
	
	public MassiveFactionManager() {
		factionInstance = FactionColl.get();
		factions = new ArrayList<>(); 
		factionInstance.getAll().forEach(faction -> { factions.add(new MassiveFaction(faction));});
	}
	
	@Override
	public List<MassiveFaction> getFactions() {
		return this.factions;
	}

	@Override
	public MassiveFaction getFactionByName(String name) {
		for(MassiveFaction faction : factions) {
			if(faction.getFactionName().equals(name)) return faction;
		}
		return null;
	}

	@Override
	public MassiveFaction getFactionById(String id) {
		for(MassiveFaction faction : factions) {
			if(faction.getFactionId().equals(id)) return faction;
		}
		return null;
	}

	@Override
	public IFactionPlayer getFactionPlayer(String name) {
		for(CorePlayer<?> player : ACoreSpigotAPI.getInstance().getCorePlayer()) {
			if(player.getName().equals(name)) return player;
		}
		return null;
	}

	@Override
	public IFactionPlayer getFactionPlayer(UUID uuid) {
		for(CorePlayer<?> player : ACoreSpigotAPI.getInstance().getCorePlayer()) {
			if(player.getUuid().equals(uuid)) return player;
		}
		return null;
	}

	@Override
	public IFactionPlayer getFactionPlayer(CorePlayer<?> player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasFaction(CorePlayer<?> player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hisFaction(IFaction faction) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ChatColor getRelationColor(CorePlayer<?> player, CorePlayer<?> target) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getColoredFactionName(CorePlayer<?> player, CorePlayer<?> target) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public FactionType getFactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerFactionListener() {
		// TODO Auto-generated method stub

	}

}
