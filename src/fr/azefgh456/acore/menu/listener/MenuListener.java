package fr.azefgh456.acore.menu.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.azefgh456.acore.menu.AMenu;
import fr.azefgh456.acore.menu.item.MenuItemButton;
import fr.azefgh456.acore.menu.item.utils.ItemButtonClickEvent;
import fr.azefgh456.acore.menu.manager.MenuManager;

public class MenuListener implements Listener {

	private MenuManager menuM;
	
	public MenuListener(MenuManager menuM) {
		this.menuM = menuM;
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getInventory();
		ItemStack item = event.getCurrentItem();
		if(inventory == null || !menuM.containPlayer(player)) return;
		
		AMenu targetMenu = menuM.getAMenu(player);
		System.out.println(targetMenu.isMenuItemButton(item));
		if(targetMenu.isMenuItemButton(item)) {
			MenuItemButton button = targetMenu.getButtonItem(item);
			ItemButtonClickEvent buttonClickEvent = new ItemButtonClickEvent(event.getClick(), player, item);
			button.onClick(buttonClickEvent);
			event.setCancelled(buttonClickEvent.isCancelled());
		}else {
			targetMenu.onClick(event);
		}
	}
	
	@EventHandler
	public void onDrag(InventoryDragEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inventory = event.getInventory();
		
		if(inventory == null || !menuM.containPlayer(player)) return;
		
		menuM.getAMenu(player).onDrag(event);
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		
		if(!menuM.containPlayer(player)) return;
		
		menuM.getAMenu(player).onClose(event);
		menuM.removePlayer(player);
		
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(menuM.containPlayer(player)) menuM.removePlayer(player);
	}
}
