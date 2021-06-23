package fr.acore.spigot.api.storage.schema;

import java.sql.Connection;
import java.util.List;

import fr.acore.spigot.api.storage.database.IDatabase;
import fr.acore.spigot.api.storage.table.ITable;

public interface ISchema<T extends ITable> {
	
	public IDatabase<?> getDataBase();
	public Connection getConnection();
	
	public String getName();
	
	public void load();
	public void save();
	
	public List<T> getTables();
	public T getTable(String name);
	public void addTable(T table);
	
	public void createTable(Class<?> tableClazz);
	public void createTable(T table);
	public void drop(String tableName);
	public void drop();

	//public T replace(ITable<?> target, ITable<?> newest);
	
	
	
}
