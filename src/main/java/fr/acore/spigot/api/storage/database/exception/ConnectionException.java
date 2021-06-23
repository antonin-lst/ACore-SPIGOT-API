package fr.acore.spigot.api.storage.database.exception;

public class ConnectionException extends Exception {

	private static final long serialVersionUID = 5316061465651558200L;
	
	private String dbName;
	
	public ConnectionException(String dbName) {
		this.dbName = dbName;
	}
	
	@Override
	public String getMessage() {
		return "Connection Impossible a la db " + dbName;
	}
	
}
