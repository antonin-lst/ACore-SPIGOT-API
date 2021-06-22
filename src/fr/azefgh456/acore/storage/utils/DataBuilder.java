package fr.azefgh456.acore.storage.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.Inventory;

public class DataBuilder {
	
	private ResultSet result;
	private String[] keys;
	
	private List<Map<String, Object>> datas;
	public List<Map<String, Object>> getDatas(){ return this.datas;}
	
	public DataBuilder(ResultSet result, String... keys) {
		this.result = result;
		this.keys = keys;
		datas = new ArrayList<>();
		if(keys != null) {
			try {
				build();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			try {
				result.next();
				Map<String, Object> d = new HashMap<>();
				d.put("count", result.getInt(1));
				datas.add(d);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void build() throws SQLException {
		
		while(result.next()) {
			
			Map<String, Object> data = new HashMap<>();
			
			for(String key : keys) {
				data.put(key, result.getObject(key));
			}
			
			datas.add(data);
			
		}
	}

	public int getListSize() {
		return datas.size();
	}
	
	public Object getObject(int indexList, String key) {
		return datas.get(indexList).get(key);
	}
	
	public String getString(int indexList, String key) {
		return (String) getObject(indexList, key);
	}
	
	public char getChar(int indexList, String key) {
		return (char) getObject(indexList, key);
	}
	
	public int getInt(int indexList, String key) {
		return (int) getObject(indexList, key);
	}
	
	public double getDouble(int indexList, String key) {
		return (double) getObject(indexList, key);
	}
	
	public long getLong(int indexList, String key) {
		return (long) getObject(indexList, key);
	}
	
	public boolean getBoolean(int indexList, String key) {
		return (boolean) getObject(indexList, key);
	}
	
	public Inventory getInventory(int indexList, String key) {
		return null;
	}
	
}
