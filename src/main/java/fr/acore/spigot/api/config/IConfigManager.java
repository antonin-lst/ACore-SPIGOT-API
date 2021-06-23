package fr.acore.spigot.api.config;

import java.util.List;
import java.util.Map;

import fr.acore.spigot.api.manager.IManager;

public interface IConfigManager<T> extends IManager{
	
	public Map<T, List<ISetupable<T>>> getSetupables();
	public void addSetupable(ISetupable<T> setupable);
	public void removeSetupable(ISetupable<T> setupable);
	public void removeAllSetupable(T key);
	
	public void reload();
	public void reload(ISetupable<T> setupable);

}
