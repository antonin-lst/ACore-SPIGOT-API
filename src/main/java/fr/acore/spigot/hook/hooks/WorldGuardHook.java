package fr.acore.spigot.hook.hooks;

import org.bukkit.plugin.PluginManager;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import fr.acore.spigot.hook.impl.PluginHook;

public class WorldGuardHook extends PluginHook<WorldGuardPlugin> {

	public WorldGuardHook(PluginManager hooker) {
		super(hooker, "WorldGuard");
	}

}
