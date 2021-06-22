package fr.azefgh456.acore.storage.utils;

import fr.azefgh456.acore.manager.AManager;

public interface SaveableObject {

	
	public abstract void load(AManager manager, Data<?> data);
	public abstract void save(AManager manager, Data<?> data);
	
}
