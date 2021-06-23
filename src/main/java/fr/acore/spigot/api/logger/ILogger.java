package fr.acore.spigot.api.logger;

public interface ILogger {

	public void log(String... args);
	public void logWarn(String... args);
	public void logErr(String... args);
	
	public void log(Object... args);
	public void logWarn(Object... args);
	public void logErr(Object... args);
	
}
