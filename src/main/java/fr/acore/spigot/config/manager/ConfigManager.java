package fr.acore.spigot.config.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.config.IConfigManager;
import fr.acore.spigot.api.config.ISetupable;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.config.utils.Conf;

public class ConfigManager implements IConfigManager<IPlugin<?>> {

	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin() { return this.plugin;}
	
	private Conf conf;
	public Conf getConf() {	return this.conf;}
	
	private Map<IPlugin<?>, List<ISetupable<IPlugin<?>>>> configParticipator;
	
	
	public ConfigManager(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		configParticipator = new HashMap<>();
		addSetupable(conf = new Conf(plugin));
	}
	
	/*
	 * 
	 * Gestion de la Map des classes de configuration
	 * 
	 */

	@Override
	public Map<IPlugin<?>, List<ISetupable<IPlugin<?>>>> getSetupables() {
		return this.configParticipator;
	}

	@Override
	public void addSetupable(ISetupable<IPlugin<?>> setupable) {
		if(setupable == null || setupable.getKey() == null) return;
		
		if(configParticipator.containsKey(setupable.getKey())) {
			configParticipator.get(setupable.getKey()).add(setupable);
		}else {
			List<ISetupable<IPlugin<?>>> setupableList = new ArrayList<>();
			setupableList.add(setupable);
			configParticipator.put(setupable.getKey(), setupableList);
		}
	}

	@Override
	public void removeSetupable(ISetupable<IPlugin<?>> setupable) {
		configParticipator.get(setupable.getKey()).remove(setupable);
	}
	
	@Override
	public void removeAllSetupable(IPlugin<?> key) {
		configParticipator.remove(key);
	}

	/*
	 * 
	 * Recharger la configuration d'un setupable ou de tout les setupable
	 * 
	 */
	
	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reload(ISetupable<IPlugin<?>> setupable) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * 
	 * Gestion des logs
	 * 
	 */
	
	@Override
	public void log(String... args) {
		plugin.log(args);
	}
	
	@Override
	public void log(Object... args) {
		plugin.log(args);
	}

	@Override
	public void logWarn(String... args) {
		plugin.logWarn(args);
	}
	
	@Override
	public void logWarn(Object... args) {
		plugin.logWarn(args);
	}

	@Override
	public void logErr(String... args) {
		plugin.logErr(args);
	}

	@Override
	public void logErr(Object... args) {
		plugin.logErr(args);
	}

}
