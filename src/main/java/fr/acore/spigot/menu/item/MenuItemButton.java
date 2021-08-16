package fr.acore.spigot.menu.item;


import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.menu.item.MenuItem;
import fr.acore.spigot.menu.event.ItemButtonClickEvent;
import fr.acore.spigot.utils.item.ItemBuilder;

public abstract class MenuItemButton extends MenuItem {

	public MenuItemButton(ItemBuilder item, int place) {
		super(item, place);
	}

	public abstract void onClick(ItemButtonClickEvent event);

}
