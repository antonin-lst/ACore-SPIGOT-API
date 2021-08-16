package fr.acore.spigot.api.menu;

import fr.acore.spigot.api.menu.item.MenuItem;
import fr.acore.spigot.menu.item.MenuItemButton;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface IMenu {

    public MenuSize getSize();
    public String getTitle();

    public List<MenuItem> getContents();
    public void clearContents();
    public void addItem(MenuItem item);

    public boolean isMenuItemButton(ItemStack item);
    public MenuItemButton getButtonItem(ItemStack item);

    public Inventory toInventory();

    public void onClick(InventoryClickEvent e);
    public void onClose(InventoryCloseEvent e);
    public void onDrag(InventoryDragEvent e);

}
