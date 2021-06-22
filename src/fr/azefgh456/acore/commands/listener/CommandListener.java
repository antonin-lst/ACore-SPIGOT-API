package fr.azefgh456.acore.commands.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

import fr.azefgh456.acore.commands.APlayerCommandTimer;
import fr.azefgh456.acore.commands.manager.CommandManager;
import fr.azefgh456.acore.commands.utils.CommandStats;
import fr.azefgh456.acore.commands.utils.ICommand;
import fr.azefgh456.acore.commands.utils.ICommandPreform;
import fr.azefgh456.acore.config.Setupable;
import fr.azefgh456.acore.config.utils.Conf;

public class CommandListener implements Listener{

	private CommandManager commandM;
	
	public CommandListener(CommandManager commandM) {
		this.commandM = commandM;
	}
	
	@EventHandler
	public void onCommandPreProcess(ServerCommandEvent event) {
		if(event.getCommand().equalsIgnoreCase("reload") || event.getCommand().equalsIgnoreCase("rl")) {
			event.setCancelled(true);
			if(event.getSender().getName().equals("CONSOLE")) {
				commandM.getForkCommand().forEach(cmd -> {
					if(cmd.getName().equals("reload")) cmd.performCommand(event.getSender(), "");
				});
			}
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
		System.out.println("debug : " + event.getMessage());
		String cmdComplette = event.getMessage().replace("/", "");
		String cmdBase = cmdComplette.contains(" ") ? cmdComplette.split(" ")[0] : cmdComplette;
		List<String> args = new ArrayList<>();
		
		if(!cmdBase.equals(cmdComplette)) {
			String data[] = cmdComplette.split(" ");
			for(int i = 1; i < data.length; i++) args.add(data[i]);
		}
		
		Player sender = event.getPlayer();
		
		for(ICommand forkCommand : commandM.getForkCommand()) {
			if(forkCommand.getName().equals(cmdBase) || forkCommand.getAlliases().contains(cmdBase)) {
				event.setCancelled(true);
				ICommandPreform performedCommand = commandM.getArgumentToCommand(forkCommand, args.toArray(new String[0]));
				CommandStats commandStats = performedCommand.getCommand().performCommand(sender, args.toArray(new String[0]));
				
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
					event.setCancelled(false);
					return;
				default:
					break;
				}
				commandM.logConsol(ChatColor.GOLD + "ForkCommand preProcess " + ChatColor.AQUA + cmdBase + ChatColor.YELLOW +" By " + ChatColor.AQUA + sender.getName() + ChatColor.YELLOW + " Result " + ChatColor.AQUA + commandStats.name());		
			}
		}
	}
}
