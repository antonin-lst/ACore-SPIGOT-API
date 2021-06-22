package fr.azefgh456.acore.hook.exception;

import fr.azefgh456.acore.hook.IHook;

@SuppressWarnings("serial")
public class PluginNotFoundException extends Exception {

	private IHook failedHook;
	
	public PluginNotFoundException(IHook failedHook) {
		this.failedHook = failedHook;
	}

	@Override
	public String getMessage() {
		return failedHook.getHookName();
	}
	
}
