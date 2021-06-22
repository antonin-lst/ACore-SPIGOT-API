package fr.azefgh456.acore.storage.requette.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.json.utils.CoreJsonObject;
import fr.azefgh456.acore.storage.requette.utils.ExecuteType;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint;

public class ISelectRequette extends IRequette{

	private Contraint contraint;
	private List<Object> values;
	
	public ISelectRequette(String tableName, List<String> args, Contraint contraint, Object... val) {
		super(tableName, args);
		this.contraint = contraint;
		this.values = new ArrayList<>();
		for(Object o : val) values.add(o);
	}
	
	public ISelectRequette(String tableName, List<String> args) {
		super(tableName, args);
	}

	@Override
	public ExecuteType getType() {
		return ExecuteType.EXECUTE_QUERY;
	}

	@Override
	public PreparedStatement buildForSql(Connection connection, IPlugin plugin) throws SQLException {

		StringBuilder requette = new StringBuilder("SELECT " + splitArgVirgule(getArgs()) + " FROM " + getTableName());
		
		if(contraint != null) {
			requette.append(contraint.toString());
		}
		
		plugin.log(requette.toString());
		PreparedStatement statement = setArgs(connection.prepareStatement(requette.toString()), values);
		return statement;
		
	}

	@Override
	public CoreJsonObject buildForJson() {
		// TODO Auto-generated method stub
		return null;
	}

}
