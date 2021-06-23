package fr.acore.spigot.hook.hooks;

import org.bukkit.plugin.PluginManager;

import com.earth2me.essentials.IEssentials;

import fr.acore.spigot.hook.impl.PluginHook;

public class EssentialsHook extends PluginHook<IEssentials> {

	public EssentialsHook(PluginManager hooker) {
		super(hooker, "Essentials");
	}

}
