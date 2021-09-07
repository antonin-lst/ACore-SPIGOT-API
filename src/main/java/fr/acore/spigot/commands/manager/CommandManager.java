package fr.acore.spigot.commands.manager;

import java.util.ArrayList;
import java.util.List;

import fr.acore.spigot.commands.listener.CommandListener;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.command.CommandStats;
import fr.acore.spigot.api.command.ICommand;
import fr.acore.spigot.api.command.ICommandManager;
import fr.acore.spigot.api.command.ICommandPreform;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.commands.sender.PlayerAndConsolSender;
import fr.acore.spigot.commands.utils.CommandPreform;
import fr.acore.spigot.config.utils.Conf;

public class CommandManager implements ICommandManager {

	/*
	 * 
	 * Instance du plugin du Manager
	 * 
	 */
	
	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin() { return this.plugin;}
	
	/*
	 * 
	 * Commandes et forkCommandes
	 * 
	 */
	
	private List<ICommand<?>> commands;
	private List<ICommand<?>> forkCommands;
	
	
	public CommandManager(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		commands = new ArrayList<>();
		forkCommands = new ArrayList<>();
		plugin.registerListener(new CommandListener(this));
	}
	
	/*
	 * 
	 * Gestion des ICommand dans commands
	 * 
	 */

	@Override
	public List<ICommand<?>> getCommands() {
		return this.commands;
	}

	@Override
	public void addCommand(ICommand<?> command) {
		this.commands.add(command);
	}

	@Override
	public void removeCommand(ICommand<?> command) {
		this.commands.remove(command);
	}
	
	/*
	 * 
	 * Gestion des ICommand dans commandForks
	 * 
	 */

	@Override
	public List<ICommand<?>> getForkCommands() {
		return this.forkCommands;
	}

	@Override
	public void addForkCommand(ICommand<?> command) {
		this.forkCommands.add(command);
	}

	@Override
	public void removeForkCommand(ICommand<?> command) {
		this.forkCommands.remove(command);
	}

	@Override
	public ICommandPreform getArgumentToCommand(ICommand<?> commandRoot, String[] args) {
		if(args.length == 0) return new CommandPreform(commandRoot, args);
		
		for(ICommand<?> command : commandRoot.getArguments()) {
			if(args[0].equalsIgnoreCase(command.getName()) || command.getAlliases().contains(args[0])) {
				List<String> data = new ArrayList<>();
				if(args.length > 1) {
					for(int i = 1; i < args.length; i++) data.add(args[i]);
				}
				return getArgumentToCommand(command, data.toArray(new String[0]));
			}
		}
		return new CommandPreform(commandRoot, args);
	}
	
	/*
	 * 
	 * Auto Completion des arguments d'une command avec TAB
	 * 
	 */
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 
	 * Check si la commande apartient au ACore si oui execution de la commande
	 * 
	 */
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String commandName = cmd.getName();
		for(ICommand<?> command : commands) {
			if(command.getName().equals(commandName) || command.getAlliases().contains(commandName)) {
				ICommandPreform performedCommand = getArgumentToCommand(command, args);
				CommandStats commandStats = performedCommand.getCommand().prePerformCommand(new PlayerAndConsolSender(sender), performedCommand.getArgs());
				
				switch (commandStats) {
				case PERMITION_DENIED:
					sender.sendMessage(Conf.getPermissionDeniedMessage());
					break;
				case ONLY_PLAYER:
					sender.sendMessage(Conf.getPlayerOnlyMessage());
					break;
				case ONLY_CONSOL:
					sender.sendMessage(Conf.getConsolOnlyMessage());
					break;
				case SYNTAX_ERROR:
					sender.sendMessage(replace(Conf.getSyntaxErrorMessage(), "{SYNTAXE}", (String)performedCommand.getCommand().getSyntax() == null ? "Indisponible" : performedCommand.getCommand().getSyntax()));
					break;
				case TIMER_CANCEL:
					//sender.sendMessage(replace(Conf.getDelayCancelMessage(), "{DELAY}", ((APlayerCommandTimer) performedCommand.getCommand()).getPlayerTimer((Player)sender).getTimeToString()));
					break;
				case SUCCESS:
					break;
				case NOT_FORKED:
					break;
				case OTHER_CANCEL:
					break;
				default:
					break;
				}
				
				log(ChatColor.GOLD + "Command preProcess " + ChatColor.AQUA + commandName + ChatColor.YELLOW +" By " + ChatColor.AQUA + sender.getName() + ChatColor.YELLOW + " Result " + ChatColor.AQUA + commandStats.name());
				return true;
			}
		}
		return false;
	}
	
	/*
	 * 
	 * Gestion des logs
	 * 
	 */
	
	@Override
	public void log(String... args) {
		plugin.log(args);
	}
	
	@Override
	public void log(Object... args) {
		plugin.log(args);
	}

	@Override
	public void logWarn(String... args) {
		plugin.logWarn(args);
	}
	
	@Override
	public void logWarn(Object... args) {
		plugin.logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		plugin.logErr(args);
	}

	@Override
	public void logErr(Object... args) {
		plugin.logErr(args);
	}

}
