package fr.acore.spigot.api.command;

import java.util.List;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

import fr.acore.spigot.api.config.IStringHelper;
import fr.acore.spigot.api.manager.IManager;

public interface ICommandManager extends IManager, TabCompleter, CommandExecutor, IStringHelper{

	public List<ICommand<?>> getCommands();
	public void addCommand(ICommand<?> command);
	public void removeCommand(ICommand<?> command);
	
	public List<ICommand<?>> getForkCommands();
	public void addForkCommand(ICommand<?> command);
	public void removeForkCommand(ICommand<?> command);
	
	public ICommandPreform getArgumentToCommand(ICommand<?> commandRoot, String[] args);
}
