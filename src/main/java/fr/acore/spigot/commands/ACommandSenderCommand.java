package fr.acore.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.command.CommandStats;
import fr.acore.spigot.api.command.ICommand;
import fr.acore.spigot.api.command.sender.ICommandSender;
import fr.acore.spigot.api.plugin.IPlugin;

public abstract class ACommandSenderCommand implements ICommand<ICommandSender<CommandSender>> {

	protected final IPlugin<?> instance;
	
	private String name;
	private List<String> alliases;
	
	private List<ICommand<?>> arguments;
	
	public ACommandSenderCommand(String name) {
		this.instance = ACoreSpigotAPI.getInstance();
		this.name = name;
		this.alliases = new ArrayList<>();
		this.arguments = new ArrayList<>();
	}
	
	/*
	 * 
	 * Nom de la commande
	 * 
	 */
	
	@Override
	public String getName() {
		return this.name;
	}

	/*
	 * 
	 * Liste d'alliases pour la commande
	 * 
	 */
	
	@Override
	public List<String> getAlliases() {
		return this.alliases;
	}

	/*
	 * 
	 * Liste des commandes en parametres
	 * 
	 */
	
	@Override
	public List<ICommand<?>> getArguments() {
		return this.arguments;
	}
	
	/*
	 * 
	 * Auto completer des parametres en commandes grace aux arguments et a une fonction de filtre sur une list
	 * 
	 */

	@Override
	public TabCompleter getTabCompleter() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 * 
	 * Verification des permittions du sender
	 * 
	 */

	@Override
	public CommandStats prePerformCommand(ICommandSender<CommandSender> sender, String... args) {
		if(getPermission() == null || getPermission().isEmpty() || sender.hasPermission(getPermission())) {
			return performCommand(sender, args);
		}
		return CommandStats.PERMITION_DENIED;
	}
	
	public abstract CommandStats performCommand(ICommandSender<CommandSender> sender, String... args);
	
}
