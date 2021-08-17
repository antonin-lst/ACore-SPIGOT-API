package fr.acore.spigot.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.acore.spigot.api.command.CommandStats;
import fr.acore.spigot.api.command.sender.ICommandSender;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.commands.sender.CorePlayerSender;

public abstract class APlayerCommand extends ACommandSenderCommand {


	public APlayerCommand(String name) {
		super(name);
	}

	@Override
	public CommandStats performCommand(ICommandSender<CommandSender> sender, String... args) {
		if(sender.getSender() instanceof Player) {
			return performAPlayerCommand(new CorePlayerSender(instance.getCorePlayer((Player) sender.getSender())), args);
		}else {
			return CommandStats.ONLY_PLAYER;
		}
	}
	
	public abstract CommandStats performAPlayerCommand(ICommandSender<CorePlayer<?>> sender, String... args);
	
}
