package fr.acore.spigot.api.hook.exception;

import fr.acore.spigot.api.hook.IHook;

public class HookFailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9056124908820250045L;

	
	private IHook failedHook;
	
	public HookFailException(IHook failedHook) {
		this.failedHook = failedHook;
	}

	@Override
	public String getMessage() {
		return failedHook.getHookName();
	}
}
