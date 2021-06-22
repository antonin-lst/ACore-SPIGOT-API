package fr.azefgh456.acore.storage.utils;

import fr.azefgh456.acore.manager.AManager;

public class AsyncData<T extends SaveableObject> implements Runnable{

	private T saveableObject;
	private Action action;
	private AManager manager;
	private Data<?> data;
	
	public AsyncData(T saveableObject, Action action, AManager manager, Data<?> data) {
		this.saveableObject = saveableObject;
		this.action = action;
		this.manager = manager;
		this.data = data;
	}
	
	@Override
	public void run() {
		if(action == Action.LOAD) {
			saveableObject.load(manager, data);
		}else {
			saveableObject.save(manager, data);
		}
	}
	
	public static enum Action{
		
		LOAD, SAVE;
		
	}
	
}
