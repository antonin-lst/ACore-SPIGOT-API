package fr.azefgh456.acore.menu.pagination.nav;

import java.util.List;

import fr.azefgh456.acore.menu.ANavMenu;
import fr.azefgh456.acore.menu.item.NavItemButton;

public abstract class NavBar {

	protected ANavMenu currentMenu;
	public ANavMenu getCurrentMenu() { return this.currentMenu;}
	private int current;
	public int getCurrent() { return this.current;}
	
	private List<NavItemButton> navItems;
	
	public NavBar(List<NavItemButton> navItems) {
		this.current = 0;
		this.navItems = navItems;
	}
	
	public NavBar() {
	}

	public void setCurrentMenu(ANavMenu currentMenu) {
		this.currentMenu = currentMenu;
	}
	
	protected void setNavItems(List<NavItemButton> navItems) {
		this.navItems = navItems;
	}
	
	public List<NavItemButton> getNavItems(){
		return this.navItems;
	}

	public void setCurrentPage(int current) {
		this.current = current;
	}

	public void addItemToMenu() {
		for(NavItemButton navItem : navItems) {
			currentMenu.addItem(navItem);
		}
	}
	
	public abstract boolean preClickAfter();
	

}
