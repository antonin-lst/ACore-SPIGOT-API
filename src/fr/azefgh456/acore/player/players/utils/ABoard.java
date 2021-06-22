package fr.azefgh456.acore.player.players.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.azefgh456.acore.nms.utils.ObjectiveMode;
import fr.azefgh456.acore.player.players.CorePlayer;

public class ABoard {

	private CorePlayer player;
	private Scoreboard scoreboard;
	
	private boolean useHealthBar;
	private boolean isUsed;
	
	
	private String boardName;
	
	public ABoard(CorePlayer player) {
		this(player, false);
	}
	
	public ABoard(CorePlayer player, boolean useHealthBar) {
		this.player = player;
		this.useHealthBar = useHealthBar;
		this.isUsed = false;
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	}
	
	
	public ABoard initScoreboard() {
		this.isUsed = true;
		if(useHealthBar) setupHealtBar();
		
		
		updateScoreboard();
		return this;
	}
	
	
	public ABoard setBoardName(String name) {
		this.boardName = name;
		String subName = player.getPlayer().getName().length() <= 14
                ? player.getPlayer().getName()
                : player.getPlayer().getName().substring(0, 14);
		if(scoreboard.getObjective("sb") != null) scoreboard.getObjective("sb"+subName).unregister();
			
		Objective o = scoreboard.registerNewObjective("sb"+subName, "dummy");
		System.out.println(boardName);
		o.setDisplayName(boardName);
		player.sendPacketPlayOutScoreboardObjective(o, ObjectiveMode.CREATE);
        player.sendPacketPlayOutScoreboardDisplayObjective(o);
		updateScoreboard();
		return this;
	}
	
	
	public ABoard setPlayerPrefixAndSuffix(String playerPrefix, String playerSuffix, Player player) {
		if(scoreboard.getTeam(player.getName()) == null)
			scoreboard.registerNewTeam(player.getName());
		Team t = scoreboard.getTeam(player.getName());
		t.setPrefix(playerPrefix);
		t.setSuffix(playerSuffix);
		t.addPlayer(player);
		updateScoreboard();
		return this;
	}
	
	public ABoard setUseHealthBar(boolean useHealthBar) {
		
		if(isUsed && useHealthBar) setupHealtBar();
		
		if(isUsed && this.useHealthBar && !useHealthBar) scoreboard.getObjective("health").unregister();
		
		this.useHealthBar = useHealthBar;
		return this;
	}
	
	
	
	
	
	private ABoard updateScoreboard() {
		player.getPlayer().setScoreboard(scoreboard);
		return this;
	}
	
	private void setupHealtBar() {
		if(scoreboard.getObjective("health") != null) {
			scoreboard.getObjective("health").unregister();
		}
		Objective o = scoreboard.registerNewObjective("health", "health");
		o.setDisplayName(ChatColor.RED + "❤");
		o.setDisplaySlot(DisplaySlot.BELOW_NAME);
	}
	
}
