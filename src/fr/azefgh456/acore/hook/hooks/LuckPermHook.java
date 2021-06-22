package fr.azefgh456.acore.hook.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;
import net.luckperms.api.LuckPerms;

public class LuckPermHook extends IHook{

	private LuckPerms luckPerm;
	public LuckPerms getLuckPerm() { return this.luckPerm;}
	
	@Override
	public IHook hook(PluginManager pm) throws PluginNotFoundException {
		if(pm.getPlugin(getHookName()) == null) throw new PluginNotFoundException(this);
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (provider != null) {
		    luckPerm = provider.getProvider();
		    setHooked();
		}
		return this;
	}

	@Override
	public String getHookName() {
		return "LuckPerms";
	}

}
