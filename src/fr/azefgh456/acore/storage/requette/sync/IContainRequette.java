package fr.azefgh456.acore.storage.requette.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.json.utils.CoreJsonObject;
import fr.azefgh456.acore.storage.requette.utils.ExecuteType;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint.ConstraintValues;

public class IContainRequette extends IRequette{

	private List<Object> values;
	
	public IContainRequette(String tableName, List<String> pointer, List<Object> values) {
		super(tableName, pointer);
		this.values = values;
	}

	@Override
	public ExecuteType getType() {
		return ExecuteType.EXECUTE_QUERY;
	}

	@Override
	public PreparedStatement buildForSql(Connection connection, IPlugin plugin) throws SQLException {
		StringBuilder builder = new StringBuilder("SELECT " + splitArgVirgule(getArgs()) + " FROM " + getTableName() + new Contraint(ConstraintValues.WHERE, getValuesToString()).toString());
		plugin.log(builder.toString());
		PreparedStatement statement = setArgs(connection.prepareStatement(builder.toString()), values);
		return statement;
	}
	
	private String getValuesToString() {
		StringBuilder str = new StringBuilder("");
		
		for(String arg : getArgs()) {
			str.append(arg).append(" = ? ");
			if(getArgs().indexOf(arg) != getArgs().size()-1) {
				str.append("AND ");
			}
		}
		return str.toString();
	}
	
	@Override
	public CoreJsonObject buildForJson() {
		return null;
	}
	
	

}
