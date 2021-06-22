package fr.azefgh456.acore.hook.utils.faction.impl.faction;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;

import fr.azefgh456.acore.hook.utils.faction.IFaction;
import fr.azefgh456.acore.hook.utils.faction.IPlayer;
import fr.azefgh456.acore.hook.utils.faction.impl.MassiveFaction;
import fr.azefgh456.acore.hook.utils.faction.impl.faction.stats.MassiveFactionStats;
import fr.azefgh456.acore.hook.utils.faction.impl.player.MassivePlayer;

public class MFaction implements IFaction<Faction, MPlayer>{

	private MassiveFaction factionM;
	private Faction faction;

	public MFaction(MassiveFaction factionM, Faction faction) {
		this.factionM = factionM;
		this.faction = faction;
	}

	@Override
	public Faction getFaction() {
		return this.faction;
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
	public ChatColor getRelationColor(IFaction<?, ?> target) {
		MFaction mf = (MFaction) target;
		return faction.getRelationWish(mf.getFaction()).getColor();
	}

	@Override
	public double getFactionBalance() {
		double factionBalance = 0.0;
		for(IPlayer<Faction, MPlayer> player : getFactionPlayers()) {
			factionBalance += player.getBalance();
		}
		
		return factionBalance;
	}
	
	public MassiveFactionStats getMassiveStats() {
		return factionM.getMassiveStats(getFactionId());
	}

	@Override
	public int getFactionKills(){
		return getMassiveStats().getTuers();
	}
	
	public void addKills() {
		getMassiveStats().addKills();
	}

	@Override
	public int getFactionDeaths(){
		return getMassiveStats().getMorts();
	}
	
	public void addDeaths() {
		getMassiveStats().addDeaths();
	}

	@Override
	public List<IPlayer<Faction, MPlayer>> getFactionPlayers() {
		List<IPlayer<Faction, MPlayer>> factionPlayers = new ArrayList<>();
		faction.getMPlayers().forEach(mPlayer -> {
			factionPlayers.add(new MassivePlayer(factionM, mPlayer));
		});
		return factionPlayers;
	}

	@Override
	public void sendMessage(String message) {
		faction.sendMessage(message);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof MFaction) {
			MFaction target = (MFaction)obj;
			if(target.getFactionId().equals(getFactionId())) return true;
		}
		return false;
	}

	@Override
	public int getKothWin() {
		return 0;
	}

	@Override
	public int getTotemWin() {
		return 0;
	}
	
}
