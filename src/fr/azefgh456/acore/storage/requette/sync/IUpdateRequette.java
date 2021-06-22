package fr.azefgh456.acore.storage.requette.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.json.utils.CoreJsonObject;
import fr.azefgh456.acore.storage.requette.utils.ExecuteType;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint;

public class IUpdateRequette extends IRequette{

	private List<Object> values;
	
	private Contraint constraint;

	public IUpdateRequette(String tableName, List<String> args, List<Object> values) {
		super(tableName, args);
		this.values = values;
	}
	
	public IUpdateRequette(String tableName, List<String> args, Contraint constraint, List<Object> values) {
		super(tableName, args);
		this.values = values;
		this.constraint = constraint;
	}

	@Override
	public ExecuteType getType() {
		return ExecuteType.EXECUTE_UPDATE;
	}

	@Override
	public PreparedStatement buildForSql(Connection connection, IPlugin plugin) throws SQLException {
		StringBuilder requette = new StringBuilder("UPDATE " + getTableName());
		
		requette.append(" SET ");
	
	for(String arg : getArgs()) {
		requette.append(arg).append(" = ?");
		if(getArgs().indexOf(arg) != getArgs().size()-1) {
			requette.append(", ");
		}
	}
	
	if(constraint != null) {
		requette.append(constraint.toString());
	}
	
	plugin.log(requette.toString());
	
	return setArgs(connection.prepareStatement(requette.toString()), values);
	
	}

	@Override
	public CoreJsonObject buildForJson() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
