package fr.azefgh456.acore.menu;

import org.bukkit.inventory.Inventory;

import fr.azefgh456.acore.menu.pagination.Pagination;
import fr.azefgh456.acore.menu.pagination.Theme;
import fr.azefgh456.acore.menu.pagination.nav.NavBar;
import fr.azefgh456.acore.menu.utils.Size;

public abstract class ANavMenu extends AMenu {

	protected Pagination pagination;
	
	public ANavMenu(String title, Size size, NavBar navBar) {
		this(title, size, navBar, null);
		navBar.setCurrentMenu(this);
	}

	public ANavMenu(String title, Size size, NavBar navBar, Theme theme) {
		super(title, size);
		this.pagination = new Pagination(navBar, theme);
	}
	
	public abstract void build(int currentPage);
	
	
	@Override
	public Inventory toInventory() {
		pagination.addItemToMenu();
		build(pagination.getNavBar().getCurrent());
		return super.toInventory();
	}

	public int getRows() {
		return getSize().getRows();
	}

}
