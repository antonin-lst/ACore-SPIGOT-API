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

    //gestion du beffering de la SIDEBAR
    private Objective mainBoard;
    private Objective bufferedMainBoard;

    private String currentMainBoardName;
    private Map<Integer, String> mainBoardLines;

    public ABoard(Player player, String currentBoardName) {
        this.player = player;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.mainBoard = scoreboard.registerNewObjective("main-board", "dummy");
        this.mainBoard = scoreboard.registerNewObjective("main-board-bf", "dummy");
        this.currentMainBoardName = currentBoardName;
        this.mainBoardLines = new HashMap<>();
    }

    /*

    Gestion du ScoreBoard SIDEBAR

     */

    //set des lignes
    public void setLines(Map<Integer, String> lines){ this.mainBoardLines = lines;}

    //ajout d'une ligne
    public void addLine(int index, String line){
        this.mainBoardLines.put(index, line);
    }

    //supprétion d'une ligne
    public void removeLine(int index){
        this.mainBoardLines.remove(index);
    }

    //suppretion de toutes les lignes
    public void clearLines(){
        this.mainBoardLines.clear();
    }

    /*

    Gestion du nametag

     */

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

    /*

    Scoreboard gestion

     */


    //actualisation du scoreboard
    public void refreshBoard(){
        switchBuffer();
        this.mainBoard.setDisplayName(currentMainBoardName);
        for(Map.Entry<Integer, String> line : mainBoardLines.entrySet()){
            Score score = mainBoard.getScore(line.getValue());
            score.setScore(line.getKey());
        }
        this.mainBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.bufferedMainBoard.unregister();
        this.bufferedMainBoard = scoreboard.registerNewObjective("main-board-bf", "dummy");

        if(player.getScoreboard() != scoreboard) setBoardToPlayer();
    }

    //echange entre main-board et buffered-board
    public void switchBuffer(){
        this.bufferedMainBoard.setDisplayName(mainBoard.getDisplayName());
        for(Map.Entry<Integer, String> line : mainBoardLines.entrySet()){
            Score score = mainBoard.getScore(line.getValue());
            score.setScore(line.getKey());
        }
        this.bufferedMainBoard.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.mainBoard.unregister();
        this.mainBoard = scoreboard.registerNewObjective("main-board", "dummy");
    }

    /*

    Gestion du joueur

     */

    public void setBoardToPlayer(){
        player.setScoreboard(scoreboard);
    }

    public Player getPlayer(){ return this.player;}


}
