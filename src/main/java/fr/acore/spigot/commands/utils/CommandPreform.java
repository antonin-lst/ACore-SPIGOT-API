package fr.acore.spigot.commands.utils;

import fr.acore.spigot.api.command.ICommand;
import fr.acore.spigot.api.command.ICommandPreform;

public class CommandPreform implements ICommandPreform {

	private ICommand command;
	private String[] args;
	
	public CommandPreform(ICommand command, String[] args) {
		this.command = command;
		this.args = args;
	}
	
	@Override
	public ICommand getCommand() {
		return this.command;
	}

	@Override
	public String[] getArgs() {
		return this.args;
	}

}
