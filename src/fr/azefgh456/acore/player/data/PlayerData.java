package fr.azefgh456.acore.player.data;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.azefgh456.acore.player.manager.PlayerManager;
import fr.azefgh456.acore.player.players.CorePlayer;
import fr.azefgh456.acore.storage.requette.sync.IContainRequette;
import fr.azefgh456.acore.storage.requette.sync.ICountRequette;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint.ConstraintValues;
import fr.azefgh456.acore.storage.utils.AsyncData;
import fr.azefgh456.acore.storage.utils.AsyncData.Action;
import fr.azefgh456.acore.storage.utils.Data;
import fr.azefgh456.acore.storage.utils.DataBuilder;

public class PlayerData extends Data<Player>{

	private PlayerManager playerM;
	
	public PlayerData(PlayerManager playerM) {
		super("playerStorage", "uuid VARCHAR(120), name VARCHAR(80), tuers INT, morts INT, cryptedPassword TEXT, premium TINYINT(1), premiumUuid VARCHAR(120)", new Contraint(ConstraintValues.PRIMARY, "uuid"));
		this.playerM = playerM;
	}

	private void setPlayerRegisteredCount() {
		
		DataBuilder dataBuilder = playerM.executeCustomTypeRequette(new ICountRequette(getStorageName()));
		
		int registeredSize = dataBuilder.getInt(0, "count");
		
		playerM.setRegisteredPlayerCount(registeredSize);
	}

	@Override
	public void load() {
		setPlayerRegisteredCount();
		if(Bukkit.getOnlinePlayers().isEmpty()) return;
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			loadAsyncPlayer(player);
		}
	}

	@Override
	public void save() {
		if(playerM.getCorePlayer().isEmpty()) return;
		
		for(CorePlayer player : playerM.getCorePlayer()) {
			player.save(playerM, this);
		}
	}

	@Override
	public boolean contain(Player val) {
		return playerM.containRequette(new IContainRequette(storageName, Arrays.asList("uuid"), Arrays.asList(val.getUniqueId().toString())));
	}
	
	public void loadAsyncPlayer(Player player) {
		CorePlayer cp = new CorePlayer(player);
		cp.setLoading(true);
		playerM.executeAsyncData(new AsyncData<CorePlayer>(playerM.addCorePlayer(cp), Action.LOAD, playerM, this));
	}
	
	public void saveAsyncPlayer(CorePlayer corePlayer) {
		playerM.executeAsyncData(new AsyncData<CorePlayer>(corePlayer, Action.SAVE, playerM, this));
	}

}
