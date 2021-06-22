package fr.azefgh456.acore.menu.pagination;

import fr.azefgh456.acore.menu.pagination.nav.NavBar;

public class Pagination {
	
	private NavBar navBar;
	public NavBar getNavBar() { return this.navBar;}
	
	private Theme theme;
	public Theme getTheme() { return this.theme;}
	
	public Pagination(NavBar navBar, Theme theme) {
		this.navBar = navBar;
		this.theme = theme;
		//items = Arrays.asList(new MenuItem(new ItemBuilder(Material.STAINED_GLASS_PANE), 0), new MenuItem(new ItemBuilder(Material.STAINED_GLASS_PANE), 0), new MenuItem(new ItemBuilder(Material.STAINED_GLASS_PANE), 0), new MenuItem(new ItemBuilder(Material.PAPER), 0), new MenuItem(new ItemBuilder(Material.STAINED_GLASS_PANE), 0), new MenuItem(new ItemBuilder(Material.PAPER), 0), new MenuItem(new ItemBuilder(Material.STAINED_GLASS_PANE), 0), new MenuItem(new ItemBuilder(Material.STAINED_GLASS_PANE), 0), new MenuItem(new ItemBuilder(Material.STAINED_GLASS_PANE), 0));
	}
	
	public void addItemToMenu() {
		navBar.addItemToMenu();
	}
	
	/*
	public List<MenuItem> getItems(){
		return items;
	}*/

}
