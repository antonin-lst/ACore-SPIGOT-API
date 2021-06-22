package fr.azefgh456.acore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.azefgh456.acore.commands.utils.CommandStats;
import fr.azefgh456.acore.commands.utils.ICommand;

public abstract class ASenderCommand extends ICommand{

	@Override
	public CommandStats performCommand(CommandSender sender, String... args) {
		if(sender instanceof Player) {
			return CommandStats.ONLY_CONSOL;
		}
		return performACommand(sender, args);
	}

	public abstract CommandStats performACommand(CommandSender sender, String... args);

}
