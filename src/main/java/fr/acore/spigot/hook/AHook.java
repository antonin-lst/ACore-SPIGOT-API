package fr.acore.spigot.hook;

import fr.acore.spigot.api.hook.IHook;

public abstract class AHook<T, U> implements IHook<T> {

	protected T hooker;
	public T getHooker() { return this.hooker;}
	
	protected U hook;
	public U getHook() { return this.hook;}
	
	private String hookName;
	private boolean isHooked;
	
	public AHook(T hooker, String hookName) {
		this.hooker = hooker;
		this.hookName = hookName;
		this.isHooked = false;
	}
	
	@Override
	public String getHookName() {
		return this.hookName;
	}
	
	@Override
	public boolean isHooked() {
		return this.isHooked;
	}
	
	@Override
	public void setHooked(boolean isHooked) {
		this.isHooked = isHooked;
	}
	
}
