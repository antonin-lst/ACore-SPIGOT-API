package fr.azefgh456.acore.menu.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.menu.AMenu;
import fr.azefgh456.acore.menu.listener.MenuListener;
import fr.azefgh456.acore.plugin.IPlugin;

public class MenuManager extends AManager{
	
	private Map<Player, AMenu> openMenus;
	
	public MenuManager(IPlugin plugin) {
		super(plugin, false);
		openMenus = new HashMap<>();
		registerListener(new MenuListener(this));
	}
	
	public void openMenu(Player player, AMenu menu) {
		if(openMenus.containsKey(player)) {
			player.closeInventory();
			removePlayer(player);
		}
		player.openInventory(menu.toInventory());
		openMenus.put(player, menu);
	}
	
	public boolean containPlayer(Player player) {
		return openMenus.containsKey(player);
	}
	
	public void removePlayer(Player player) {
		openMenus.remove(player);
	}

	public AMenu getAMenu(Player player) {
		return openMenus.get(player);
	}
}
