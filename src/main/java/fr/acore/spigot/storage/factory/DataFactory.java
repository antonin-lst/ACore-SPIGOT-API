package fr.acore.spigot.storage.factory;

import java.util.List;

import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.storage.constraint.query.QueryConstraintType;
import fr.acore.spigot.api.storage.factory.IDataFactory;
import fr.acore.spigot.api.storage.schema.ISchema;
import fr.acore.spigot.api.storage.table.ITable;
import fr.acore.spigot.storage.DBHelper;
import fr.acore.spigot.storage.StorageManager;
import fr.acore.spigot.storage.constraint.QueryConstraint;

public abstract class DataFactory<T, U extends IManager> implements IDataFactory<T, U> {

	/*
	 * 
	 * Table Clazz
	 * 
	 */
	private Class<T> clazz;
	
	/*
	 * 
	 * instance du IManager au quelle apartient la DataFactory
	 * 
	 */
	
	protected U manager;
	
	/*
	 * 
	 * Instance de la table cible
	 * 
	 */
	
	protected ITable table;
	
	/*
	 * 
	 * Constructeur utilisent le schema par default de la base de donnée par default
	 * 
	 */
	
	public DataFactory(Class<T> clazz, U manager) {
		this.clazz = clazz;
		this.manager = manager;
		String tableName = DBHelper.getRealClassName(clazz);
		ISchema<?> defaultSchema = manager.getPlugin().getManager(StorageManager.class).getDefaultDatabase().getDefaultSchema();

		defaultSchema.createTable(clazz);
		table = defaultSchema.getTable(tableName);
	}
	
	/*
	 * 
	 * Utilisation d'un schema custom
	 * 
	 */
	
	public DataFactory(Class<T> clazz, U manager, ISchema<?> target) {
		this.clazz = clazz;
		this.manager = manager;
		String tableName = DBHelper.getRealClassName(clazz);
		target.createTable(clazz);
		table = target.getTable(tableName);
	}
	
	/*
	 * 
	 * Gestion de la population
	 * 
	 */
	
	@Override
	public T load(String constraint, Object... datas) {
		return selectFirst(new QueryConstraint(QueryConstraintType.WHERE, constraint, datas));
	}
	
	@Override
	public boolean contain(String contraint, Object... datas) {
		try {
			List<T> test = select(new QueryConstraint(QueryConstraintType.WHERE, contraint, datas));
			//System.out.println(test.size());
			//System.out.println(test.get(0));
			return !select(new QueryConstraint(QueryConstraintType.WHERE, contraint, datas)).isEmpty();
		}catch(Exception e) {
			return false;
		}
	}
	
	/*
	 * 
	 * Interaction avec la table
	 * 
	 */
	
	@Override
	public void insert(T data) {
		table.insert(data);
	}
	
	@Override
	public List<T> select(QueryConstraint queryConstraint) {
		return table.select(clazz, queryConstraint);
	}
	
	@Override
	public T selectFirst(QueryConstraint queryConstraint) {
		return select(queryConstraint).get(0);
	}
	
	
	@Override
	public void delete(T data) {
		table.delete(data);
	}

	@Override
	public void update(T data) {
		table.update(data);
	}

	/*
	 * 
	 * Getter du manager et de la table heritée de IDataFactory
	 * 
	 */
	
	@Override
	public U getManager() {
		return this.manager;
	}
	
	@Override
	public ITable getTable() {
		return this.table;
	}
	
	
}
