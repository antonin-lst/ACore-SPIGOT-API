package fr.azefgh456.acore.storage.json.manager;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.storage.utils.IDataManager;

public class JsonManager implements IDataManager {

	@SuppressWarnings("unused")
	private ACore plugin;
	
	public JsonManager(ACore plugin) {
		this.plugin = plugin;
	}
	
}
