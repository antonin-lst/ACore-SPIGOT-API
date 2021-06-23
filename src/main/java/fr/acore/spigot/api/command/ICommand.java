package fr.acore.spigot.api.command;

import java.util.List;

import org.bukkit.command.TabCompleter;

import fr.acore.spigot.api.command.sender.ICommandSender;

public interface ICommand<T extends ICommandSender<?>> {
	
	public String getName();
	public List<String> getAlliases();
	public List<ICommand<?>> getArguments();
	public TabCompleter getTabCompleter();
	
	public CommandStats prePerformCommand(T sender, String... args);
	
	public String getPermission();
	public String getSyntax();
	
	
}
