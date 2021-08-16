package fr.acore.spigot.api.menu.item;

import fr.acore.spigot.utils.item.ItemBuilder;
import org.bukkit.inventory.ItemStack;

public class MenuItem {

    private ItemBuilder item;
    private int place;

    public MenuItem(ItemBuilder item, int place){
        this.item = item;
        this.place = place;
    }

    public void setItem(ItemBuilder item) {
        this.item = item;
    }

    public ItemStack getItem() {
        return item.build();
    }

    public int getPlace() {
        return this.place;
    }

}
