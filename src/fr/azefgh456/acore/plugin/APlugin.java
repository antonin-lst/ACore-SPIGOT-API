package fr.azefgh456.acore.plugin;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import com.google.gson.JsonObject;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.commands.utils.ICommand;
import fr.azefgh456.acore.config.Setupable;
import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.jedis.manager.JedisManager;
import fr.azefgh456.acore.jedis.pubsub.action.JedisActions;
import fr.azefgh456.acore.manager.IManager;
import fr.azefgh456.acore.menu.AMenu;
import fr.azefgh456.acore.player.players.CorePlayer;
import fr.azefgh456.acore.player.players.HookedPlayer;
import fr.azefgh456.acore.plugin.manager.APluginManager;
import fr.azefgh456.acore.runnable.ARunnable;
import fr.azefgh456.acore.storage.utils.Data;
import fr.azefgh456.acore.storage.utils.IStorage;
import fr.azefgh456.acore.utils.time.TimerBuilder;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class APlugin extends IPlugin implements ARunnable{

	private ACore plugin;
	private boolean licenseChecked;
	public boolean getLicenceChecked() { return this.licenseChecked;}
	public void setLicenseChecked() { licenseChecked = true;}
	private TimerBuilder licenseCheckTimer;
	

	@Override
	public void onEnable() {
		plugin = ACore.plugin;
		((APluginManager) getManager(APluginManager.class)).addPlugin(this);
		licenseChecked = false;
		super.onEnable();
		log("Starting License And Update check du plugin " + getClass().getSimpleName());
		enableModule();
		licenseCheckTimer = new TimerBuilder(15000L);
		registerSyncRunnable(this);
		
	}
	
	/*
	 * License Check + Maj Check
	 * 
	 */
	
	public void enableModule() {
		JedisManager jedisM = plugin.getManager(JedisManager.class);
		JsonObject json = new JsonObject();
		json.addProperty("serverName", plugin.getServerName());
		json.addProperty("moduleName", getClass().getSimpleName());
		json.addProperty("moduleVersion", getDescription().getVersion());
		json.addProperty("moduleToken", getConfig().getString("license"));
		jedisM.sendMessageToMain(JedisActions.CHECK_UPDATE_MODULE, json);
	}
	
	
	/*
	 * 
	 * Register
	 * 
	 */
	
	@Override
	public void registerManager(IManager manager) {
		plugin.registerManager(manager);
	}
	
	@Override
	public <T> T getManager(Class<? extends IManager> clazz) {
		return plugin.getManager(clazz);
	}
	
	
	@Override
	public void registerSetupable(Setupable setupable) {
		plugin.registerSetupable(setupable);
	}
	

	@Override
	public void registerCommand(String name, ICommand command) {
		plugin.registerCommand(this, name, command);
	}
	
	public void registerForkCommand(String name, ICommand command) {
		plugin.registerForkCommand(name, command);
	}
	
	@Override
	public Data<?> registerData(Data<?> data) {
		plugin.registerData(data);
		return data;
	}
	
	@Override
	public Listener registerListener(Listener listener) {
		return plugin.registerListener(listener);
	}

	@Override
	public ARunnable registerSyncRunnable(ARunnable runnable) {
		return plugin.registerSyncRunnable(runnable);
	}

	@Override
	public ARunnable registerAsyncRunnable(ARunnable runnable) {
		return plugin.registerAsyncRunnable(runnable);
	}
	
	/*
	 * 
	 * Logger
	 * 
	 */
	
	@Override
	public void log(String... args) {
		plugin.log(args);
	}

	@Override
	public void logWarn(String... args) {
		plugin.logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		plugin.logErr(args);
	}

	@Override
	public void log(Object... args) {
		plugin.log(args);
	}

	@Override
	public void logWarn(Object... args) {
		plugin.logWarn(args);
	}

	@Override
	public void logErr(Object... args) {
		plugin.logErr(args);
	}
	
	public void logAPluginLink(APlugin linkedPlugin) {
		log(ChatColor.GREEN + linkedPlugin.getName() + ChatColor.YELLOW + " est maintenant relié au " + ChatColor.GOLD + "A" + ChatColor.YELLOW + "Core");
	}

	/*
	 * 
	 * Hooks
	 * 
	 */
	
	public <T extends IHook> T getHook(String name){
		return (T) plugin.getHook(name);
	}
	
	@Override
	public void registerHook(IHook hook) {
		plugin.registerHook(hook);	
	}
	
	/*
	 * 
	 * Storage
	 * 
	 */
	
	@Override
	public IStorage getStorage() {
		return plugin.getStorage();
	}
	
	/*
	 * 
	 * CorePlayer 
	 * (LoginPlayer/HookedPlayer/NMSPlayer/AbstractPlayer)
	 * 
	 */
	
	@Override
	public CorePlayer getCorePlayer(Player player) {
		return plugin.getCorePlayer(player);
	}
	
	@Override
	public HookedPlayer getOfflinePlayer(OfflinePlayer player) {
		return plugin.getOfflinePlayer(player);
	}

	@Override
	public int getRegisteredSize() {
		return plugin.getRegisteredSize();
	}

	/*
	 * Deprecated use the static method 
	 * EventWrapper#callEvent(Event event);
	 * 
	 */
	
	@Deprecated
	@Override
	public void callEvent(Event event) {
		plugin.callEvent(event);
	}

	@Override
	public double getTPS() {
		return plugin.getTPS();
	}
	
	@Override
	public void openMenu(Player player, AMenu menu) {
		plugin.openMenu(player, menu);
	}
	
	public boolean playerHasMenuOpen(Player player) {
		return plugin.playerHasMenuOpen(player);
	}

	@Override
	public void ticks() {
		if(licenseChecked) return;
		
		if(licenseCheckTimer.hisFished()) {
			logErr("Impossible de verifier la license de " + getClass().getSimpleName());
			MinecraftServer.getServer().safeShutdown();
		}
	}

	@Override
	public void checkLicencePlugin(String name) {
		logErr("Impossible de check la licence d'un module avec un module. Try with API");
	}
}
