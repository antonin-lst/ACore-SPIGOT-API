package fr.acore.spigot.storage.constraint;

import java.util.List;

import fr.acore.spigot.api.storage.constraint.Constraint;
import fr.acore.spigot.storage.constraint.column.ColumnConstraintType;

public class ColumnConstraint implements Constraint<ColumnConstraintType> {

	private ColumnConstraintType constraintType;
	
	public ColumnConstraint(ColumnConstraintType constraintType) {
		this.constraintType = constraintType;
	}
	
	@Override
	public String toSql() {
		return constraintType.getSqlType();
	}

	@Override
	public ColumnConstraintType getConstraintType() {
		return this.constraintType;
	}
	
	@Override
	public List<Object> getDatas() {
		// TODO Auto-generated method stub
		return null;
	}

}
