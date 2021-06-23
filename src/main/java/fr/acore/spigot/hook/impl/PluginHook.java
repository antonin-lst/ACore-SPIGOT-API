package fr.acore.spigot.hook.impl;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import fr.acore.spigot.api.hook.IHook;
import fr.acore.spigot.api.hook.exception.HookFailException;
import fr.acore.spigot.hook.AHook;

public class PluginHook<T extends Plugin> extends AHook<PluginManager, T> {
	
	public PluginHook(PluginManager hooker, String hookName) {
		super(hooker, hookName);
	}
	
	@Override
	public IHook<PluginManager> hook() throws HookFailException {
		if(hooker.getPlugin(getHookName()) == null) throw new HookFailException(this);
		
		hook = (T) hooker.getPlugin(getHookName());
		
		if(hook != null) {
			setHooked(true);
		}
		
		return this;
	}
	
	

}
