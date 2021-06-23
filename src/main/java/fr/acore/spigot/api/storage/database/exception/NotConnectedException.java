package fr.acore.spigot.api.storage.database.exception;

public class NotConnectedException extends Exception {

	private static final long serialVersionUID = 7860825410679648441L;
	
	private String dbName;
	
	public NotConnectedException(String name) {
		this.dbName = name;
	}
	
	@Override
	public String getMessage() {
		return "Impossible de se deconnecter de la db " + dbName;
	}

}
