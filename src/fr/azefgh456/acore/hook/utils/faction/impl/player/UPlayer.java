package fr.azefgh456.acore.hook.utils.faction.impl.player;

import org.bukkit.entity.Player;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;

import fr.azefgh456.acore.hook.exception.UnsuportedVersionException;
import fr.azefgh456.acore.hook.utils.faction.IFaction;
import fr.azefgh456.acore.hook.utils.faction.IPlayer;
import fr.azefgh456.acore.hook.utils.faction.impl.faction.UFaction;
import fr.azefgh456.acore.manager.AManager;

public class UPlayer implements IPlayer<Faction, FPlayer>{

	private AManager manager;
	private FPlayer player;
	
	public UPlayer(AManager manager, FPlayer player) {
		this.manager = manager;
		this.player = player;
	}

	@Override
	public IFaction<Faction, FPlayer> getIFaction() {
		return new UFaction(manager, player.getFaction());
	}

	@Override
	public double getPower() {
		return player.getPower();
	}

	@Override
	public double getBalance() {
		return manager.getOfflinePlayer(player.getPlayer()).getBalance();
	}

	@Override
	public int getKills() throws UnsuportedVersionException {
		return player.getKills();
	}

	@Override
	public int getDeaths() throws UnsuportedVersionException {
		return player.getDeaths();
	}
	
	@Override
	public String getChatTag(IPlayer<Faction, FPlayer> target) {
		return player.getChatTag(target.getFPlayer());
	}
	
	@Override
	public FPlayer getFPlayer() {
		return player;
	}

	@Override
	public Player getPlayer() {
		return player.getPlayer();
	}

	@Override
	public String getFactionGrade() {
		return player.getRole().getPrefix();
	}

}
