package fr.acore.spigot.api.plugin.module;

import java.util.List;

import fr.acore.spigot.api.manager.IManager;

public interface IModuleManager extends IManager{
	
	public List<IModule> getModules();
	public void addModule(IModule module);
	public void removeModule(IModule module);
	
	public void setModuleLicenceChecked(String moduleName, boolean valid);
	
	public <T extends IManager> T getInModulesManager(Class<T> clazz);

}
