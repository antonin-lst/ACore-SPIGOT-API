package fr.azefgh456.acore.hook.hooks;

import org.bukkit.plugin.PluginManager;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;

public class WorldEditHook extends IHook {

	private WorldEditPlugin worldEdit;
	public WorldEditPlugin getWorldEdit() { return this.worldEdit;}
	
	@Override
	public IHook hook(PluginManager pm) throws PluginNotFoundException {
		if(pm.getPlugin(getHookName()) == null) throw new PluginNotFoundException(this);
		
		worldEdit = (WorldEditPlugin) pm.getPlugin(getHookName());
		if(worldEdit != null) {
			setHooked();
			return this;
		}
		
		return null;
	}

	@Override
	public String getHookName() {
		return "WorldEdit";
	}

}
