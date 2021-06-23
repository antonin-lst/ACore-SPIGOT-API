package fr.acore.spigot.commands.sender;

import org.bukkit.command.CommandSender;

import fr.acore.spigot.api.command.sender.ICommandSender;

public class PlayerAndConsolSender implements ICommandSender<CommandSender> {

	private CommandSender sender;
	
	public PlayerAndConsolSender(CommandSender sender) {
		this.sender = sender;
	}
	
	@Override
	public CommandSender getSender() {
		return this.sender;
	}

	@Override
	public boolean hasPermission(String perm) {
		return sender.hasPermission(perm);
	}

	@Override
	public boolean isInstanceOf(Class<?> clazz) {
		return getClass().isInstance(clazz);
	}

}
