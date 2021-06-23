package fr.acore.spigot.api.storage.column.type;

import java.sql.Date;

import fr.acore.spigot.api.storage.utils.CustomSize;

public enum ColumnType {
	
	STRING(String.class, new CustomSize(100), "VARCHAR"),
	INTEGER(Integer.class, new CustomSize(11), "INT"),
	LONG(Long.class, new CustomSize(150), "BIGINT"),
	DOUBLE(Double.class, new CustomSize(2, 2), "DOUBLE"),
	FLOAT(Float.class, new CustomSize(2, 2), "FLOAT"),
	BOOLEAN(Boolean.class, new CustomSize(1), "TINYINT"),
	DATE(Date.class, null, "DATE");
	
	private Class<?> javaClazz;
	private CustomSize defaultSize;
	private String sqlType;
	
	ColumnType(Class<?> javaClazz, CustomSize defaultSize, String sqlType) {
		this.javaClazz = javaClazz;
		this.defaultSize = defaultSize;
		this.sqlType = sqlType;
	}
	
	public CustomSize getDefaultSize() {
		return defaultSize;
	}
	
	public Class<?> getJavaClazz() {
		return javaClazz;
	}
	
	public String getSqlType() {
		return sqlType;
	}

	public static ColumnType fromSqlType(String cType) {
		for(ColumnType target : ColumnType.values()) {
			if(target.getSqlType().equals(cType.split("\\(")[0].toUpperCase())) return target;
		}
		return ColumnType.STRING;
	}

}
