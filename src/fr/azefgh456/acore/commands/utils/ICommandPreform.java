package fr.azefgh456.acore.commands.utils;

public class ICommandPreform {
	
	private ICommand command;
	public ICommand getCommand() { return this.command;}
	private String[] args;
	public String[] getArgs() { return this.args;}
	
	public ICommandPreform(ICommand command, String[] args) {
		this.command = command;
		this.args = args;
	}

}
