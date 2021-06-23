package fr.acore.spigot.api.storage.factory;

import java.util.List;

import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.storage.table.ITable;
import fr.acore.spigot.storage.constraint.QueryConstraint;

public interface IDataFactory<T, U extends IManager> {

	public ITable getTable();
	public U getManager();
	
	public void loadAll();
	public void saveAll();
	
	public T load(String contraint, Object... datas);
	public void save(T obj);
	
	public boolean contain(String constraint, Object... datas);
	
	public void insert(T data);
	public void delete(T data);
	public void update(T data);
	public List<T> select(QueryConstraint queryConstraint);
	public T selectFirst(QueryConstraint queryConstraint);
	
}
