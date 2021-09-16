package fr.acore.spigot.module.manager;

import java.util.ArrayList;
import java.util.List;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.api.plugin.module.IModule;
import fr.acore.spigot.api.plugin.module.IModuleManager;

public class AModuleManager implements IModuleManager{

	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin(){ return this.plugin;}
	
	private List<IModule> modules;
	
	public AModuleManager(ACoreSpigotAPI plugin) {
		this.plugin = plugin;
		this.modules = new ArrayList<>();
	}
	
	/*
	 * 
	 * Gestion de la liste des module disponible
	 * 
	 */
	
	@Override
	public List<IModule> getModules() {
		return this.modules;
	}

	@Override
	public void addModule(IModule module) {
		this.modules.add(module);
	}

	@Override
	public void removeModule(IModule module) {
		this.modules.remove(module);
	}
	
	/*
	 * 
	 * Gestion de la licence d'un module
	 * 
	 */

	@Override
	public void setModuleLicenceChecked(String moduleName, boolean valid) {
		for(IModule module : modules) {
			if(module.getPluginName().equals(moduleName)) {
				
				if(valid) module.setValidLicence();
				
				module.setLicenceChecked();
			}
		}
	}
	
	/*
	 * 
	 * Permet de récuperer un manager par ca class dans la list des modules existant
	 * voir HookSysteme
	 * 
	 */

	@Override
	public <T extends IManager> T getInModulesManager(Class<T> clazz) {
		for(IModule module : modules) {
			if(module.getInternalManager(clazz) != null) return module.getInternalManager(clazz);
		}
		return null;
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
