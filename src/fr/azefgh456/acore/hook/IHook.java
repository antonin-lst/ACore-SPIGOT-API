package fr.azefgh456.acore.hook;

import org.bukkit.plugin.PluginManager;

import fr.azefgh456.acore.hook.exception.PluginNotFoundException;

public abstract class IHook {
	
	private boolean hooked = false;
	public boolean hisHooked() {
		return this.hooked;
	}
	
	public void setHooked() {
		hooked = true;
	}
	
	
	public abstract IHook hook(PluginManager pm) throws PluginNotFoundException;

	public abstract String getHookName();

}
