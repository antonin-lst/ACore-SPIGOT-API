package fr.acore.spigot.logger;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.command.ConsoleCommandSender;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.logger.ILoggerManager;
import fr.acore.spigot.api.logger.Levels;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.api.runnable.IRunnable;
import fr.acore.spigot.api.runnable.RunnableUsage;
import fr.acore.spigot.config.utils.Conf;

public class LoggerManager implements ILoggerManager {

	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin(){ return this.plugin;}
	private ConsoleCommandSender console;
	
	private boolean printterEnable;
	
	private File root;
	private File logFile;
	private PrintWriter writer;
	
	private LoggerListener loggerListener;
	
	public LoggerManager(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		this.console = plugin.getServer().getConsoleSender();
		createFile();
		plugin.registerListener(loggerListener = new LoggerListener(this));
	}
	
	public void enableAsyncPrintter() {
		plugin.registerAsyncRunnable(RunnableUsage.LONG_TASK, (IRunnable) loggerListener);
		printterEnable = true;
	}
	
	public void disable() {
		writer.close();
	}
	
	private void createFile() {
		File pluginFolder = plugin.getDataFolder();
		if(!pluginFolder.exists()) pluginFolder.mkdir();
		
		root = new File(plugin.getDataFolder(), "logs");
		if(!root.exists()) root.mkdir();
		
		logFile = new File(root, "logs.txt");
		int index = 1;
		while(logFile.exists()) {
			logFile = new File(root, "logs" + index + ".txt");
			index++;
		}
		
		try {
			logFile.createNewFile();
			writer = new PrintWriter(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void log(Levels level, String... args) {
		args = formatPrefix(level, args);
		for(String arg : args) { 
			console.sendMessage(arg);
		}
		if(printterEnable) {
			plugin.callEvent( new LogDispatchEvent<String[]>(args) {
				
				@Override
				public void printStackTrace() throws IOException {
					println(logFile, writer, getMessages());
				}
				
			});
		}
	}

	private void log(Levels level, Object... args) {
		
		for(Object arg : args) {
			console.sendMessage(Conf.getConsolPrefix() + String.valueOf(arg));
		}
		if(printterEnable) {
			plugin.callEvent(new LogDispatchEvent<Object[]>(args) {
				
				@Override
				public void printStackTrace() throws IOException {
					println(logFile, writer, getMessages());
				}
				
			});
		}
	}
	
	private String[] formatPrefix(Levels level, String... args) {
		String[] data = new String[args.length];
		int i = 0;
		for(String arg : args) {
			data[i] = level.getDesc() + " " + Conf.getConsolPrefix() + arg;
			i++;
		}
		return data;
	}
	
	@Override
	public void log(String... args) {
		log(Levels.INFO, args);
	}

	@Override
	public void logWarn(String... args) {
		log(Levels.WARN, args);
	}

	@Override
	public void logErr(String... args) {
		log(Levels.ERR, args);
	}

	@Override
	public void log(Object... args) {
		log(Levels.INFO, args);
	}

	@Override
	public void logWarn(Object... args) {
		log(Levels.WARN, args);
	}

	@Override
	public void logErr(Object... args) {
		log(Levels.ERR, args);
	}
}
