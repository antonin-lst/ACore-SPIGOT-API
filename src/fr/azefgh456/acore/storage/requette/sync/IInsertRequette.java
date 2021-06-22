package fr.azefgh456.acore.storage.requette.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.json.utils.CoreJsonObject;
import fr.azefgh456.acore.storage.requette.utils.ExecuteType;

public class IInsertRequette extends IRequette{

	private List<Object> values;
	
	public IInsertRequette(String tableName, List<String> colone, List<Object> values) {
		super(tableName, colone);
		this.values = values;
	}

	@Override
	public ExecuteType getType() {
		return ExecuteType.EXECUTE;
	}

	@Override
	public PreparedStatement buildForSql(Connection connection, IPlugin plugin) throws SQLException {
		StringBuilder builder = new StringBuilder("INSERT INTO " + getTableName() + " (");
		StringBuilder builderValues = new StringBuilder("VALUES(");
		for(String key : getArgs()) {
			if(!isLast(getArgs(), key)) {
				builderValues.append("?").append(", ");
				builder.append(key).append(", ");
			}else {
				builder.append(key).append(") ");
				builderValues.append("?").append(")");
			}
		}
		plugin.log(builder.toString() + " " + builderValues.toString());
		return setArgs(connection.prepareStatement(builder.toString() + " " + builderValues.toString()), values);
	}

	
	
	@Override
	public CoreJsonObject buildForJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
