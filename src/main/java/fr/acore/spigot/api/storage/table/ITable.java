package fr.acore.spigot.api.storage.table;

import java.sql.Connection;
import java.util.List;

import fr.acore.spigot.api.storage.column.IColumn;
import fr.acore.spigot.api.storage.column.foreign.ModificationConstraintType;
import fr.acore.spigot.api.storage.constraint.Constraint;
import fr.acore.spigot.api.storage.constraint.ConstraintType;
import fr.acore.spigot.api.storage.constraint.query.IQueryConstraint;
import fr.acore.spigot.api.storage.constraint.query.by.IByConstraint;
import fr.acore.spigot.api.storage.constraint.query.column.IColumnQueryConstraint;
import fr.acore.spigot.api.storage.schema.ISchema;

public interface ITable {
	
	public ISchema<?> getSchema();
	
	public String getName();
	
	public Class<?> getRessourceClass();
	public boolean isInjected();
	public void inject(Class<?> ressourceClazz);
	
	public void addColumn(IColumn column);
	public void setColumn(List<IColumn> columns);
	public List<IColumn> getColumns();
	public IColumn getColumn(String name);

	public List<IColumn> getPrimaryKey();
	public List<IColumn> getForeignKey();

	public List<OneToManyCollection> getOneToManyCollections();
	public void addOneToManyCollection(OneToManyCollection oneToManyColletion);
	
	public Connection getConnection();
	public <T> void insert(T obj);
	
	public <T> void delete(T obj);
	public <T> void delete(T obj, Constraint<? extends ConstraintType> constraint);
	public <T> void delete(T obj, Constraint<? extends ConstraintType> constraint, ModificationConstraintType deleteType);
	public void truncate();
	
	public <T> List<T> select(Class<T> clazz);
	public <T> List<T> select(Class<T> clazz, List<IByConstraint> byConstraints);
	public <T> List<T> select(Class<T> clazz, IColumnQueryConstraint columnConstraint, List<IByConstraint> byConstraints);
	public <T> List<T> select(Class<T> clazz, IQueryConstraint queryConstraint);
	public <T> List<T> select(Class<T> clazz, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints);
	public <T> List<T> select(Class<T> clazz,IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint);
	
	public List<?> select(Class<?> clazz, Object parent);
	public List<?> select(Class<?> clazz, List<IByConstraint> byConstraints, Object parent);
	public List<?> select(Class<?> clazz, IQueryConstraint queryConstraint, Object parent);
	public List<?> select(Class<?> clazz, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints, Object parent);
	public List<?> select(Class<?> clazz,IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints, Object parent);
	
	public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint);
	public <T> T selectFirst(Class<T> clazz, IQueryConstraint queryConstraint);
	public <T> T selectFirst(Class<T> clazz, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints);
	public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint, List<IByConstraint> byConstraints);
	public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint);
	public <T> T selectFirst(Class<T> clazz, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint, List<IByConstraint> byConstraints);
	
	public <T> void update(T obj);
	public <T> void update(T obj, IColumnQueryConstraint columnConstraint);
	public <T> void update(T obj, IQueryConstraint queryConstraint);
	public <T> void update(T obj, IColumnQueryConstraint columnConstraint, IQueryConstraint queryConstraint);

	public default String toSql(boolean checkExist) {
		
		StringBuilder builder = new StringBuilder("CREATE TABLE ");
		StringBuilder primaryKeyBuilder = new StringBuilder("");
		if(checkExist) builder.append("IF NOT EXISTS ");
		
		builder.append(getSchema().getName() + "." + getName() + "(");
		int index = 1;
		for(IColumn c : getColumns()) {
			builder.append(c.toSql());
			if(index < getColumns().size()) builder.append(", ");
			index++;
		}
		
		if(!getPrimaryKey().isEmpty()) {
			primaryKeyBuilder.append(", CONSTRAINT PK_Table PRIMARY KEY (");
			int i = 1;
			for(IColumn primaryColumn : getPrimaryKey()) {
				primaryKeyBuilder.append(primaryColumn.getName());
				if(i < getPrimaryKey().size()) primaryKeyBuilder.append(", ");
				i++;
			}
			primaryKeyBuilder.append(")");
			builder.append(primaryKeyBuilder);
		}
		//System.out.println("Debug Foreign : " + getForeignKey().size());
		if(!getForeignKey().isEmpty()) {
			for(IColumn foreignColumn : getForeignKey()) {
				String[] reference = foreignColumn.getForeign().split("\\.");
				StringBuilder foreignBuilder = new StringBuilder(", CONSTRAINT FK_Table FOREIGN KEY (" + foreignColumn.getName() + ")" + " REFERENCES " + reference[0] + "(" + reference[1] + ")");
				builder.append(foreignBuilder.toString());
			}
		}
		
		builder.append(") ENGINE = INNODB;");
		System.out.println(builder.toString());
		return builder.toString();
	}
	
}
