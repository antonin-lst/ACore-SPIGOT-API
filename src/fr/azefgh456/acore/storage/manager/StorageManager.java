package fr.azefgh456.acore.storage.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import fr.azefgh456.acore.manager.IManager;
import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.utils.Data;
import fr.azefgh456.acore.storage.utils.IStorage;
import fr.azefgh456.acore.storage.utils.StorageType;

public class StorageManager<T extends IStorage> implements IManager{

	private IPlugin plugin;
	
	private T storage;
	public T getStorage() { return this.storage;}
	
	private List<Data<?>> dataStorages;
	
	public StorageManager(IPlugin plugin, T storage) {
		this.plugin = plugin;
		this.storage = storage;
		this.dataStorages = new ArrayList<>();
	}
	
	public StorageType getStorageType() {
		return storage.getStorageType();
	}

	public void addData(Data<?> data) {
		storage.loadSimpleData(data);
		dataStorages.add(data);
	}

	public void saveAll() {
		plugin.log("Save Storage");
		storage.saveAll(dataStorages);
	}
	
	public void save(Data<?> data) {
		storage.save(data);
	}

	public void disabled() {
		saveAll();
		storage.disabled();
	}
	
	@Override
	public String logEnabled() {
		return getStorageType() + getClass().getSimpleName() + ChatColor.YELLOW + " Enabled";
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof StorageManager<?>) return true;
		
		return false;
	}
	
	
}
