package fr.acore.spigot.api.hook;

import java.util.List;

import fr.acore.spigot.api.hook.exception.HookFailException;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.manager.Informable;

public interface IHookManager extends IManager, Informable{

	public List<IHook<?>> getHooks();
	
	
	public <T extends IHook<?>> T getHook(Class<T> clazz);
	public <T extends IHook<?>> T getHook(String hookName);
	
	public <T> void registerHook(IHook<T> hook) throws HookFailException;
}
