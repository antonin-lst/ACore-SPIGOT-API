package fr.azefgh456.acore.storage.utils;

import fr.azefgh456.acore.storage.requette.sync.ICreateRequette;
import fr.azefgh456.acore.storage.requette.sync.IRequette;
import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint;

public abstract class Data<T> {
	
	protected String storageName;
	private String content;
	private Contraint contraint;
	
	public Data(String storageName, String content, Contraint contraint) {
		this.storageName = storageName;
		this.content = content;
		this.contraint = contraint;
	}
	
	public Data(String storageName, String content) {
		this.storageName = storageName;
		this.content = content;
	}
	
	public IRequette createTable() {
		if(contraint !=null) {
			return new ICreateRequette(storageName, content, contraint);
		}
		return new ICreateRequette(storageName, content);
		
	}
	
	public String getStorageName() {
		return this.storageName;
	}
	
	public abstract void load();
	
	public abstract void save();
	
	public abstract boolean contain(T val);
	
}
