package fr.acore.spigot.storage.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import fr.acore.spigot.api.storage.database.DBUser;
import fr.acore.spigot.api.storage.database.IDatabase;
import fr.acore.spigot.api.storage.database.driver.DatabaseDriver;
import fr.acore.spigot.api.storage.database.exception.ConnectionException;
import fr.acore.spigot.api.storage.database.exception.NotConnectedException;
import fr.acore.spigot.api.storage.exception.schema.SchemaNotFounException;
import fr.acore.spigot.storage.schema.MySqlSchema;

public class MySqlDatabase implements IDatabase<MySqlSchema>{

	
	private String dbName;
	private DatabaseDriver driver;
	private DBUser user;
	private String host;
	private int port;
	private boolean isConnected;
	private List<MySqlSchema> schemas;
	private MySqlSchema defaultSchema;
	private Connection connection;

	public MySqlDatabase(String dbName, DBUser user) {
		this(dbName, user, "127.0.0.1");
	}
	
	public MySqlDatabase(String dbName, DBUser user, String host) {
		this(dbName, user, host, 3306);
	}
	
	public MySqlDatabase(String dbName, DBUser user, String host, int port) {
		this.dbName = dbName;
		this.driver = DatabaseDriver.MYSQL;
		this.user = user;
		this.host = host;
		this.port = port;
		this.isConnected = false;
		this.schemas = new ArrayList<>();
	}
	
	@Override
	public void load() {
		System.out.println("Nothing to do in mysqlLoad");
	}

	@Override
	public void save() {
		
	}
	
	@Override
	public void connect() throws ConnectionException {
		try {
			connection = DriverManager.getConnection(driver.getDriver() + host + ":" + port, user.getPseudo(), user.getPassword());
			isConnected = true;
		} catch (Exception e) {
			throw new ConnectionException(dbName);
		}
	}

	@Override
	public void disconect() throws NotConnectedException {
		try {
			connection.close();
		}catch(Exception e) {
			throw new NotConnectedException(dbName);
		}
	}
	
	@Override
	public String getName() {
		return this.dbName;
	}
	
	@Override
	public DatabaseDriver getDriver() {
		return this.driver;
	}
	
	@Override
	public DBUser getDbUser() {
		return this.user;
	}
	
	@Override
	public void setDbUser(DBUser user) {
		this.user = user;
	}
	
	@Override
	public String getHost() {
		return this.host;
	}

	@Override
	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public int getPort() {
		return this.port;
	}

	@Override
	public void setPort(int port) {
		this.port = port;
	}
	

	@Override
	public boolean isConnected() {
		return this.isConnected;
	}

	@Override
	public Connection getConnection() {
		return this.connection;
	}


	@Override
	public List<MySqlSchema> getSchemas() {
		return this.schemas;
	}

	@Override
	public MySqlSchema getSchema(String name) throws SchemaNotFounException {
		for(MySqlSchema sch : schemas) {
			if(sch.getName().equals(name)) return sch;
		}
		throw new SchemaNotFounException(name);
	}

	@Override
	public void addSchema(String name) {
		addSchema(new MySqlSchema(this, name));
	}
	
	@Override
	public void addSchema(MySqlSchema schema) {
		schema.load();
		this.schemas.add(schema);
	}


	public MySqlSchema getDefaultSchema() {
		return this.defaultSchema;
	}
	
	@Override
	public void setDefaultSchema(String name) throws SchemaNotFounException {
		this.defaultSchema = getSchema(name);
	}

}