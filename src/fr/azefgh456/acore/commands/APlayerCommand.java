package fr.azefgh456.acore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.azefgh456.acore.commands.utils.CommandStats;
import fr.azefgh456.acore.commands.utils.ICommand;

public abstract class APlayerCommand extends ICommand{

	
	@Override
	public CommandStats performCommand(CommandSender sender, String... args) {
		if(sender instanceof Player) {
			return performACommand((Player)sender, args);
		}
		return CommandStats.ONLY_PLAYER;
	}
	
	public abstract CommandStats performACommand(Player player, String... args);
	
}
