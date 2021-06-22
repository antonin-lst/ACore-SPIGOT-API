package fr.azefgh456.acore.config.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.config.Setupable;
import fr.azefgh456.acore.config.utils.Conf;
import fr.azefgh456.acore.manager.IManager;
import fr.azefgh456.acore.manager.utils.Informable;
import fr.azefgh456.acore.plugin.IPlugin;
import fr.azefgh456.acore.storage.manager.StorageManager;

public class ConfigManager implements IManager, Informable{

	private ACore plugin;
	
	private Conf conf;
	public Conf getConf() {	return this.conf;}
	
	private Map<IPlugin, List<Setupable>> configParticipator;
	
	public ConfigManager(ACore plugin) {
		this.plugin = plugin;
		configParticipator = new HashMap<>();
		addSetupable(conf = new Conf(plugin));
	}
	
	@Override
	public void logs() {
		plugin.log("ConfBoard Loaded");
	}
	
	public void addSetupable(Setupable setupable) {
		if(setupable == null || setupable.getPlugin() == null) return;
		
		if(configParticipator.containsKey(setupable.getPlugin())) {
			configParticipator.get(setupable.getPlugin()).add(setupable);
		}else {
			List<Setupable> setupableList = new ArrayList<>();
			setupableList.add(setupable);
			configParticipator.put(setupable.getPlugin(), setupableList);
		}
	}
	
	public void reloadConfig() {
		if(configParticipator.isEmpty()) {
			plugin.log("Configuration is empty");
			return;
		}
		
		plugin.log("Debut de lactualisation de la config\n");
		for(Entry<IPlugin, List<Setupable>> entry : configParticipator.entrySet()) {
			plugin.log("IPlugin : " + entry.getKey().getName());
			entry.getKey().reloadConfig(entry.getKey().file);
			
			if(entry.getValue().isEmpty()) {
				plugin.log("Aucun module passage au prochain IPlugin");
				continue;
			}
			
			plugin.log(ChatColor.GOLD + entry.getKey().getName() + ChatColor.YELLOW + "(" + ChatColor.AQUA + entry.getValue().size() + ChatColor.YELLOW + ") " + ChatColor.AQUA + "Setupable");
			
			for(Setupable setupable : entry.getValue()) {
				try {
					setupable.setup(setupable.getPlugin().getConfig());
					plugin.log("Chargement réusit : " + ChatColor.YELLOW + setupable.getClass().getSimpleName());
				}catch(Exception ex) {
					plugin.logErr("Erreur de chargement de la configuration du module : " + getClass().getSimpleName());
					continue;
				}
			}
			
			if(entry.getKey().getName().equals("ACore")) {
				StorageManager<?> storageM = plugin.getManager(StorageManager.class);
				storageM.saveAll();
				//((StorageManager<? extends IStorage>) plugin.getManager(StorageManager.class)).saveAll();
				plugin.log("Data save");
				plugin.registerManager(plugin.setStorage());
				plugin.log("Actualisation du systeme de data finit");
			}
		}
	}
}
