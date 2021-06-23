package fr.acore.spigot.api.command.sender;

public interface ICommandSender<T> {

	public T getSender();
	
	public boolean hasPermission(String perm);
	public boolean isInstanceOf(Class<?> clazz);
	
}
