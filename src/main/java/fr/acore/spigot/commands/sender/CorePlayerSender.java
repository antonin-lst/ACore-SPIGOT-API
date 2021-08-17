package fr.acore.spigot.commands.sender;

import fr.acore.spigot.api.command.sender.ICommandSender;
import fr.acore.spigot.api.player.impl.CorePlayer;

public class CorePlayerSender implements ICommandSender<CorePlayer<?>> {

	private CorePlayer<?> sender;
	
	public CorePlayerSender(CorePlayer<?> sender) {
		this.sender = sender;
	}

	@Override
	public CorePlayer<?> getSender() {
		return this.sender;
	}

	@Override
	public boolean hasPermission(String perm) {
		return sender.getPlayer().hasPermission(perm);
	}

	@Override
	public boolean isInstanceOf(Class<?> clazz) {
		return this.getClass().equals(clazz);
	}

}
