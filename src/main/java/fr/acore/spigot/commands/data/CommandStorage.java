package fr.acore.spigot.commands.data;

import fr.acore.spigot.api.command.ICommand;
import fr.acore.spigot.api.command.ICommandCooldown;
import fr.acore.spigot.api.storage.column.Column;
import fr.acore.spigot.api.storage.column.foreign.ManyToOne;
import fr.acore.spigot.api.storage.column.type.ColumnType;
import fr.acore.spigot.api.storage.table.Table;
import fr.acore.spigot.player.online.OnlineCorePlayerStorage;

@Table(name = "commandCooldownStorage")
public class CommandStorage implements ICommandCooldown<OnlineCorePlayerStorage>{

	@ManyToOne(columnName = "playerId", primary = true, foreign = "playerStorage.uuid", type = ColumnType.STRING)
	private OnlineCorePlayerStorage corePlayer;
	
	@Column(primary = true)
	private String serverName;
	
	@Column(primary = true)
	private String commandName;
	
	private ICommand<?> command;
	
	@Column
	private long current;
	
	@Column
	private long delay;
	
	public CommandStorage() {
	}
	
	@Override
	public ICommand<?> getCommand() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long getCurrent() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public long getDelay() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String getFromatedRemainingTime() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public OnlineCorePlayerStorage getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public long getRemainingTime() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isFinish() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void setCurrent(long current) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setDelay(long delay) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
