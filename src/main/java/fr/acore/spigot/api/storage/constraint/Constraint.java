package fr.acore.spigot.api.storage.constraint;

import java.util.List;

public interface Constraint<T extends ConstraintType> {
	
	public T getConstraintType();
	
	public List<Object> getDatas();
	
	public String toSql();
	
}
