package fr.acore.spigot.faction.faction;

import java.util.List;

import org.bukkit.ChatColor;

import com.massivecraft.factions.entity.Faction;

import fr.acore.spigot.api.faction.IFaction;
import fr.acore.spigot.api.faction.IFactionPlayer;

public class MassiveFaction implements IFaction {

	private Faction faction;
	
	public MassiveFaction(Faction faction) {
		this.faction = faction;
	}
	
	@Override
	public String getFactionName() {
		return faction.getName();
	}

	@Override
	public String getFactionId() {
		return faction.getId();
	}

	@Override
	public int getFactionSize() {
		return faction.getMPlayers().size();
	}

	@Override
	public double getFactionPower() {
		return faction.getPower();
	}

	@Override
	public double getFactionMaxPower() {
		return faction.getPowerMax();
	}

	@Override
	public int getFactionLandCount() {
		return faction.getLandCount();
	}
	
	@Override
	public double getFactionBalance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFactionKills() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFactionDeaths() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<IFactionPlayer> getFactionPlayers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendMessage(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public ChatColor getRelationColor(IFaction target) {
		// TODO Auto-generated method stub
		return null;
	}

}
