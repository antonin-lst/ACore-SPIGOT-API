package fr.azefgh456.acore.menu.item;

import org.bukkit.inventory.ItemStack;

import fr.azefgh456.acore.utils.item.ItemBuilder;

public class MenuItem {
	
	private ItemBuilder item;
	private int place;
	
	public MenuItem(ItemBuilder item, int place) {
		this.item = item;
		this.place = place;
	}
	
	public void setItem(ItemBuilder item) {
		this.item = item;
	}
	
	public int getPlace() {
		return this.place;
	}
	
	public ItemStack getItem() {
		return item.build();
	}

}
