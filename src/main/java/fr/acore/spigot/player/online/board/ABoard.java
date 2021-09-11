package fr.acore.spigot.player.online.board;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ABoard {

    //instance du joueur
    private Player player;

    //instance du scoreboard
    private Scoreboard scoreboard;

    private Objective mainBoard;
    private Objective bufferedMainBoard;
    private String currentMainBoardName;
    private Map<Integer, String> mainBoardLines;

    public ABoard(Player player, String currentBoardName) {
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.mainBoard = scoreboard.registerNewObjective("main-board", "dummy");
        this.currentMainBoardName = currentBoardName;
        this.mainBoardLines = new HashMap<>();
    }

    public void addLine(int index, String line){
        this.mainBoardLines.put(index, line);
    }

    public void clearLines(){
        this.mainBoardLines.clear();
    }

    public void refreshBoard(){
        this.mainBoard.setDisplayName(currentMainBoardName);
        this.mainBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
        for(Map.Entry<Integer, String> line : mainBoardLines.entrySet()){
            Score score = mainBoard.getScore(line.getValue());
            score.setScore(line.getKey());
        }

        if(player.getScoreboard() != scoreboard) setBoardToPlayer();
    }

    public void setBoardToPlayer(){
        player.setScoreboard(scoreboard);
    }

    public void addHealthBare() {
        Objective o = this.scoreboard.registerNewObjective("health", "health");
        o.setDisplayName(ChatColor.RED + "❤");
        o.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }

    public void setPlayerPrefixAndSuffix(String playerPrefix, String playerSuffix, Player player, String teamName) {
        if(scoreboard.getTeam(teamName) == null)
            scoreboard.registerNewTeam(teamName);
        Team t = scoreboard.getTeam(teamName);
        t.setPrefix(playerPrefix);
        t.setSuffix(playerSuffix);
        t.addPlayer(player);
    }

}
