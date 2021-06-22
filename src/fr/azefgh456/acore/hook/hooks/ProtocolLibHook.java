package fr.azefgh456.acore.hook.hooks;

import org.bukkit.plugin.PluginManager;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;

public class ProtocolLibHook extends IHook{

	private ProtocolManager protocolManager;
	public ProtocolManager getProtocolManager() { return this.protocolManager;}
	
	@Override
	public IHook hook(PluginManager pm) throws PluginNotFoundException {
		
		if(pm.getPlugin(getHookName()) == null) throw new PluginNotFoundException(this);
		
		protocolManager = ProtocolLibrary.getProtocolManager();
		
		if(protocolManager != null ) {
			setHooked();
			return this;
		}
		
		return null;
	}

	@Override
	public String getHookName() {
		return "ProtocolLib";
	}

}
