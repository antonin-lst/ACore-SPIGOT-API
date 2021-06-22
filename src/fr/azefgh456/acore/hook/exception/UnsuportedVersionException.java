package fr.azefgh456.acore.hook.exception;

@SuppressWarnings("serial")
public class UnsuportedVersionException extends Exception {
	
	private String message;
	
	public UnsuportedVersionException(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
