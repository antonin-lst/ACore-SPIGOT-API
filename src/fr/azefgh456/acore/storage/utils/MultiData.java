package fr.azefgh456.acore.storage.utils;

import fr.azefgh456.acore.storage.requette.utils.contraint.Contraint;

public abstract class MultiData<T, M> extends Data<T>{

	public MultiData(String storageName, String content, Contraint contraint) {
		super(storageName, content, contraint);
	}

	@Override
	public boolean contain(T val) {
		return false;
	}
	
	protected abstract boolean contain(T val, M val2);
	
	
}
