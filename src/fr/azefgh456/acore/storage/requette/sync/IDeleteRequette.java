package fr.azefgh456.acore.storage.requette.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.json.utils.CoreJsonObject;
import fr.azefgh456.acore.storage.requette.utils.ExecuteType;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint;

public class IDeleteRequette extends IRequette{

	private Contraint condition;
	private List<Object> values;
	
	public IDeleteRequette(String tableName, Contraint condition, List<Object> values) {
		super(tableName, null);
		this.condition = condition;
		this.values = values;
	}

	@Override
	public ExecuteType getType() {
		return ExecuteType.EXECUTE_UPDATE;
	}

	@Override
	public PreparedStatement buildForSql(Connection connection, IPlugin plugin) throws SQLException {
		StringBuilder builder = new StringBuilder("DELETE FROM " + getTableName());
		
		if(condition != null) {
			builder.append(condition.toString());
		}
		
		plugin.log(builder.toString());
		return setArgs(connection.prepareStatement(builder.toString()), values);
	}

	@Override
	public CoreJsonObject buildForJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
