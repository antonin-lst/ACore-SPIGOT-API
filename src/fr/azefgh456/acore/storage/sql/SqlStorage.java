package fr.azefgh456.acore.storage.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.storage.requette.sync.IRequette;
import fr.azefgh456.acore.storage.sql.manager.SqlManager;
import fr.azefgh456.acore.storage.utils.AsyncData;
import fr.azefgh456.acore.storage.utils.Data;
import fr.azefgh456.acore.storage.utils.DataBuilder;
import fr.azefgh456.acore.storage.utils.IDataManager;
import fr.azefgh456.acore.storage.utils.IStorage;
import fr.azefgh456.acore.storage.utils.StorageType;
import net.md_5.bungee.api.ChatColor;

public class SqlStorage implements IStorage{
	
	private SqlManager sqlManager;
	public IDataManager getManager() { return sqlManager;}
	ExecutorService executor;
	
	public SqlStorage(ACore plugin) {
		this.sqlManager = new SqlManager(plugin);
		sqlManager.connection();
		executor = Executors.newFixedThreadPool(2);
	}
	
	/*
	 * 
	 * Load Data
	 * 
	 */
	
	@Override
	public void loadSimpleData(Data<?> data) {
		//Create Table
		executeSimpleRequette(data.createTable());
		//Load some data
		data.load();
	}
	
	@Override
	public void loadSimpleAsyncData(Data<?> data) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * 
	 * Save Data
	 * 
	 */
	
	@Override
	public void save(Data<?> data) {
		data.save();
	}
	
	@Override
	public void saveAsync(Data<?> data) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void saveAll(List<Data<?>> datas) {
		if(datas.isEmpty()) return;
		
		sqlManager.getPlugin().log("Commencement de la sauvegarde complette");
		
		for(Data<?> data : datas) {
			data.save();
			sqlManager.getPlugin().log(data.getClass().getSimpleName() + " saved");
		}
		
	}
	
	@Override
	public void saveAllAsync(List<Data<?>> datas) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void disabled() {
		executor.shutdown();
		sqlManager.disconnect();
	}
	
	@Override
	public void executeSimpleRequette(IRequette requette) {
		if(requette == null) return;

		try {
			PreparedStatement query = requette.buildForSql(sqlManager.getConnection(), sqlManager.getPlugin());
			executeQuery(requette, query);
			query.close();
		}catch(NullPointerException | SQLException e) {
			e.printStackTrace();
			sqlManager.getPlugin().logErr("Une erreur c'est produit pendant l'envoi de la requette " + ChatColor.AQUA + requette.getTableName() + " " + requette.getType().toString());
		}
		
	}
	
	
	
	@Override
	public boolean executeBooleanRequette(IRequette requette) {
		
		if(requette == null) return false;
		
		try {
			PreparedStatement query = requette.buildForSql(sqlManager.getConnection(), sqlManager.getPlugin());
			ResultSet result = executeQuery(requette, query);
			boolean hasAccount = result.next();
			query.close();
			return hasAccount;
		}catch(NullPointerException | SQLException e) {
			e.printStackTrace();
			sqlManager.getPlugin().logErr("Une erreur c'est produit pendant l'envoi de la requette " + ChatColor.AQUA + requette.getTableName() + " " + requette.getType().toString());
		}
		return false;
	}
	
	@Override
	public DataBuilder executeCustomTypeRequette(IRequette requette) {
		if(requette == null) return null;
		
		try {
			PreparedStatement query = requette.buildForSql(sqlManager.getConnection(), sqlManager.getPlugin());
			ResultSet result = executeQuery(requette, query);
			DataBuilder data = new DataBuilder(result, requette.getArgs() != null ? requette.getArgs().toArray(new String[0]) : null);
			query.close();
			return data;
				
		}catch(NullPointerException | SQLException e) {
			e.printStackTrace();
			sqlManager.getPlugin().logErr("Une erreur c'est produit pendant l'envoi de la requette " + ChatColor.AQUA + requette.getTableName() + " " + requette.getType().toString());
		}
		return null;
	}
	
	private ResultSet executeQuery(IRequette requette, PreparedStatement query) throws SQLException{
		switch (requette.getType()) {
		case EXECUTE:
			query.execute();
			break;
		case EXECUTE_UPDATE:
			query.executeUpdate();
			break;
		case EXECUTE_QUERY:
			return query.executeQuery();
		}
		return null;
	}

	@Override
	public void executeSimpleAsyncRequette(AsyncData<?> asyncData) {
		executor.execute(asyncData);
	}

	@Override
	public StorageType getStorageType() {
		return StorageType.SQL;
	}

	public Connection getConnection() {
		return sqlManager.getConnection();
	}
}
