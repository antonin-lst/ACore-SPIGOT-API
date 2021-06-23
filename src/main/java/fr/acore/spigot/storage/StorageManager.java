package fr.acore.spigot.storage;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.api.storage.IStorageManager;
import fr.acore.spigot.api.storage.database.IDatabase;
import fr.acore.spigot.api.storage.database.exception.ConnectionException;
import fr.acore.spigot.api.storage.database.exception.NotConnectedException;
import fr.acore.spigot.api.storage.exception.DBExistsException;
import fr.acore.spigot.api.storage.exception.DBNotFoundException;
import fr.acore.spigot.api.storage.factory.IDataFactory;

public class StorageManager implements IStorageManager, IManager{

	private ACoreSpigotAPI plugin;
	public IPlugin<?> getPlugin() { return this.plugin;}
	
	/*
	 * 
	 * gestion des bases de données
	 * 
	 */
	
	private List<IDatabase<?>> databases;
	private IDatabase<?> defaultDb;
	
	private List<IDataFactory<?, ?>> factories;
	
	public StorageManager(ACoreSpigotAPI plugin, IDatabase<?>... db) {
		this.plugin = plugin;
		this.databases = new ArrayList<>();
		this.factories = new ArrayList<>();
		for(IDatabase<?> d : db)
			try {
				addDatabase(d);
			} catch (DBExistsException e) {}
	}
	
	/*
	 * 
	 * loading des bases de données
	 * 
	 */
	
	@Override
	public void load() {
		for(IDatabase<?> db : databases) {
			try {
				plugin.log("Loading " + db.getName());
				db.connect();
				db.load();
			} catch (ConnectionException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/*
	 * 
	 * Sauvgarde de la population des bases de données et la base de données
	 * 
	 */
	
	@Override
	public void save() {
		for(IDataFactory<?, ?> factory : factories) {
			factory.saveAll();
			plugin.log("T'entative de sauvgarde de la factory " + ChatColor.GREEN + factory.getClass().getSimpleName());
		}
		for(IDatabase<?> db : databases) {
			try {
				plugin.log("Saving " + db.getName());
				db.save();
				db.disconect();
			} catch (NotConnectedException e) {
				plugin.logErr(e.getMessage());
			}
		}
	}
	
	/*
	 * 
	 * gestion des bases de données
	 * 
	 */
	
	@Override
	public void addDatabase(IDatabase<?> db) throws DBExistsException {
		try {
			getDatabase(db.getName());
			throw new DBExistsException();
		}catch(DBNotFoundException e) {
			databases.add(db);
		}
	}
	
	@Override
	public List<IDatabase<?>> getDatabases() {
		return this.databases;
	}

	@Override
	public IDatabase<?> getDatabase(String name) throws DBNotFoundException {
		for(IDatabase<?> db : databases) {
			if(db.getName().equals(name)) return db;
		}
		throw new DBNotFoundException(name);
	}
	
	/*
	 * 
	 * Gestion de la base de données par default (utilisée par les population si pas passez en arguments)
	 * 
	 */

	@Override
	public IDatabase<?> getDefaultDatabase() {
		return this.defaultDb;
	}
	
	@Override
	public void setDefaultDatabase(String name) throws DBNotFoundException {
		defaultDb = getDatabase(name);
	}
	
	/*
	 * 
	 * Gestion des populations
	 * 
	 */
	
	@Override
	public void addDataFactory(IDataFactory<?, ?> dataFactory) {
		dataFactory.loadAll();
		this.factories.add(dataFactory);
	}
	
	@Override
	public List<IDataFactory<?, ?>> getDataFactories() {
		return this.factories;
	}
	
	@Override
	public void removeDataFactory(IDataFactory<?, ?> dataFactory) {
		this.factories.remove(dataFactory);
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
