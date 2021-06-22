package fr.azefgh456.acore.menu.pagination.nav;

import java.util.Arrays;

import org.bukkit.Material;

import fr.azefgh456.acore.menu.item.NavItemButton;
import fr.azefgh456.acore.menu.item.utils.ItemButtonClickEvent;
import fr.azefgh456.acore.menu.pagination.nav.action.NavAction;
import fr.azefgh456.acore.utils.item.ItemBuilder;

public class DefaultNavBar extends NavBar{

	public DefaultNavBar() {
		ItemBuilder decoNavBar = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, "", (byte)3);
		ItemBuilder buttonNav1 = new ItemBuilder(Material.PAPER, 1, "Page précedante");
		ItemBuilder buttonNav2 = new ItemBuilder(Material.PAPER, 1, "Page suivante");
		
		setNavItems(Arrays.asList(new NavItemButton(buttonNav1, getCurrentMenu().getRows() * 9 - 10 + 2, this, NavAction.PRECEDENT) {
			
			@Override
			public void preClick(ItemButtonClickEvent event, NavAction action) {
				if(getCurrent() == 0) event.setCancelled(true);
			}
		}, new NavItemButton(buttonNav2, getCurrentMenu().getRows() * 9 - 10 + 4, this, NavAction.SUIVANT) {
			
			@Override
			public void preClick(ItemButtonClickEvent event, NavAction action) {
				if(!preClickAfter()) event.setCancelled(true);
			}
		}));
	}
	
	
	@Override
	public boolean preClickAfter() {
		return true;
	}
	
/*
	public DefaultNavBar(AMenu currentMenu, AMenu futureMenu) {
		super(currentMenu, futureMenu);
		ItemBuilder decoNavBar = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, "", (byte)3);
		ItemBuilder buttonNav1 = new ItemBuilder(Material.PAPER, 1, "Page précedante");
		ItemBuilder buttonNav2 = new ItemBuilder(Material.PAPER, 1, "Page suivante");
		
		List<MenuItem> navItems = Arrays.asList(
				new MenuItem(decoNavBar, 0), 
				new MenuItem(decoNavBar, 0), 
				new MenuItem(decoNavBar, 0),
				
				new NavItemButton(buttonNav1, 0, currentMenu, null) {
					
					@Override
					public void preClick(ItemButtonClickEvent event) {
						System.out.println("preClick");
						System.out.println(currentMenu.getParent() != null);
						
						if(currentMenu.getParent() == null) {
							event.setCancelled(true);
							return;
						}
						setTarget(currentMenu.getParent());
						System.out.println("oldMenu != null");
						event.setCancelled(preClickBefore(event));
					}
				},
				new MenuItem(decoNavBar, 0),
				new NavItemButton(buttonNav2, 0, currentMenu, futureMenu) {
					
					@Override
					public void preClick(ItemButtonClickEvent event) {
						if(futureMenu == null) {
							event.setCancelled(true);
							return;
						}
						System.out.println("futureMenu != null");
						event.setCancelled(preClickAfter(event));
						System.out.println("event canceled : " + String.valueOf(event.isCancelled()));
					}
				},
				new MenuItem(decoNavBar, 0),
				new MenuItem(decoNavBar, 0),
				new MenuItem(decoNavBar, 0));
		
		setNavItems(navItems);
	}
	
	public boolean preClickBefore(ItemButtonClickEvent event) {
		return event.isCancelled();
	}
	
	public boolean preClickAfter(ItemButtonClickEvent event) {
		return event.isCancelled();
	}*/

}
