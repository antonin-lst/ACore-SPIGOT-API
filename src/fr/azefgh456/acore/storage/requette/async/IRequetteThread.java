package fr.azefgh456.acore.storage.requette.async;

public interface IRequetteThread<T> extends Runnable{

	
	public void loadAsync(T data);
	public void saveAsync(T data);
	
	
	public static interface IRequetteThread2<T, V> extends Runnable{
		
		public void loadAsync(T data, V datas);
		public void saveAsync(T data, V datas);
		
	}
	
}
