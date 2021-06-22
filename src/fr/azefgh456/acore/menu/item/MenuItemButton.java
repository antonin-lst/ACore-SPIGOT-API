package fr.azefgh456.acore.menu.item;

import fr.azefgh456.acore.ACore;
import fr.azefgh456.acore.menu.item.utils.ItemButtonClickEvent;
import fr.azefgh456.acore.utils.item.ItemBuilder;

public abstract class MenuItemButton extends MenuItem {
	
	protected ACore plugin = ACore.plugin;

	public MenuItemButton(ItemBuilder item, int place) {
		super(item, place);
	}
	public abstract void onClick(ItemButtonClickEvent event);
}
