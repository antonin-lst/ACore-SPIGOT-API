package fr.azefgh456.acore.config.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.config.Setupable;
import fr.azefgh456.acore.storage.utils.StorageType;

public class Conf extends Setupable{
	
	/*
	 * Logger
	 * Configuration
	 */
	private static String prefix;
	public static String getPrefix() { return prefix == null ? ChatColor.YELLOW + "A" + ChatColor.GOLD + "Core "+ ChatColor.GREEN : prefix;}
	private static String consolPrefix;
	public static String getConsolPrefix() { return consolPrefix == null ? ChatColor.YELLOW + "A" + ChatColor.GOLD + "Core "+ ChatColor.GREEN : consolPrefix;}
	private static boolean verbose;
	public static boolean isVerbose() { return verbose;}
	private static boolean verboseEvent = false;
	public static boolean isVerboseEvent() { return verboseEvent;}
	
	/*
	 * Storage
	 * configuration
	 */
	private static boolean useStorage;
	public static boolean isUseStorage() { return useStorage;}
	private static StorageType storageType;
	public static StorageType getStorageType() { return storageType == null ? StorageType.JSON : storageType;}
	//sql
	private static String urlbase;
	public static String getUrlBase() { return urlbase;}
	private static String host;
	public static String getHost() { return host;}
	private static String database;
	public static String getDatabase() { return database;}
	private static String user;
	public static String getUser() { return user;}
	private static String pass;
	public static String getPass() { return pass;}
	//json
	
	/*
	 * Command
	 * Configuration
	 */
	private static String syntaxErrorMessage;
	public static String getSyntaxErrorMessage() { return syntaxErrorMessage;}
	private static String permissionDeniedMessage;
	public static String getPermissionDeniedMessage() { return permissionDeniedMessage;}
	private static String playerOnlyMessage;
	public static String getPlayerOnlyMessage() { return playerOnlyMessage;}
	private static String consolOnlyMessage;
	public static String getConsolOnlyMessage() { return consolOnlyMessage;}
	private static String delayCancelMessage;
	public static String getDelayCancelMessage() { return delayCancelMessage;}
	
	private static boolean bungee;
	public static boolean useBungee() { return bungee;}
	
	private static boolean mcp;
	public static boolean useMcp() { return mcp;}
	
	/*
	 * RedisConfiguration
	 * 
	 */
	private static String jedisHost;
	public static String getJedisHost() { return jedisHost;}
	private static int jedisPort;
	public static int getJedisPort() { return jedisPort;}
	private static String jedisPassword;
	public static String getJedisPassword() { return jedisPassword;}
	
	
	public Conf(ACore plugin) {
		super(plugin, true);
	}

	@Override
	public void setup(FileConfiguration config) {
		prefix = convertColor(config.getString("logger.prefix"));
		consolPrefix = convertColor(config.getString("logger.consolPrefix"));
		verbose = config.getBoolean("logger.verbose");
		
		useStorage = config.getBoolean("storage.enable");
		storageType = StorageType.valueOf(config.getString("storage.type"));
		urlbase = config.getString("storage.sql.urlbase");
		host = config.getString("storage.sql.host");
		database = config.getString("storage.sql.database");
		user = config.getString("storage.sql.user");
		pass = config.getString("storage.sql.pass");
		
		syntaxErrorMessage = replace(convertColor(config.getString("commands.syntaxErrorMessage")), "{PREFIX}", getPrefix());
		permissionDeniedMessage = replace(convertColor(config.getString("commands.permissionDeniedMessage")), "{PREFIX}", getPrefix());
		playerOnlyMessage = replace(convertColor(config.getString("commands.playerOnlyMessage")), "{PREFIX}", getPrefix());
		consolOnlyMessage = replace(convertColor(config.getString("commands.consolOnlyMessage")), "{PREFIX}", getPrefix());
		delayCancelMessage = replace(convertColor(config.getString("commands.delayCancelMessage")), "{PREFIX}", getPrefix());
	
		bungee = config.getBoolean("bungee");
		mcp = config.getBoolean("mcp");
		
		jedisHost = config.getString("jedis.host");
		jedisPort = config.getInt("jedis.port");
		jedisPassword = config.getString("jedis.password");
	}
	
	public static void setStorageType(StorageType type) {
		storageType = type;
	}
}
