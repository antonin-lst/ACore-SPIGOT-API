package fr.acore.spigot.player.factory;

import fr.acore.spigot.commands.data.CommandStorage;
import fr.acore.spigot.player.manager.PlayerManager;
import fr.acore.spigot.player.online.OnlineCorePlayerStorage;
import fr.acore.spigot.storage.factory.DataFactory;

public class OnlinePlayerFactory extends DataFactory<OnlineCorePlayerStorage, PlayerManager> {

	public OnlinePlayerFactory(PlayerManager manager) {
		super(OnlineCorePlayerStorage.class, manager);
		table.getSchema().createTable(CommandStorage.class);
	}

	@Override
	public void loadAll() {
		
	}

	@Override
	public void saveAll() {
		// TODO Auto-generated method stub
		
	}
	
	public OnlineCorePlayerStorage load(String uuid) {
		if(contain(uuid)) {
			return load("uuid = ?", uuid);
		}else {
			return new OnlineCorePlayerStorage();
		}
		
	}

	public boolean contain(String uuid) {
		return contain("uuid = ?", uuid);
	}
	
	@Override
	public void save(OnlineCorePlayerStorage obj) {
		if(contain(obj.getUuid().toString())) {
			//update(obj);
		}else {
			insert(obj);
		}
	}

}
