package fr.acore.spigot.api.storage.database;

public class DBUser {
	
	private String pseudo;
	private String password;
	
	public DBUser(String pseudo, String password) {
		this.pseudo = pseudo;
		this.password = password;
	}
	
	public String getPseudo() {
		return pseudo;
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public String toString() {
		return "pseudo=" + pseudo + " password=" + password;
	}

}
