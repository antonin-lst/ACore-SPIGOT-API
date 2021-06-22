package fr.azefgh456.acore.hook.hooks;

import org.bukkit.plugin.PluginManager;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;

public class WorldGuardHook extends IHook{
	
	private WorldGuardPlugin worldGuard;
	public WorldGuardPlugin getWorldGuard() { return this.worldGuard;}
	
	public WorldGuardHook() {
		worldGuard = null;
	}
	
	@Override
	public IHook hook(PluginManager pm) throws PluginNotFoundException {
		if(pm.getPlugin(getHookName()) == null) throw new PluginNotFoundException(this);
		
		worldGuard = WorldGuardPlugin.inst();
		
		if(worldGuard != null) {
			 setHooked();
	         return this;
		}
	    
	    return null;
	}

	@Override
	public String getHookName() {
		return "WorldGuard";
	}

}
