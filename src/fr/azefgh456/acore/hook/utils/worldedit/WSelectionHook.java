package fr.azefgh456.acore.hook.utils.worldedit;

import org.bukkit.Location;

import com.sk89q.worldedit.bukkit.selections.Selection;

public class WSelectionHook {
	
	private Selection selection;
	
	public WSelectionHook(Selection selection) {
		this.selection = selection;
	}
	
	public boolean isEmpty() {
		return selection == null;
	}
	
	public boolean containPos(Location loc) {
		if(isEmpty()) return false;
		
		return selection.contains(loc);
	}

}
