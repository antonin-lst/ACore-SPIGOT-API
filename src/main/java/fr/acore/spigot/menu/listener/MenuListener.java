package fr.acore.spigot.menu.listener;

import fr.acore.spigot.api.menu.IMenu;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.menu.event.ItemButtonClickEvent;
import fr.acore.spigot.menu.item.MenuItemButton;
import fr.acore.spigot.menu.manager.MenuManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {

    private MenuManager menuM;

    public MenuListener(MenuManager menuM){
        this.menuM = menuM;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        CorePlayer<?> player = menuM.getPlugin().getCorePlayer((Player) event.getWhoClicked());
        Inventory inventory = event.getInventory();
        ItemStack item = event.getCurrentItem();
        if(inventory == null || !menuM.containPlayer(player)) return;

        IMenu targetMenu = menuM.getPlayerMenu(player);

        //System.out.println(targetMenu.isMenuItemButton(item));
        if(targetMenu.isMenuItemButton(item)) {
            MenuItemButton button = targetMenu.getButtonItem(item);
            ItemButtonClickEvent buttonClickEvent = new ItemButtonClickEvent(event.getClick(), player.getPlayer(), item);
            button.onClick(buttonClickEvent);
            event.setCancelled(buttonClickEvent.isCancelled());
        }else {
            targetMenu.onClick(event);
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        CorePlayer<?> player = menuM.getPlugin().getCorePlayer((Player) event.getWhoClicked());
        Inventory inventory = event.getInventory();

        if(inventory == null || !menuM.containPlayer(player)) return;

        menuM.getPlayerMenu(player).onDrag(event);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        CorePlayer<?> player = menuM.getPlugin().getCorePlayer((Player) event.getPlayer());
        if(!menuM.containPlayer(player)) return;

        menuM.getPlayerMenu(player).onClose(event);
        menuM.removePlayer(player);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        CorePlayer<?> player = menuM.getPlugin().getCorePlayer(event.getPlayer());
        if(menuM.containPlayer(player)) menuM.removePlayer(player);
    }
}
