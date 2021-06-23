package fr.acore.spigot.api.storage.database;

import java.sql.Connection;
import java.util.List;

import fr.acore.spigot.api.storage.database.driver.DatabaseDriver;
import fr.acore.spigot.api.storage.database.exception.ConnectionException;
import fr.acore.spigot.api.storage.database.exception.NotConnectedException;
import fr.acore.spigot.api.storage.exception.schema.SchemaNotFounException;
import fr.acore.spigot.api.storage.schema.ISchema;

public interface IDatabase<T extends ISchema<?>> {
	
	public String getHost();
	public void setHost(String host);
	public int getPort();
	public void setPort(int port);
	public DBUser getDbUser();
	public void setDbUser(DBUser user);
	public void connect() throws ConnectionException;
	public void disconect() throws NotConnectedException;
	public boolean isConnected();
	public Connection getConnection();
	
	public void load();
	public void save();
	
	public DatabaseDriver getDriver();
	public String getName();
	
	public List<T> getSchemas();
	public T getSchema(String name) throws SchemaNotFounException;
	public void addSchema(T schema);
	public void addSchema(String name);
	
	public void setDefaultSchema(String name) throws SchemaNotFounException;
	public T getDefaultSchema();

}
