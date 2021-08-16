package fr.acore.spigot.storage;

import java.lang.reflect.Field;

import fr.acore.spigot.api.storage.column.Column;
import fr.acore.spigot.api.storage.column.IColumn;
import fr.acore.spigot.api.storage.column.foreign.ManyToOne;
import fr.acore.spigot.api.storage.column.type.ColumnType;
import fr.acore.spigot.api.storage.table.ITable;
import fr.acore.spigot.api.storage.table.Table;
import fr.acore.spigot.api.storage.utils.CustomSize;

public class DBHelper {
	
	public static String getRealColumnName(Field columnField) {
		return columnField.getDeclaredAnnotation(Column.class) != null ? columnField.getDeclaredAnnotation(Column.class).columnName() : columnField.getName();
	}
	
	public static String getRealClassName(Class<?> clazz) {
		return clazz.getDeclaredAnnotation(Table.class) != null	? clazz.getDeclaredAnnotation(Table.class).name() : clazz.getSimpleName();
	}
	
}
