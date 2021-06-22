package fr.azefgh456.acore.storage.requette.sync;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.json.utils.CoreJsonObject;
import fr.azefgh456.acore.storage.requette.utils.ExecuteType;

public abstract class IRequette {
	
	private String tableName;
	private List<String> args;
	
	public IRequette(String tableName, List<String> args) {
		this.tableName = tableName;
		this.args = args;
	}

	public String getTableName() {
		return this.tableName;
	}
	
	public List<String> getArgs(){
		return this.args;
	}
	
	public boolean isLast(List<String> list, String str) {
		return list.indexOf(str) == list.size()-1;
	}
	
	public String splitArgVirgule(List<String> args) {
		StringBuilder builder = new StringBuilder("");
		for(String arg : args) {
			builder.append(arg);
			if(!(args.indexOf(arg) == args.size()-1)) {
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	public void setTypedArgs(PreparedStatement q, Object o, int pos) throws SQLException {
		if(o.getClass().equals(Integer.class)) {
			q.setInt(pos, (int) o);
		}else if(o.getClass().equals(String.class)) {
			q.setString(pos, (String) o);
		}else if(o.getClass().equals(Double.class)) {
			q.setDouble(pos, (double) o);
		}else if(o.getClass().equals(Long.class)) {
			q.setLong(pos, (Long) o);
		}
	}
	
	public PreparedStatement setArgs(PreparedStatement prepareStatement, List<Object> values) {
		
		if(values == null || values.isEmpty()) return prepareStatement;
		
		int i = 1;
		for(Object obj : values) {
			try {
				setTypedArgs(prepareStatement, obj, i);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			i++;
		}
		
		return prepareStatement;
	}
	
	public abstract ExecuteType getType();

	public abstract PreparedStatement buildForSql(Connection connection, IPlugin plugin) throws SQLException;
	public abstract CoreJsonObject buildForJson();
	
}
