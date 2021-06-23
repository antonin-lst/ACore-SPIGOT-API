package fr.acore.spigot.api.storage.constraint.query;

import fr.acore.spigot.api.storage.constraint.ConstraintType;

public enum QueryConstraintType implements ConstraintType{
	
	WHERE,
	LIKE;

	@Override
	public String getSqlType() {
		return name();
	}

}
