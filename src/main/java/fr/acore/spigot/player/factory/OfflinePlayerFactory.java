package fr.acore.spigot.player.factory;

import fr.acore.spigot.api.storage.schema.ISchema;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import fr.acore.spigot.api.player.impl.OfflineCorePlayer;
import fr.acore.spigot.api.storage.constraint.query.QueryConstraintType;
import fr.acore.spigot.hook.hooks.FactionHook;
import fr.acore.spigot.hook.manager.HookManager;
import fr.acore.spigot.player.manager.PlayerManager;
import fr.acore.spigot.player.offline.OfflineCorePlayerStorage;
import fr.acore.spigot.player.online.OnlineCorePlayerStorage;
import fr.acore.spigot.storage.constraint.QueryConstraint;
import fr.acore.spigot.storage.factory.DataFactory;

public class OfflinePlayerFactory extends DataFactory<OfflineCorePlayerStorage, PlayerManager> {

	private FactionHook factionHook;
	
	public OfflinePlayerFactory(PlayerManager manager) {
		super(OfflineCorePlayerStorage.class, manager);
		factionHook = manager.getPlugin().getInternalManager(HookManager.class).getHook(FactionHook.class);
	}

	@Override
	public void loadAll() {
		for(OfflinePlayer offPlayer : Bukkit.getOfflinePlayers()) {
			if(contain(offPlayer.getUniqueId().toString())) {
				OfflineCorePlayer offcp = null;
				manager.addPlayer(offcp = selectFirst(new QueryConstraint(QueryConstraintType.WHERE, "uuid = ?", offPlayer.getUniqueId().toString())).setOfflinePlayer(offPlayer));
			}else {
				manager.addPlayer(new OfflineCorePlayerStorage(offPlayer));
			}
		}
	}

	@Override
	public void saveAll() {
		for(OfflineCorePlayer offCorePlayer : manager.getOfflineCorePlayers()) {
			if(offCorePlayer.isOnline()) {
				save((OfflineCorePlayerStorage) ((OnlineCorePlayerStorage) offCorePlayer).getOfflinePlayer());
			}else {
				save((OfflineCorePlayerStorage) offCorePlayer);
			}
			
		}
	}
	
	public boolean contain(String uuid) {
		return this.contain("uuid = ?", uuid);
	}

	@Override
	public void save(OfflineCorePlayerStorage obj) {
		//System.out.println(obj.getUuid().toString());
		//System.out.println("ContainOFFPLAYER : " + String.valueOf(contain(obj.getUuid().toString())));
		if(contain(obj.getUuid().toString())) {
			update(obj);
		}else {
			insert(obj);
		}
	}


}
