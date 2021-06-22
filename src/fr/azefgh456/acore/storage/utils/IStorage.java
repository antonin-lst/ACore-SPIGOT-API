package fr.azefgh456.acore.storage.utils;

import java.util.List;

import fr.azefgh456.acore.storage.requette.sync.IRequette;

public interface IStorage {
		
	public void executeSimpleRequette(IRequette requette);
	public boolean executeBooleanRequette(IRequette requette);
	public DataBuilder executeCustomTypeRequette(IRequette requette);
//	public void executeSimpleAsyncRequette(IRequetteThread<?> requette);	
	
	public void loadSimpleData(Data<?> data);
	public void loadSimpleAsyncData(Data<?> data);
	public void save(Data<?> data);
	public void saveAsync(Data<?> data);
	
	public void saveAll(List<Data<?>> datas);
	public void saveAllAsync(List<Data<?>> datas);
	
	public void disabled();

	public StorageType getStorageType();
	public IDataManager getManager();
	void executeSimpleAsyncRequette(AsyncData<?> asyncData);

}
