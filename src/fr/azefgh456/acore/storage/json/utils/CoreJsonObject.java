package fr.azefgh456.acore.storage.json.utils;

public class CoreJsonObject {
	
	private JsonRequetteType type;
	private String jsonMessage;
	
	public CoreJsonObject(JsonRequetteType type, String jsonMessage) {
		this.type = type;
		this.jsonMessage = jsonMessage;
	}
	
	public JsonRequetteType getType() {
		return type;
	}
	
	public String getJsonMessage() {
		return this.jsonMessage;
	}
	
	public static enum JsonRequetteType{
		
		SELECT, UPDATE, CONTAIN, INSERT, DELETE, DROP;
		
	}

}
