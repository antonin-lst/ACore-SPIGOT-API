package fr.azefgh456.acore.hook.utils.worldedit.capping;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.azefgh456.acore.event.cap.CapableZoneWinEvent;
import fr.azefgh456.acore.hook.utils.worldedit.WSelectionHook;
import fr.azefgh456.acore.hook.utils.worldedit.capping.Capper.PlayerCapper;
import fr.azefgh456.acore.manager.AManager;

public abstract class PlayerCapableZone extends CapableZone<Player> {

	public PlayerCapableZone(AManager manager, WSelectionHook selection, long capDelay) {
		super(manager, selection, capDelay);
	}

	@Override
	public List<Capper<Player>> getCapperInZone() {
		List<Capper<Player>> playerCapables = new ArrayList<>();
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(capZone.containPos(player.getLocation())) playerCapables.add(new PlayerCapper(player));
		}
		return playerCapables;
	}
	
	@Override
	public void win(Capper<Player> winner) {
		manager.callEvent(new CapableZoneWinEvent<Player, PlayerCapableZone>(winner, this));
	}
}
