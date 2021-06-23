package fr.acore.spigot.api.storage.exception.schema;

public class SchemaNotFounException extends Exception {


	private static final long serialVersionUID = 7732723017204510839L;

	private String schemaName;
	
	public SchemaNotFounException(String schemaName) {
		this.schemaName = schemaName;
	}
	
	@Override
	public String getMessage() {
		return "Le schema " + schemaName + " n'existe pas";
	}
	
}
