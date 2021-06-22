package fr.azefgh456.acore.hook.utils.faction.impl.player;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;

import fr.azefgh456.acore.hook.exception.UnsuportedVersionException;
import fr.azefgh456.acore.hook.utils.faction.IFaction;
import fr.azefgh456.acore.hook.utils.faction.IPlayer;
import fr.azefgh456.acore.hook.utils.faction.impl.MassiveFaction;
import fr.azefgh456.acore.hook.utils.faction.impl.faction.MFaction;

public class MassivePlayer implements IPlayer<Faction, MPlayer>{

	private MPlayer mPlayer;
	private MassiveFaction factionM;
	
	public MassivePlayer(MassiveFaction factionM, MPlayer mPlayer) {
		this.factionM = factionM;
		this.mPlayer = mPlayer;
	}
	
	@Override
	public IFaction<Faction, MPlayer> getIFaction() {
		return new MFaction(factionM, mPlayer.getFaction());
	}

	@Override
	public double getPower() {
		return mPlayer.getPower();
	}

	@Override
	public double getBalance() {
		return factionM.getOfflinePlayer((OfflinePlayer)mPlayer.getPlayer()).getBalance();
	}

	@Override
	public int getKills() throws UnsuportedVersionException {
		throw new UnsuportedVersionException("Massive Not Support player kills");
	}

	@Override
	public int getDeaths() throws UnsuportedVersionException{
		throw new UnsuportedVersionException("Massive Not Support player deaths");
	}
	
	@Override
	public String getChatTag(IPlayer<Faction, MPlayer> player) throws UnsuportedVersionException {
		throw new UnsuportedVersionException("Massive Not Support chatTag");
	}

	@Override
	public MPlayer getFPlayer() {
		return mPlayer;
	}
	
	@Override
	public Player getPlayer() {
		return mPlayer.getPlayer();
	}

	@Override
	public String getFactionGrade() {
		return mPlayer.getRole().getPrefix();
	}
}
