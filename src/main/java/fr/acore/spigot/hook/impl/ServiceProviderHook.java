package fr.acore.spigot.hook.impl;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

import fr.acore.spigot.api.hook.IHook;
import fr.acore.spigot.api.hook.exception.HookFailException;
import fr.acore.spigot.hook.AHook;

public class ServiceProviderHook<T> extends AHook<ServicesManager, T> {

	public ServiceProviderHook(ServicesManager hooker, String hookName) {
		super(hooker, hookName);
	}

	@Override
	public IHook<ServicesManager> hook() throws HookFailException {
		
		RegisteredServiceProvider<T> provider = (RegisteredServiceProvider<T>) hooker.getRegistration(getHook().getClass());
		
		if(provider == null) throw new HookFailException(this);
		
        hook = provider.getProvider();
        setHooked(true);
		return this;
		
	}

}
