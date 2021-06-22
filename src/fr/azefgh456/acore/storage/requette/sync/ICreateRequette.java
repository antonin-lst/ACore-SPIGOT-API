package fr.azefgh456.acore.storage.requette.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.json.utils.CoreJsonObject;
import fr.azefgh456.acore.storage.requette.utils.ExecuteType;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint;

public class ICreateRequette extends IRequette {

	private String content;
	private Contraint contraint;
	
	public ICreateRequette(String tableName, String content) {
		super(tableName, null);
		this.content = content;
	}
	
	public ICreateRequette(String tableName, String content, Contraint contraint) {
		super(tableName, null);
		this.content = content;
		this.contraint = contraint;
	}

	@Override
	public PreparedStatement buildForSql(Connection connection, IPlugin plugin) throws SQLException {
		StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS " + getTableName() + "(" + content);
		builder.append(contraint == null ? "" : contraint.toString()).append(")");
		plugin.log(builder.toString());
		return connection.prepareStatement(builder.toString());
	}

	@Override
	public CoreJsonObject buildForJson() {
		return null;
	}

	@Override
	public ExecuteType getType() {
		return ExecuteType.EXECUTE;
	}

}
