package fr.acore.spigot.hook.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.hook.IHook;
import fr.acore.spigot.api.hook.IHookManager;
import fr.acore.spigot.api.hook.exception.HookFailException;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.hook.hooks.EssentialsHook;
import fr.acore.spigot.hook.hooks.FactionHook;
import fr.acore.spigot.hook.hooks.LuckPermHook;
import fr.acore.spigot.hook.hooks.VaultEcoHook;
import fr.acore.spigot.hook.hooks.WorldEditHook;
import fr.acore.spigot.hook.hooks.WorldGuardHook;

public class HookManager implements IHookManager {

	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin(){ return this.plugin;}
	
	private List<IHook<?>> hooks;
	
	public HookManager(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		this.hooks = new ArrayList<>();
		hookInternalModule();
	}

	@Override
	public void informe() {
		for(IHook<?> hook : hooks) {
			if(hook.isHooked()) plugin.log(ChatColor.GOLD + hook.getHookName() + ChatColor.YELLOW + " est Hook");
		}
	}

	
	private void hookInternalModule() {
		PluginManager pm = plugin.getServer().getPluginManager();
		ServicesManager sm = plugin.getServer().getServicesManager();
		IHook<?> listHookedClass[] = {
				   new EssentialsHook(pm), 
				   new VaultEcoHook(sm),
				   new LuckPermHook(sm), 
				   new WorldEditHook(pm),
				   new WorldGuardHook(pm),
				   new FactionHook(pm),
				  // new MCPHook()
				   };

		for(IHook<?> hclass : listHookedClass) {
			try {
				registerHook(hclass);
			} catch (HookFailException e) {
				plugin.logErr(ChatColor.RED + "Hook Impossible : " + ChatColor.GRAY + e.getMessage());
			}
		}
	}
	
	@Override
	public List<IHook<?>> getHooks() {
		return this.hooks;
	}

	@Override
	public <T extends IHook<?>> T getHook(Class<T> clazz) {
		for(IHook<?> hooked : hooks) {
			if(hooked.getClass().equals(clazz)) {
				return (T) hooked;
			}
		}
		plugin.logErr("Le plugin " + clazz.getSimpleName() + " est indisponible");
		return null;
	}

	@Override
	public <T extends IHook<?>> T getHook(String hookName) {
		for(IHook<?> hooked : hooks) {
			if(hooked.getHookName().equals(hookName)) {
				return (T) hooked;
			}
		}
		plugin.logErr("Le plugin " + hookName + " est indisponible");
		return null;
	}

	@Override
	public <T> void registerHook(IHook<T> hook) throws HookFailException {
		hooks.add(hook);
		hook.hook();
	}
	
	/*
	 * 
	 * Gestion des logs
	 * 
	 */
	
	@Override
	public void log(String... args) {
		plugin.log(args);
	}
	
	@Override
	public void log(Object... args) {
		plugin.log(args);
	}

	@Override
	public void logWarn(String... args) {
		plugin.logWarn(args);
	}
	
	@Override
	public void logWarn(Object... args) {
		plugin.logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		plugin.logErr(args);
	}

	@Override
	public void logErr(Object... args) {
		plugin.logErr(args);
	}


}
