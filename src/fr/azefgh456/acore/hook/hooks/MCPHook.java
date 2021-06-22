package fr.azefgh456.acore.hook.hooks;

import org.bukkit.plugin.PluginManager;

import fr.azefgh456.acore.config.utils.Conf;
import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;
import fr.azefgh456.acore.nms.utils.ServerVersion;

public class MCPHook extends IHook{

	public MCPHook() {
		
	}
	
	@Override
	public IHook hook(PluginManager pm) throws PluginNotFoundException {
		if(Conf.useMcp())
			setHooked();
		return this;
	}

	@Override
	public String getHookName() {
		return "MCP " + ServerVersion.getVersion();
	}

}
