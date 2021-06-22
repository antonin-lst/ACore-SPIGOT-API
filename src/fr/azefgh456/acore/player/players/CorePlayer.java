package fr.azefgh456.acore.player.players;

import java.text.DecimalFormat;
import java.util.Arrays;

import org.bukkit.entity.Player;

import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.player.data.PlayerData;
import fr.azefgh456.acore.player.players.utils.ASBoard;
import fr.azefgh456.acore.storage.requette.sync.IInsertRequette;
import fr.azefgh456.acore.storage.requette.sync.ISelectRequette;
import fr.azefgh456.acore.storage.requette.sync.IUpdateRequette;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint.ConstraintValues;
import fr.azefgh456.acore.storage.utils.Data;
import fr.azefgh456.acore.storage.utils.DataBuilder;
import fr.azefgh456.acore.storage.utils.SaveableObject;

public class CorePlayer extends HookedPlayer implements SaveableObject{
	
	private boolean loading;
	public boolean isLoading() { return this.loading;}
	public void setLoading(boolean loading) { this.loading = loading;}
	
	private int kills;
	public int getKills() { return this.kills;}
	private int deaths;
	public int getDeaths() { return this.deaths;}
	
	private String encryptedPassword;
	public String getEncryptedPassword() { return this.encryptedPassword;}
	public void setPassword(String password) { 
		this.encryptedPassword = password;
		plugin.getStorage().executeSimpleRequette(new IUpdateRequette("playerStorage", Arrays.asList("cryptedPassword"), new Contraint(ConstraintValues.WHERE, "uuid = ?"), Arrays.asList(password, player.getUniqueId().toString())));
	}
	
	private boolean isPremium;
	public boolean isPremium() { return this.isPremium;}
	
	private boolean firstJoin;
	public boolean isFirstJoin() { return this.firstJoin;}
	
	private ASBoard scoreboard;
	public ASBoard getScoreboard() { return this.scoreboard;}

	
	public CorePlayer(Player player) {
		this(player, false);
	}
	
	public CorePlayer(Player player, boolean firstJoin) {
		super(player);
		kills = 0;
		deaths = 0;
		encryptedPassword = "";
		this.firstJoin = firstJoin;
	}
	
	public double getRatio() {
		if(deaths == 0.00 || kills == 0.00) {
			return kills;
		}
		return kills/deaths;
	}
	
	public double getFormatedRatio() {
		double ratio = getRatio();
		DecimalFormat f = new DecimalFormat("#.##");
		return Double.parseDouble(f.format(ratio));
	}
	
	public void createBoard(String name, boolean useHealthBar) {
		scoreboard = new ASBoard(plugin, (Player)player, name);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CorePlayer) {
			CorePlayer target = (CorePlayer) obj;
			if(target.getPlayer().equals(getPlayer())) return true;
		}
		return false;
	}
	
	@Override
	public void load(AManager manager, Data<?> dataM) {
		DataBuilder dataBuilder = manager.executeCustomTypeRequette(new ISelectRequette(dataM.getStorageName(), Arrays.asList("tuers", "morts", "cryptedPassword", "premium"), new Contraint(ConstraintValues.WHERE, "uuid = ?"), player.getUniqueId().toString()));
		kills = dataBuilder.getInt(0, "tuers");
		deaths = dataBuilder.getInt(0, "morts");
		encryptedPassword = dataBuilder.getString(0, "cryptedPassword");
		isPremium = dataBuilder.getBoolean(0, "premium");
		loading = false;
	}

	@Override
	public void save(AManager manager, Data<?> dataM) {
		PlayerData pData = (PlayerData)dataM;
		if(pData.contain(getPlayer())) {
			manager.simpleRequette(new IUpdateRequette(dataM.getStorageName(), Arrays.asList("tuers", "morts"), new Contraint(ConstraintValues.WHERE, "uuid = ?"), Arrays.asList(kills, deaths, player.getUniqueId().toString())));
		}else {
			manager.simpleRequette(new IInsertRequette(dataM.getStorageName(), Arrays.asList("uuid", "name", "tuers", "morts", "cryptedPassword"), Arrays.asList(player.getUniqueId().toString(), player.getName(), kills, deaths, encryptedPassword)));
		}
		
	}
	
}
