package fr.azefgh456.acore.menu.item;

import fr.azefgh456.acore.menu.item.utils.ItemButtonClickEvent;
import fr.azefgh456.acore.menu.pagination.nav.NavBar;
import fr.azefgh456.acore.menu.pagination.nav.action.NavAction;
import fr.azefgh456.acore.utils.item.ItemBuilder;

public abstract class NavItemButton extends MenuItemButton{

	private NavBar navBar;
	private NavAction action;
	
	public NavItemButton(ItemBuilder item, int place, NavBar navBar, NavAction action) {
		super(item, place);
		this.navBar = navBar;
		this.action = action;
	}	

	
	public abstract void preClick(ItemButtonClickEvent event, NavAction action);

	@Override
	public void onClick(ItemButtonClickEvent event) {
		preClick(event, action);
		if(event.isCancelled()) return;
		
		event.setCancelled(true);
		
		if(action.equals(NavAction.PRECEDENT)) {
			navBar.setCurrentPage(navBar.getCurrent()-1);
		}else {
			navBar.setCurrentPage(navBar.getCurrent()+1);
		}
		
		navBar.getCurrentMenu().clear();
		event.getPlayer().openInventory(navBar.getCurrentMenu().toInventory());
		
	}
}
