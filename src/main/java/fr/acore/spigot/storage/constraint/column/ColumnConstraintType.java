package fr.acore.spigot.storage.constraint.column;

import fr.acore.spigot.api.storage.constraint.ConstraintType;

public enum ColumnConstraintType implements ConstraintType{
	
	PRIMARY_KEY("PRIMARY KEY"),
	FOREIGN_KEY("FOREIGN KEY");
	
	private String sqlConstraint;
	
	private ColumnConstraintType(String sqlConstraint) {
		this.sqlConstraint = sqlConstraint;
	}

	public String getSqlType() {
		return sqlConstraint;
	}
	
}
