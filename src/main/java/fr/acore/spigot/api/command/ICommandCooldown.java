package fr.acore.spigot.api.command;

import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.timer.ITimer;

public interface ICommandCooldown<T extends CorePlayer<?>> extends ITimer{
	
	public ICommand<?> getCommand();
	public String getCommandName();
	public T getPlayer();

}
