package fr.acore.spigot.api.storage.column;

import java.lang.reflect.Field;

import fr.acore.spigot.api.storage.column.type.ColumnType;
import fr.acore.spigot.api.storage.table.ITable;
import fr.acore.spigot.api.storage.utils.CustomSize;

public interface IColumn {
	
	public ITable getTable();
	
	public String getName();
	public ColumnType getType();
	public CustomSize getSize();

	public boolean isPrimary();
	public boolean isForeign();
	public String getForeign();
	public boolean isNullable();
	public String defaultValue();
	public boolean isUnique();
	public boolean isAutoIncrement();
	
	public Field getInjectedField();
	public void injectField(Field columnField);
	public boolean isInjected();
	
	public <T> Object values(T obj);
	
	public default String toSql() {
		StringBuilder tableToSql = new StringBuilder(getName());
		tableToSql.append(" ").append(getType().getSqlType()).append("(").append(getSize().toSql() + ")");

		if(!isNullable()) tableToSql.append(" NOT NULL");
		if(isAutoIncrement() && getType().equals(ColumnType.INTEGER) && isPrimary()) tableToSql.append(" AUTO_INCREMENT");
		return tableToSql.toString();
	}

}
