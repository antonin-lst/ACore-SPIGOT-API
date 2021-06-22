package fr.azefgh456.acore.plugin.manager;

import java.util.ArrayList;
import java.util.List;

import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.plugin.APlugin;
import fr.azefgh456.acore.plugin.IPlugin;

public class APluginManager extends AManager {

	private List<APlugin> plugins;
	
	public APluginManager(IPlugin plugin) {
		super(plugin, false);
		plugins = new ArrayList<>();
	}
	
	public void addPlugin(APlugin plugin) {
		this.plugins.add(plugin);
	}
	
	public void removePlugin(APlugin plugin) {
		this.plugins.remove(plugin);
	}
	
	public void checkPlugin(String className) {
		for(APlugin plugin : plugins) {
			System.out.println(plugin.getClass().getSimpleName());
			if(plugin.getClass().getSimpleName().equals(className)) {
				log("Licence Checked for plugin " + className);
				plugin.setLicenseChecked();
			}
		}
	}
	
	

}
