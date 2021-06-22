package fr.azefgh456.acore.hook.utils.faction.impl.faction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;

import fr.azefgh456.acore.hook.utils.faction.IFaction;
import fr.azefgh456.acore.hook.utils.faction.IPlayer;
import fr.azefgh456.acore.hook.utils.faction.impl.player.SPlayer;
import fr.azefgh456.acore.manager.AManager;

public class SFaction implements IFaction<Faction, FPlayer>{

	private AManager manager;
	
	private Faction faction;
	
	public SFaction(AManager manager, Faction faction) {
		this.manager = manager;
		this.faction = faction;
	}
	
	@Override
	public Faction getFaction() {
		return this.faction;
	}
	
	@Override
	public String getFactionName() {
		return faction.getTag();
	}
	
	@Override
	public String getFactionId() {
		return faction.getId();
	}
	
	@Override
	public int getFactionSize() {
		return faction.getFPlayers().size();
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
		return faction.getLandRounded();
	}

	@Override
	public double getFactionBalance() {
		return 0.0;
	}

	@Override
	public int getFactionKills() {
		return faction.getKills();
	}

	@Override
	public int getFactionDeaths() {
		return faction.getDeaths();
	}

	@Override
	public List<IPlayer<Faction, FPlayer>> getFactionPlayers() {
		List<IPlayer<Faction, FPlayer>> factionPlayers = new ArrayList<>();
		faction.getFPlayers().forEach(mPlayer -> {
			factionPlayers.add(new  SPlayer(manager, mPlayer));
		});
		return factionPlayers;
	}

	@Override
	public void sendMessage(String message) {
		faction.sendMessage(message);
	}
	
	@Override
	public ChatColor getRelationColor(IFaction<?, ?> target) {
		SFaction ft = (SFaction) target;
		return faction.getColorTo(ft.getFaction());
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof SFaction) {
			SFaction target = (SFaction)obj;
			if(target.getFactionId().equals(getFactionId())) return true;
		}
		return false;
	}

	@Override
	public int getKothWin() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotemWin() {
		// TODO Auto-generated method stub
		return 0;
	}

}
