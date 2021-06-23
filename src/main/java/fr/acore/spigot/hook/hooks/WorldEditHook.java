package fr.acore.spigot.hook.hooks;

import org.bukkit.plugin.PluginManager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.acore.spigot.hook.impl.PluginHook;

public class WorldEditHook extends PluginHook<WorldEditPlugin> {

	public WorldEditHook(PluginManager hooker) {
		super(hooker, "WorldEdit");
	}

}
