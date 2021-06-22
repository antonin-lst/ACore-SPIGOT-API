package fr.azefgh456.acore.logger.manager;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import fr.azefgh456.acore.config.utils.Conf;
import fr.azefgh456.acore.event.EventWrapper;
import fr.azefgh456.acore.logger.event.LogDispatchEvent;
import fr.azefgh456.acore.logger.listener.LoggerListener;
import fr.azefgh456.acore.logger.utils.ILogger;
import fr.azefgh456.acore.logger.utils.Levels;
import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.plugin.IPlugin;

public class LoggerManager extends AManager implements ILogger{

	private JavaPlugin plugin;
	private ConsoleCommandSender console;
	
	private File root;
	private File logFile;
	private PrintWriter writer;
	
	public LoggerManager(IPlugin plugin) {
		super(plugin, false);
		this.plugin = plugin;
		this.console = plugin.getServer().getConsoleSender();
		createFile();
		registerListener(new LoggerListener(this));
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
		EventWrapper.callEvent(plugin, new LogDispatchEvent<String[]>(args) {
			
			@Override
			public void printStackTrace() throws IOException {
				println(logFile, writer, getMessages());
			}
			
		});
	}

	private void log(Levels level, Object... args) {
		
		for(Object arg : args) {
			console.sendMessage(Conf.getConsolPrefix() + String.valueOf(arg));
		}
		EventWrapper.callEvent(plugin, new LogDispatchEvent<Object[]>(args) {
			
			@Override
			public void printStackTrace() throws IOException {
				println(logFile, writer, getMessages());
			}
			
		});
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
