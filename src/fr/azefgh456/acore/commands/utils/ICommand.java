package fr.azefgh456.acore.commands.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;

import fr.azefgh456.acore.ACore;

public abstract class ICommand {

	protected ACore plugin = ACore.plugin;
	
	private String name;
	private List<String> alliases;
	private List<ICommand> arguments;
	
	public ICommand() {
		name = null;
		alliases = new ArrayList<>();
		arguments = new ArrayList<>();
	}
	
	public ICommand setName(String name) {
		this.name = name;
		return this;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addAlliases(String alliase) {
		this.alliases.add(alliase);
	}
	
	public List<String> getAlliases(){
		return this.alliases;
	}
	
	public ICommand setAlliases(List<String> alliases) {
		this.alliases = alliases;
		return this;
	}
	
	public void addArguments(ICommand arg) {
		arguments.add(arg);
	}
	
	public List<ICommand> getArguments() {
		return this.arguments;
	}	
	
	public CommandStats prePerformCommand(CommandSender sender, String... args) {
		if(getPermission() == null || sender.hasPermission(getPermission())) return performCommand(sender, args);
		
		return CommandStats.PERMITION_DENIED;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ICommand) {
			ICommand target = (ICommand)obj;
			if(target.getName().equals(name)) return true;
		}
		return false;
	}
	
	public abstract CommandStats performCommand(CommandSender sender, String... args);	
	
	public abstract String getPermission();
	public abstract String getSyntax();
}
