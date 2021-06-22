package fr.azefgh456.acore.storage.requette.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.json.utils.CoreJsonObject;
import fr.azefgh456.acore.storage.requette.utils.ExecuteType;

public class ICountRequette extends IRequette{

	public ICountRequette(String tableName) {
		super(tableName, null);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ExecuteType getType() {
		return ExecuteType.EXECUTE_QUERY;
	}

	@Override
	public PreparedStatement buildForSql(Connection connection, IPlugin plugin) throws SQLException {
		return connection.prepareStatement("SELECT COUNT(*) FROM " + getTableName());
	}

	@Override
	public CoreJsonObject buildForJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
