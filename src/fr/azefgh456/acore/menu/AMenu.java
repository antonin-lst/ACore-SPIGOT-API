package fr.azefgh456.acore.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.azefgh456.acore.menu.item.MenuItem;
import fr.azefgh456.acore.menu.item.MenuItemButton;
import fr.azefgh456.acore.menu.utils.Size;
import fr.azefgh456.acore.utils.item.ItemBuilder;

public abstract class AMenu {
	
    private String title;

    private Size size;
    public Size getSize() { return this.size;}
	
	private List<MenuItem> contents;
	
	public AMenu(String title, Size size) {
		this.title = title;
		this.size = size;
		contents = new ArrayList<>();
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
    
	public String getTitle() {
		return title;
	}
	
	public void addItem(ItemStack item, int place) {
		addItem(new ItemBuilder(item), place);
	}
	
	public void addItem(ItemBuilder item, int place) {
		addItem(new MenuItem(item, place));
	}
	
	public void addItem(MenuItem item) {
		contents.add(item);
	}
	
	public List<MenuItemButton> getButtonItem(){
		List<MenuItemButton> itemsButton = new ArrayList<>();
		for(MenuItem item : contents) {
			if(item instanceof MenuItemButton) itemsButton.add((MenuItemButton) item);
		}
		
		return itemsButton;
	}
	
	public MenuItemButton getButtonItem(ItemStack item) {
		for(MenuItem button : getButtonItem()) {
			if(button.getItem().equals(item)) return (MenuItemButton) button;
		}
		return null;
	}
	
	public boolean isMenuItemButton(ItemStack clickedItem) {
		for(MenuItem mItem : getButtonItem()) {
			if(mItem.getItem().equals(clickedItem)) return true;
		}
		return false;
	}
	
	public Inventory toInventory() {
		int s = size.getSize();
		Inventory inv = Bukkit.createInventory(null, s, title);
		
		for(MenuItem menuItem : contents) {
			inv.setItem(menuItem.getPlace(), menuItem.getItem());
		}
		
		return inv;
		
	}
	
	public void clear() {
		this.contents.clear();
	}
	
	public abstract void onClick(InventoryClickEvent e);
	public abstract void onClose(InventoryCloseEvent e);
	public abstract void onDrag(InventoryDragEvent e);


}