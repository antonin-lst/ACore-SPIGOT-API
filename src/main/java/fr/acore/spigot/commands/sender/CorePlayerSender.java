package fr.acore.spigot.commands.sender;

import fr.acore.spigot.api.command.sender.ICommandSender;
import fr.acore.spigot.api.player.impl.CorePlayer;

public class CorePlayerSender implements ICommandSender<CorePlayer<?>> {

	
	
	public CorePlayerSender(CorePlayer<?> sender) {
		
	}

	@Override
	public CorePlayer<?> getSender() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermission(String perm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInstanceOf(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

}
