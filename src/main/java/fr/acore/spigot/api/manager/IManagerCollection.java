package fr.acore.spigot.api.manager;

import java.util.List;

public interface IManagerCollection<U extends IManager> {

	public List<U> getInternalManagers();
	public <T extends IManager> T getInternalManager(Class<T> clazz);
	public <T extends IManager> T getManager(Class<T> clazz);
	public void registerManager(U manager);
	public void unregisterManager(U manager);
	
}
