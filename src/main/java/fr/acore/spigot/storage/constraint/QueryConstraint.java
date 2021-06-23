package fr.acore.spigot.storage.constraint;

import java.util.Arrays;
import java.util.List;

import fr.acore.spigot.api.storage.constraint.query.IQueryConstraint;
import fr.acore.spigot.api.storage.constraint.query.QueryConstraintType;

public class QueryConstraint implements IQueryConstraint{

	public static final QueryConstraint WHERE_ALL = new QueryConstraint(QueryConstraintType.WHERE, "1");
	
	private QueryConstraintType constrainType;
	
	private String constraint;
	
	public List<Object> datas;
	
	public QueryConstraint(QueryConstraintType constraintType, String constraint, Object... datas) {
		this.constrainType = constraintType;
		this.constraint = constraint;
		this.datas = Arrays.asList(datas);
	}
	
	@Override
	public String toSql() {
		StringBuilder sqlConstraint = new StringBuilder("");
		sqlConstraint.append(constrainType.name() + " ");
		switch (constrainType) {
		case WHERE:
			sqlConstraint.append(constraint);
			break;

		default:
			break;
		}
		return sqlConstraint.toString();
	}

	@Override
	public QueryConstraintType getConstraintType() {
		return this.constrainType;
	}
	
	public String getConstraint() {
		return constraint;
	}
	
	@Override
	public List<Object> getDatas() {
		return datas;
	}

}
