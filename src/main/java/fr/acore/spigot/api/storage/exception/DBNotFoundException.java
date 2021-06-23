package fr.acore.spigot.api.storage.exception;

public class DBNotFoundException extends Exception {

	private static final long serialVersionUID = 3730156947908094707L;

	private String name;
	
	public DBNotFoundException(String name) {
		this.name = name;
	}
	
	@Override
	public String getMessage() {
		return "La base de donnée : " + name + " n'existe pas";
	}

}
