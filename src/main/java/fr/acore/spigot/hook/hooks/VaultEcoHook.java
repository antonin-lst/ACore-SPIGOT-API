package fr.acore.spigot.hook.hooks;

import org.bukkit.plugin.ServicesManager;

import fr.acore.spigot.hook.impl.ServiceProviderHook;
import net.milkbowl.vault.economy.Economy;

public class VaultEcoHook extends ServiceProviderHook<Economy> {

	public VaultEcoHook(ServicesManager hooker) {
		super(hooker, "Vault", Economy.class);
		// TODO Auto-generated constructor stub
	}


	

}
