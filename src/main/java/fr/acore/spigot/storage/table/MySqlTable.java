package fr.acore.spigot.storage.table;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import fr.acore.spigot.api.storage.column.IColumn;
import fr.acore.spigot.api.storage.column.foreign.ManyToOne;
import fr.acore.spigot.api.storage.column.foreign.ModificationConstraintType;
import fr.acore.spigot.api.storage.column.foreign.OneToMany;
import fr.acore.spigot.api.storage.constraint.Constraint;
import fr.acore.spigot.api.storage.constraint.ConstraintType;
import fr.acore.spigot.api.storage.constraint.query.IQueryConstraint;
import fr.acore.spigot.api.storage.constraint.query.QueryConstraintType;
import fr.acore.spigot.api.storage.constraint.query.by.IByConstraint;
import fr.acore.spigot.api.storage.constraint.query.column.ColumnQueryConstraintType;
import fr.acore.spigot.api.storage.constraint.query.column.IColumnQueryConstraint;
import fr.acore.spigot.api.storage.schema.ISchema;
import fr.acore.spigot.api.storage.table.ITable;
import fr.acore.spigot.api.storage.table.OneToManyCollection;
import fr.acore.spigot.api.storage.table.Table;
import fr.acore.spigot.storage.DBHelper;
import fr.acore.spigot.storage.constraint.QueryConstraint;
import fr.acore.spigot.storage.constraint.query.QueryColumnConstraint;
import fr.acore.spigot.storage.table.utils.AutoIncrement;

public class MySqlTable implements ITable {

	private ISchema<?> schema;
	private String name;
	private List<IColumn> columns;
	
	private List<OneToManyCollection> oneToManyCollections;
	
	private Class<?> ressourceClass;
	
	public MySqlTable(ISchema<?> schema, String name) {
		this(schema, name, null);
	}
	
	public MySqlTable(ISchema<?> schema, String name, Class<?> clazz) {
		this.schema = schema;
		this.name = name;
		this.columns = new ArrayList<>();
		this.oneToManyCollections = new ArrayList<>();
		this.ressourceClass = clazz;
	}
	
	@Override
	public ISchema<?> getSchema() {
		return this.schema;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Class<?> getRessourceClass() {
		return this.ressourceClass;
	}

	@Override
	public boolean isInjected() {
		return ressourceClass != null;
	}

	@Override
	public void inject(Class<?> ressourceClazz) {
		//System.out.println("Injection de la class : " + ressourceClazz.getSimpleName());
		this.ressourceClass = ressourceClazz;
		for(IColumn c : columns) {
			for(Field f : ressourceClass.getDeclaredFields()) {
				if(DBHelper.getRealColumnName(f).equals(c.getName())) {
					//System.out.println("InjectionDebug " + f.getName() + " " + DBHelper.getRealColumnName(f) + " " + c.getName());
					c.injectField(f);
				}else if(f.getDeclaredAnnotation(ManyToOne.class) != null && (f.getDeclaredAnnotation(ManyToOne.class).columnName().equals(c.getName())) || f.getName().equals(c.getName())) {
					c.injectField(f);
				}
			}
		}
		for(Field field : ressourceClass.getDeclaredFields()) {
			if(field.getDeclaredAnnotation(OneToMany.class) != null) {
				addOneToManyCollection(new OneToManyCollection(field, field.getDeclaredAnnotation(OneToMany.class)));
			}
		}
	}

	@Override
	public void setColumn(List<IColumn> columns) {
		this.columns = columns;
	}
	
	@Override
	public void addColumn(IColumn column) {
		this.columns.add(column);
	}

	@Override
	public List<IColumn> getColumns() {
		return this.columns;
	}

	@Override
	public IColumn getColumn(String name) {
		for(IColumn column : columns) {
			if(column.getName().equals(name)) return column;
		}
		return null;
	}
	
	@Override
	public List<IColumn> getPrimaryKey() {
		List<IColumn> primaryKeys = new ArrayList<>();
		for(IColumn c : columns) if(c.isPrimary()) primaryKeys.add(c);
		return primaryKeys;
	}
	
	@Override
	public List<IColumn> getForeignKey() {
		List<IColumn> foreignKeys = new ArrayList<>();
		for(IColumn c : columns) if(c.isForeign()) {
			foreignKeys.add(c);
		}
		return foreignKeys;
	}
	
	@Override
	public List<OneToManyCollection> getOneToManyCollections() {
		return this.oneToManyCollections;
	}
	
	@Override
	public void addOneToManyCollection(OneToManyCollection oneToManyColletion) {
		this.oneToManyCollections.add(oneToManyColletion);
	}
	
	@Override
	public Connection getConnection() {
		return this.schema.getConnection();
	}
	
	@Override
	public <T> void insert(T obj) {
		StringBuilder request = new StringBuilder("INSERT INTO " + getSchema().getName() + "." + getName() + "(");
		StringBuilder values = new StringBuilder(" VALUES(");
		int i = 1;
		for(IColumn column : columns) {
			request.append(column.getName());
			values.append("?");
			if(i < columns.size()) {
				request.append(", ");
				values.append(", ");
			}
			i++;
		}
		request.append(")");
		values.append(");");
		request.append(values.toString());
		System.out.println(request.toString());
		try {
			PreparedStatement insert = getConnection().prepareStatement(request.toString());
			List<AutoIncrement> autoIncrement = new ArrayList<>();
			for(int j = 1; j < i; j++) {
				//System.out.println("DEBUG : " + j + " " + columns.get(j-1).getName());
				if(columns.get(j-1).isAutoIncrement()) {
					PreparedStatement lastIndexStatement = getConnection().prepareStatement("SELECT " + columns.get(j-1).getName() + " FROM " + getSchema().getName() + "." + getName() + " ORDER BY " + columns.get(j-1).getName() + " DESC");
					ResultSet result = lastIndexStatement.executeQuery();
					int lastIndex = result.next() ? result.getInt(columns.get(j-1).getName()) : 0;
					lastIndexStatement.close();
					autoIncrement.add(new AutoIncrement(columns.get(j-1).getInjectedField(), obj, lastIndex));
				}
				insert.setObject(j, columns.get(j-1).values(obj));
			}
			insert.execute();
			insert.close();
			for(AutoIncrement ai : autoIncrement) {
				ai.injectIncrement();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void truncate() {
		List<?> objs = select(ressourceClass, QueryConstraint.WHERE_ALL);
		
		for(Object obj : objs) {
			delete(obj);
		}
	}
	 
	
	@Override
	public <T> T selectFirst(Class<T> clazz, IQueryConstraint queryConstraint) {
		return selectFirst(clazz, queryConstraint, new ArrayList<>());
	}
	
	@Override
	public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint) {
		return selectFirst(clazz, columnConstraint, QueryConstraint.WHERE_ALL);
	}
	
	@Override
	public <T> T selectFirst(Class<T> clazz, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints) {
		return selectFirst(clazz, new QueryColumnConstraint(ColumnQueryConstraintType.SELECT), queryConstraint, byConstraints);
	}
	
	@Override
	public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint) {
		return selectFirst(clazz, columnConstraint, queryConstraint, new ArrayList<>());
	}	

	
	@Override
	public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint, List<IByConstraint> byConstraints) {
		return selectFirst(clazz, columnConstraint, QueryConstraint.WHERE_ALL, byConstraints);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint, IQueryConstraint constraint, List<IByConstraint> byConstraints) {
		List<T> objs = (List<T>) select(clazz, columnConstraint, constraint, byConstraints, null);
		
		return objs.isEmpty() ? null : objs.get(0);
	}
	
	@Override
	public <T> List<T> select(Class<T> clazz) {
		return select(clazz, new ArrayList<>());
	}
	
	@Override
	public List<?> select(Class<?> clazz, Object parent) {
		return select(clazz, new ArrayList<>(), parent);
	}
	
	@Override
	public <T> List<T> select(Class<T> clazz, List<IByConstraint> byConstraints) {
		return select(clazz, QueryConstraint.WHERE_ALL, byConstraints);
	}
	
	@Override
	public List<?> select(Class<?> clazz, List<IByConstraint> byConstraints, Object parent) {
		return select(clazz, QueryConstraint.WHERE_ALL, byConstraints, parent);
	}
	
	@Override
	public <T> List<T> select(Class<T> clazz, IQueryConstraint queryConstraint) {
		return select(clazz, queryConstraint, new ArrayList<>());
	}

	@Override
	public List<?> select(Class<?> clazz, IQueryConstraint queryConstraint, Object parent) {
		return select(clazz, queryConstraint, new ArrayList<>(), parent);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> select(Class<T> clazz, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints) {
		return (List<T>) select(clazz, new QueryColumnConstraint(ColumnQueryConstraintType.SELECT), queryConstraint, byConstraints, null);
	}
	
	@Override
	public List<?> select(Class<?> clazz, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints, Object parent) {
		return select(clazz, new QueryColumnConstraint(ColumnQueryConstraintType.SELECT), queryConstraint, byConstraints, parent);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> select(Class<T> clazz, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint) {
		return (List<T>) select(clazz, columnConstraint, queryConstraint, new ArrayList<>(), null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> select(Class<T> clazz, IColumnQueryConstraint columnConstraint, List<IByConstraint> byConstraints) {
		return (List<T>) select(clazz, columnConstraint, QueryConstraint.WHERE_ALL, byConstraints, null);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public List<?> select(Class<?> clazz, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint, List<IByConstraint> ordersConstraint, Object parent) {
		try {
			StringBuilder request = new StringBuilder("SELECT " + columnConstraint.toSql() + " FROM " + getSchema().getName() + "." + getName() + " " + queryConstraint.toSql());
			for(IByConstraint byConstraint : ordersConstraint) {
				request.append(" ").append(byConstraint.toSql());
			}
			request.append(";");
			System.out.println(request);
			PreparedStatement select = getConnection().prepareStatement(request.toString());
			
			int i = 1;
			for(Object o : queryConstraint.getDatas()) {
				if(o instanceof Integer) {
					//System.out.println("Is int");
					select.setInt(i, (Integer) o);
				}
				select.setObject(i, o);
				i++;
			}
			
			ResultSet result = select.executeQuery();
			//System.out.println(select.execute());
			List<Object> objs = new ArrayList<>();
			while(result.next()) {
				Object obj = clazz.newInstance();
				if(columnConstraint.getDatas().isEmpty() || (columnConstraint.getDatas().size() == 1 && String.valueOf(columnConstraint.getDatas().get(0)).equals("*"))){
					for(IColumn column : getColumns()) {
						//System.out.println(column.getName());
						Object  objResult = result.getObject(column.getName());
						if(objResult != null) {
							column.getInjectedField().setAccessible(true);
							if(column.isForeign()) {
								column.getInjectedField().set(obj, parent);
							}else {
								column.getInjectedField().set(obj, objResult);
							}

						}
					}
				}else {
					for(Object selectedColumn : columnConstraint.getDatas()) {
						Field f = getColumn(String.valueOf(selectedColumn)).getInjectedField();
						f.setAccessible(true);
						f.set(obj, result.getObject((String)selectedColumn));
					}
				}
				if(!getOneToManyCollections().isEmpty()) {
					
					for(OneToManyCollection oneToManyCollection : getOneToManyCollections()) {
						List<Object> listObj = new ArrayList<>();
						ParameterizedType pType = (ParameterizedType) oneToManyCollection.getCollectionField().getGenericType();
						Class<?> targetClass = (Class<?>) pType.getActualTypeArguments()[0];
						ITable targetTable = getSchema().getTable(targetClass.getDeclaredAnnotation(Table.class) != null ? targetClass.getDeclaredAnnotation(Table.class).name() : targetClass.getSimpleName());
						StringBuilder constraint = new StringBuilder("");
						List<Object> datas = new ArrayList<>();
						if(!targetTable.getForeignKey().isEmpty()) {
							int cpt = 1;
							for(IColumn foreignColumn : targetTable.getForeignKey()) {
								datas.add(getColumn(foreignColumn.getForeign().split("\\.")[1]).values(obj));
								constraint.append(foreignColumn.getName() + " = ?");
								if(cpt < targetTable.getForeignKey().size()) constraint.append(", ");
								cpt++;
							}
						}
						for(Object targetObject : targetTable.select(targetClass, new QueryConstraint(QueryConstraintType.WHERE, constraint.toString(), datas.toArray(new Object[0])), obj)) {
							listObj.add(targetObject);
						}
						oneToManyCollection.getCollectionField().setAccessible(true);
						oneToManyCollection.getCollectionField().set(obj, listObj);
					}
					
				}
				objs.add(obj);
			}
			select.close();
			
			
			return objs;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public <T> void update(T obj) {
		update(obj, new QueryColumnConstraint(ColumnQueryConstraintType.UPDATE));
	}
	
	@Override
	public <T> void update(T obj, IColumnQueryConstraint columnConstraint) {
		List<Object> datas = new ArrayList<>();
		StringBuilder builder = new StringBuilder("");
		
		int i = 1;
		for(IColumn primaryColumn : getPrimaryKey()) {
			datas.add(primaryColumn.values(obj));
			builder.append(primaryColumn.getName() + " = ?");
			if(i < getPrimaryKey().size()) builder.append(", ");
			i++;
		}
		
		update(obj, columnConstraint, new QueryConstraint(QueryConstraintType.WHERE, builder.toString(), datas.toArray(new Object[0])));
	}
	
	@Override
	public <T> void update(T obj, IQueryConstraint queryConstraint) {
		update(obj, new QueryColumnConstraint(ColumnQueryConstraintType.UPDATE), queryConstraint);
	}
	
	@Override
	public <T> void update(T obj, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint) {
		StringBuilder builder = new StringBuilder("UPDATE " + getSchema().getName() + "." + getName() + " SET ");
		List<Object> datas = new ArrayList<>();
		
		String columnConstraintValue = "";
		if(columnConstraint.getDatas().isEmpty() || (columnConstraint.getDatas().size() == 1 && String.valueOf(columnConstraint.getDatas().get(0)).equals("*"))) {
			StringBuilder columnConstraintBuilder = new StringBuilder("");
			int i = 1;
			for(IColumn column : columns) {
				if(column.isPrimary()) continue;
				
				if(column.isForeign()) {
					IColumn targetColumn = getSchema().getTable(column.getForeign().split("\\.")[0]).getColumn(column.getForeign().split("\\.")[1]);
					datas.add(targetColumn.values(column.values(obj)));
					columnConstraintBuilder.append(column.getName() + " = ?");
					if(i < columns.size() - getPrimaryKey().size()) columnConstraintBuilder.append(", ");
					i++;
				}else {
					datas.add(column.values(obj));
					columnConstraintBuilder.append(column.getName() + " = ?");
					if(i < columns.size() - getPrimaryKey().size()) columnConstraintBuilder.append(", ");
					i++;
				}
			}
			columnConstraintValue = columnConstraintBuilder.toString();
		}else {
			columnConstraintValue = columnConstraint.toSql();
			datas.addAll(columnConstraint.getDatas());
		}
		
		String queryConstraintValue = "";
		if(queryConstraint.getDatas().isEmpty()) {
			
		}else {
			queryConstraintValue = queryConstraint.toSql();
			datas.addAll(queryConstraint.getDatas());
		}
		
		builder.append(columnConstraintValue).append(" ").append(queryConstraintValue).append(";");
		
		System.out.println(builder.toString());
		try {
			PreparedStatement request = getConnection().prepareStatement(builder.toString());
			int i = 1;
			for(Object data : datas) {
				request.setObject(i, data);
				i++;
			}
			request.executeUpdate();
			request.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public <T> void delete(T obj) {
		StringBuilder constraint = new StringBuilder("");
		List<Object> datas = new ArrayList<>();
		if(!getPrimaryKey().isEmpty()) {
			int i = 1;
			for(IColumn primaryColumn : getPrimaryKey()) {
				datas.add(primaryColumn.values(obj));
				constraint.append(primaryColumn.getName() + " = ?");
				if(i < getPrimaryKey().size()) constraint.append(", ");
				i++;
			}
		}
		
		delete(obj, new QueryConstraint(QueryConstraintType.WHERE, constraint.toString(), datas.toArray(new Object[0])));
	}
	
	@Override
	public <T> void delete(T obj, Constraint<? extends ConstraintType> constraint) {
		try {
			if(!getForeignKey().isEmpty()) {
				PreparedStatement disableFC = getConnection().prepareStatement("SET GLOBAL FOREIGN_KEY_CHECKS=0;");
				disableFC.execute();
				disableFC.close();
			}
			delete(obj, constraint, oneToManyCollections.isEmpty() ? ModificationConstraintType.ALWAYS : ModificationConstraintType.CASCADE);
			if(!getForeignKey().isEmpty()) {
				PreparedStatement enableFC = getConnection().prepareStatement("SET GLOBAL FOREIGN_KEY_CHECKS=1;");
				enableFC.execute();
				enableFC.close();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public <T> void delete(T obj, Constraint<? extends ConstraintType> constraint, ModificationConstraintType deleteType) {
		StringBuilder builder = new StringBuilder("DELETE FROM " + getSchema().getName() + "." + getName() + " " + constraint.toSql());
		try {
			if(deleteType.equals(ModificationConstraintType.CASCADE)) {
				for(OneToManyCollection oneToMany : oneToManyCollections) {
					ParameterizedType pType = (ParameterizedType) oneToMany.getCollectionField().getGenericType();
					Class<?> targetClass = (Class<?>) pType.getActualTypeArguments()[0];
					ITable targetTable = getSchema().getTable(targetClass.getDeclaredAnnotation(Table.class) != null ? targetClass.getDeclaredAnnotation(Table.class).name() : targetClass.getSimpleName());
					oneToMany.getCollectionField().setAccessible(true);
					Collection<Object> targetObjects = (Collection<Object>) oneToMany.getCollectionField().get(obj);
					for(Object targetObj : targetObjects) {
						targetTable.delete(targetObj);
					}
				}
			}
			System.out.println(builder.toString());
			PreparedStatement delete = getConnection().prepareStatement(builder.toString());
			int i = 1;
			for(Object data : constraint.getDatas()) {
				delete.setObject(i, data);
				i++;
			}
			delete.execute();
			delete.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
