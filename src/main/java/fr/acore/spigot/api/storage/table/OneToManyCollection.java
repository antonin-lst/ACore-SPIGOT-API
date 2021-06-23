package fr.acore.spigot.api.storage.table;

import java.lang.reflect.Field;

import fr.acore.spigot.api.storage.column.foreign.OneToMany;

public class OneToManyCollection {
	
	private Field collectionField;
	private OneToMany oneToMany;
	
	public OneToManyCollection(Field collectionField, OneToMany oneToMany) {
		this.collectionField = collectionField;
		this.oneToMany = oneToMany;
	}
	
	public Field getCollectionField() {
		return collectionField;
	}
	
	public OneToMany getOneToMany() {
		return oneToMany;
	}

}
