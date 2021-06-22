package fr.azefgh456.acore.storage.requette.async;

import java.util.List;

import fr.azefgh456.acore.storage.requette.sync.IInsertRequette;

public abstract class IInsertRequetteThread<T> extends IInsertRequette implements IRequetteThread<T>{

	public IInsertRequetteThread(String tableName, List<String> colone, List<Object> values) {
		super(tableName, colone, values);
	}

	@Override
	public void loadAsync(T data) {
		
	}



}
