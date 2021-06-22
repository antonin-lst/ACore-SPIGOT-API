package fr.azefgh456.acore.hook.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;
import fr.azefgh456.acore.hook.hooks.EssentialsHook;
import fr.azefgh456.acore.hook.hooks.FactionHook;
import fr.azefgh456.acore.hook.hooks.LuckPermHook;
import fr.azefgh456.acore.hook.hooks.MCPHook;
import fr.azefgh456.acore.hook.hooks.ProtocolLibHook;
import fr.azefgh456.acore.hook.hooks.RandomTPHook;
import fr.azefgh456.acore.hook.hooks.VaultHook;
import fr.azefgh456.acore.hook.hooks.WorldEditHook;
import fr.azefgh456.acore.hook.hooks.WorldGuardHook;
import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.manager.utils.Informable;

public class HookManager extends AManager implements Informable{
	
	private List<IHook> hookClass;
	
	public HookManager(ACore plugin) {
		super(plugin, false);
		hookClass = new ArrayList<>();
		hookPlugins();
	}
	
	private void hookPlugins() {
			IHook listHookedClass[] = {new EssentialsHook(), 
								   new VaultHook(plugin),
								   new LuckPermHook(), 
								   new ProtocolLibHook(),
								   new RandomTPHook(),
								   new FactionHook(this),
								   new MCPHook(),
								   new WorldEditHook(),
								   new WorldGuardHook()};
			
			for(IHook hclass : listHookedClass) {
				try {
					registerHook(hclass);
				} catch (PluginNotFoundException | NullPointerException e) {
					plugin.logErr(ChatColor.RED + "Hook Impossible : " + ChatColor.GRAY + e.getMessage());
				}
			}
	}
	
	
	public <T extends IHook> T getHook(String hookName) {
		for(IHook hooked : hookClass) {
			if(hooked.getHookName().equals(hookName)) {
				return (T) hooked;
			}
		}
		plugin.logErr("Le plugin " + hookName + " est indisponible");
		return null;
	}

	public void logs() {
		for(IHook hook : hookClass) {
			if(hook.hisHooked()) plugin.log(ChatColor.GOLD + hook.getHookName() + ChatColor.YELLOW + " est Hook utilisation :" + ChatColor.GRAY + "?");
		}
	}

	public void registerHook(IHook hook) throws PluginNotFoundException, NullPointerException{
		hookClass.add(hook);
		hook.hook(plugin.getServer().getPluginManager());
	}
	

}
