package fr.azefgh456.acore.hook.hooks;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;
import fr.azefgh456.acore.plugin.IPlugin;
import net.milkbowl.vault.economy.Economy;

public class VaultHook extends IHook{
	
	private IPlugin plugin;
	
	private Economy economy;
	public Economy getEconomy() { return this.economy;}
	
	
	public VaultHook(IPlugin plugin) {
		economy = null;
		this.plugin = plugin;
	}
	

	@Override
	public IHook hook(PluginManager pm) throws PluginNotFoundException {
		if(pm.getPlugin(getHookName()) == null) throw new PluginNotFoundException(this);
		
	    RegisteredServiceProvider<Economy> economyProvider = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
	    
	    if (economyProvider != null) {
	            economy = economyProvider.getProvider();
	            setHooked();
	        }
	    return null;
	}

	@Override
	public String getHookName() {
		return "Vault";
	}

}
