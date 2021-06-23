package fr.acore.spigot.hook.impl;

import fr.acore.spigot.api.hook.IHook;
import fr.acore.spigot.api.hook.exception.HookFailException;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.hook.AHook;

public class ManagerHook<T extends IManager> extends AHook<IPlugin<?>, T> {

	private Class<T> hookClazz;
	
	public ManagerHook(IPlugin<?> hooker, Class<T> hookClazz) {
		super(hooker, hookClazz.getSimpleName());
		this.hookClazz = hookClazz;
	}
	
	@Override
	public IHook<IPlugin<?>> hook() throws HookFailException {
		hook = hooker.getManager(hookClazz);
		if(hook == null) throw new HookFailException(this);
		
		setHooked(true);
		return this;
	}

}
