package fr.azefgh456.acore.storage.json;

import java.util.List;

import fr.azefgh456.acore.storage.json.manager.JsonManager;
import fr.azefgh456.acore.storage.requette.sync.IRequette;
import fr.azefgh456.acore.storage.utils.AsyncData;
import fr.azefgh456.acore.storage.utils.Data;
import fr.azefgh456.acore.storage.utils.DataBuilder;
import fr.azefgh456.acore.storage.utils.IDataManager;
import fr.azefgh456.acore.storage.utils.IStorage;
import fr.azefgh456.acore.storage.utils.StorageType;

public class JsonStorage implements IStorage{

	private JsonManager jsonManager;
	public IDataManager getManager() { return jsonManager;}
	
	public JsonStorage(JsonManager jsonManager) {
		this.jsonManager = jsonManager;
	}
	

	

	@Override
	public StorageType getStorageType() {
		return StorageType.JSON;
	}

	@Override
	public void disabled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveAll(List<Data<?>> datas) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void save(Data<?> data) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void executeSimpleRequette(IRequette requette) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean executeBooleanRequette(IRequette requette) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DataBuilder executeCustomTypeRequette(IRequette requette) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeSimpleAsyncRequette(AsyncData<?> requette) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSimpleData(Data<?> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadSimpleAsyncData(Data<?> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveAsync(Data<?> data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveAllAsync(List<Data<?>> datas) {
		// TODO Auto-generated method stub
		
	}

}
