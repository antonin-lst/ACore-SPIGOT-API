package fr.acore.spigot.hook.hooks;

import org.bukkit.plugin.ServicesManager;

import fr.acore.spigot.hook.impl.ServiceProviderHook;
import net.luckperms.api.LuckPerms;

public class LuckPermHook extends ServiceProviderHook<LuckPerms> {

	public LuckPermHook(ServicesManager hooker) {
		super(hooker, "LuckPerms");
	}

}
