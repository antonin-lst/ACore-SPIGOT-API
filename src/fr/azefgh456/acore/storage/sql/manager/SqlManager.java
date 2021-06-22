package fr.azefgh456.acore.storage.sql.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.config.utils.Conf;
import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.json.JsonStorage;
import fr.azefgh456.acore.storage.json.manager.JsonManager;
import fr.azefgh456.acore.storage.manager.StorageManager;
import fr.azefgh456.acore.storage.utils.IDataManager;
import net.md_5.bungee.api.ChatColor;

public class SqlManager implements IDataManager{
	
	private ACore plugin;
	public IPlugin getPlugin() { return plugin;}
	
	private Connection connection;
	public SqlManager(ACore plugin) {
		this.plugin = plugin;
	}


	public void connection(){
		if(!isConected()){
			try {
				connection = DriverManager.getConnection(Conf.getUrlBase() + Conf.getHost() + "/" + Conf.getDatabase() + "?autoReconnect=true", Conf.getUser(), Conf.getPass());
				plugin.log("Connection avec la bdd reussit");
			} catch (SQLException e) {
				e.printStackTrace();
				plugin.logErr(ChatColor.RED + "Connection BDD imposible");
				plugin.registerManager(new StorageManager<JsonStorage>(plugin, new JsonStorage(new JsonManager(plugin))));
			}
		}
	}
	public void disconnect(){
		if(isConected()){
			try {
				connection.close();
				plugin.log(ChatColor.GREEN + "BDD Deconected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean isConected(){
		return connection !=null;
	}
	
	public Connection getConnection(){
		if(!isConected()) connection();
		
		return this.connection;
	}

}
