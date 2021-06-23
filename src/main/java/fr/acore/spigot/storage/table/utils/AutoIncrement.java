package fr.acore.spigot.storage.table.utils;

import java.lang.reflect.Field;

public class AutoIncrement {
	
	private Field targetField;
	private Object incrementObject;
	private int incrementIndex;

	public AutoIncrement(Field targetField, Object incrementObject, int incrementIndex) {
		this.targetField = targetField;
		this.incrementObject = incrementObject;
		this.incrementIndex = incrementIndex;
	}
	
	public Field getTargetField() {
		return targetField;
	}
	
	public Object getIncrementObject() {
		return incrementObject;
	}
	
	public int getIncrementIndex() {
		return incrementIndex;
	}

	public void injectIncrement() {
		try {
			targetField.setInt(incrementObject, ++incrementIndex);
		} catch (Exception  e) {
			e.printStackTrace();
		}
	}
	
}
