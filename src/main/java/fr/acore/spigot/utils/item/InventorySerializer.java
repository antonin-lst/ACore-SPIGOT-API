package fr.acore.spigot.utils.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventorySerializer {
	
	private static String regex = "!";
	//private static String regex2 = "*";
	
	public static String serealize(Inventory inv) {
		StringBuilder builder = new StringBuilder("");
		for(int i = 0; i < inv.getSize(); i++) {
			if(inv.getItem(i) == null) {
				builder.append(ItemSerializer.serialize(new ItemStack(Material.AIR, 1)));
			}else {
				builder.append(ItemSerializer.serialize(inv.getItem(i)));
			}
			
			
			if(i < inv.getSize()-1) builder.append(regex);
		}
		return builder.toString();
	}
	
	public static Inventory deserealize(String data) {
		Inventory inv = Bukkit.createInventory(null, 54);
		
		String datas[] = data.split(regex);
		
		for(int i = 0; i < 54; i++) {
			inv.setItem(i, ItemSerializer.deserialize(datas[i]));
		}
		
		return inv;
	}

}
