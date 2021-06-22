package fr.azefgh456.acore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import fr.azefgh456.acore.commands.utils.CommandStats;
import fr.azefgh456.acore.utils.time.PlayerTimerBuilder;

public abstract class APlayerCommandTimer extends APlayerCommand{

	protected Long cmdTimer;
	public Long getCmdTimer() {return this.cmdTimer;}
	
	private List<PlayerTimerBuilder> timer = new ArrayList<>();
	public List<PlayerTimerBuilder> getTimer(){ return timer;}
	public void setTimer(List<PlayerTimerBuilder> timer) { this.timer = timer;}
	
	public void addPlayer(Player player) {
		 if(getPlayerTimer(player) != null) {
			 removePlayer(player);
		 }
		 
		 timer.add(new PlayerTimerBuilder(player.getUniqueId().toString(), System.currentTimeMillis(), cmdTimer));
	 }
	
	public boolean addPlayer(String string, long l) {
		
		if(getPlayerTimer(string) != null) {
			removePlayer(string);
		}
		
		PlayerTimerBuilder timerBuilder = new PlayerTimerBuilder(string, l, cmdTimer);
		
		if(timerBuilder.hisFished()) return false;
		
		timer.add(timerBuilder);
		return true;
	}
	
	public APlayerCommandTimer(Long cmdTimer) {
		this.cmdTimer = cmdTimer;
	}

	 public void removePlayer(Player player) {
		removePlayer(player.getUniqueId().toString());
	 }
	 
	 public void removePlayer(String player) {
			for(int i = 0; i < timer.size(); i++) {
				PlayerTimerBuilder t = timer.get(i);
				if(t.getPlayer().equals(player)) timer.remove(t);
			}
		 }
	 
	 public PlayerTimerBuilder getPlayerTimer(String player) {
		for(PlayerTimerBuilder t : timer) {
			if(t.getPlayer().equals(player)) return t;
		}
		return null;
	 }
	 
	 public PlayerTimerBuilder getPlayerTimer(Player player) {
		return getPlayerTimer(player.getUniqueId().toString());
	 }
	
	@Override
	public CommandStats performACommand(Player player, String... args) {
		if(getPlayerTimer(player) != null && !getPlayerTimer(player).hisFished()) {
			return CommandStats.TIMER_CANCEL;
		}
		
		CommandStats stats = performACommandTimer(player, args);
		if(stats == CommandStats.SUCCESS) addPlayer(player);
		
		return stats;
	}

	
	public abstract CommandStats performACommandTimer(Player player, String... args);
	
}
