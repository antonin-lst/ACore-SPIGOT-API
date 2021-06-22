package fr.azefgh456.acore.commands.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.commands.APlayerCommandTimer;
import fr.azefgh456.acore.commands.data.CommandData;
import fr.azefgh456.acore.commands.listener.CommandListener;
import fr.azefgh456.acore.commands.utils.CommandStats;
import fr.azefgh456.acore.commands.utils.ICommand;
import fr.azefgh456.acore.commands.utils.ICommandPreform;
import fr.azefgh456.acore.config.Setupable;
import fr.azefgh456.acore.config.utils.Conf;
import fr.azefgh456.acore.manager.AManager;

public class CommandManager extends AManager implements CommandExecutor{

	private List<ICommand> commands;
	public List<ICommand> getCommands(){ return this.commands;}
	
	private List<ICommand> forkCommands;
	public List<ICommand> getForkCommand(){ return forkCommands;}
	
	public CommandManager(ACore plugin) {
		super(plugin, false);
		this.commands = new ArrayList<>();
		this.forkCommands = new ArrayList<>();
		registerListener(new CommandListener(this));
		if(Conf.isUseStorage()) {
			registerData(new CommandData(this));
		}else {
			logConsol("CommandData desactivé");
		}
	}
	
	public CommandManager addCommand(ICommand command) { 
		if(command instanceof APlayerCommandTimer) {
			if(Conf.isUseStorage()) {
				((CommandData)getData()).loadCommand((APlayerCommandTimer)command);
			}else {
				logConsol("Impossible de load la command : " + ChatColor.AQUA + command.getName());
				return this;
			}
				
		}
		this.commands.add(command);
		return this;
	}
	
	public void addForkCommand(ICommand command) {
		if(command instanceof APlayerCommandTimer) {
			if(Conf.isUseStorage()) {
				((CommandData)getData()).loadCommand((APlayerCommandTimer)command);
			}else {
				logConsol("Impossible de load la command : " + ChatColor.AQUA + command.getName());
				return;
			}
				
		}
		if(forkCommands.contains(command)) {
			forkCommands.remove(command);
		}
		this.forkCommands.add(command);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		String commandName = cmd.getName();
		for(ICommand command : commands) {
			if(command.getName().equals(commandName) || command.getAlliases().contains(commandName)) {
				ICommandPreform performedCommand = getArgumentToCommand(command, args);
				CommandStats commandStats = performedCommand.getCommand().performCommand(sender, performedCommand.getArgs());
				
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
					sender.sendMessage(Setupable.replace(Conf.getSyntaxErrorMessage(), "{SYNTAXE}", performedCommand.getCommand().getSyntax() == null ? "Indisponible" : performedCommand.getCommand().getSyntax()));
					break;
				case TIMER_CANCEL:
					sender.sendMessage(Setupable.replace(Conf.getDelayCancelMessage(), "{DELAY}", ((APlayerCommandTimer) performedCommand.getCommand()).getPlayerTimer((Player)sender).getTimeToString()));
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
				
				logConsol(ChatColor.GOLD + "Command preProcess " + ChatColor.AQUA + commandName + ChatColor.YELLOW +" By " + ChatColor.AQUA + sender.getName() + ChatColor.YELLOW + " Result " + ChatColor.AQUA + commandStats.name());
				return true;
			}
		}
		return false;
		
	}
	
	public ICommandPreform getArgumentToCommand(ICommand commandRoot, String[] args) {
		
		if(args.length == 0) return new ICommandPreform(commandRoot, args);
		
		for(ICommand command : commandRoot.getArguments()) {
			if(args[0].equalsIgnoreCase(command.getName()) || command.getAlliases().contains(args[0])) {
				List<String> data = new ArrayList<>();
				if(args.length > 1) {
					for(int i = 1; i < args.length; i++) data.add(args[i]);
				}
				return getArgumentToCommand(command, data.toArray(new String[0]));
			}
		}
		return new ICommandPreform(commandRoot, args);
	}
}
