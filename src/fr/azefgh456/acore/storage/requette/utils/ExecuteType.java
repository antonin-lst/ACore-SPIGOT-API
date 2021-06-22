package fr.azefgh456.acore.storage.requette.utils;

public enum ExecuteType {

	EXECUTE("execute")
	, EXECUTE_QUERY("execute_query")
	, EXECUTE_UPDATE("execute_update");
	
	private String value;
	
	private ExecuteType(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	public ExecuteType fromString(String value) {
		for(ExecuteType type : values()) {
			if(type.toString().equals(value)) return type;
		}
		return null;
	}
}