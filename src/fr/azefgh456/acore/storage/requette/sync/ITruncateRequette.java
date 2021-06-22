package fr.azefgh456.acore.storage.requette.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.json.utils.CoreJsonObject;
import fr.azefgh456.acore.storage.requette.utils.ExecuteType;

public class ITruncateRequette extends IRequette{

	public ITruncateRequette(String tableName) {
		super(tableName, null);
	}

	@Override
	public ExecuteType getType() {
		return ExecuteType.EXECUTE_UPDATE;
	}

	@Override
	public PreparedStatement buildForSql(Connection connection, IPlugin plugin) throws SQLException {
		return connection.prepareStatement("TRUNCATE TABLE " + getTableName());
	}

	@Override
	public CoreJsonObject buildForJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
