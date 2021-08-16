package fr.acore.spigot.menu;

import fr.acore.spigot.api.menu.IMenu;
import fr.acore.spigot.api.menu.MenuSize;
import fr.acore.spigot.api.menu.item.MenuItem;
import fr.acore.spigot.menu.item.MenuItemButton;
import fr.acore.spigot.utils.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public abstract class AMenu implements IMenu {

    private String title;
    private MenuSize size;

    private List<MenuItem> contents;

    public AMenu(String title, MenuSize size){
        this.title = title;
        this.size = size;
        this.contents = new ArrayList<>();
    }

    @Override
    public MenuSize getSize() {
        return this.size;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public List<MenuItem> getContents() {
        return this.contents;
    }

    public void addItem(ItemStack item, int place) {
        addItem(new ItemBuilder(item), place);
    }

    public void addItem(ItemBuilder item, int place) {
        addItem(new MenuItem(item, place));
    }

    @Override
    public void addItem(MenuItem item) {
        contents.add(item);
    }

    public List<MenuItemButton> getButtonItem(){
        List<MenuItemButton> itemsButton = new ArrayList<>();
        for(MenuItem item : contents) {
            if(item instanceof MenuItemButton) itemsButton.add((MenuItemButton) item);
        }

        return itemsButton;
    }

    @Override
    public MenuItemButton getButtonItem(ItemStack item) {
        for(MenuItem button : getButtonItem()) {
            if(button.getItem().equals(item)) return (MenuItemButton) button;
        }
        return null;
    }

    @Override
    public boolean isMenuItemButton(ItemStack clickedItem) {
        for(MenuItem mItem : getButtonItem()) {
            if(mItem.getItem().equals(clickedItem)) return true;
        }
        return false;
    }

    @Override
    public Inventory toInventory() {
        int s = size.getSize();
        Inventory inv = Bukkit.createInventory(null, s, title);

        for(MenuItem menuItem : contents) {
            inv.setItem(menuItem.getPlace(), menuItem.getItem());
        }

        return inv;

    }

    @Override
    public void clearContents() {
        this.contents.clear();
    }
}
