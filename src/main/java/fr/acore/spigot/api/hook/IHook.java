package fr.acore.spigot.api.hook;

import fr.acore.spigot.api.hook.exception.HookFailException;

public interface IHook<T> {
	
	/*
	 * 
	 * Nom du hook
	 * 
	 */
	
	public String getHookName();
	
	/*
	 * 
	 * Hooker
	 * 
	 */
	
	public T getHooker();
	
	/*
	 * 
	 * retourne si le hook est disponible
	 * 
	 */
	
	public boolean isHooked();
	public void setHooked(boolean isHooked);
	
	/*
	 * 
	 * Method de hook
	 * 
	 */
	
	public abstract IHook<T> hook() throws HookFailException;

}
