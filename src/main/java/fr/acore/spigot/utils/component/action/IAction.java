package fr.acore.spigot.utils.component.action;

import org.bukkit.entity.Player;

import fr.acore.spigot.utils.component.EasyComponent;

public interface IAction<T extends IActionReplacer> {
	
	public EasyComponent transform(T manager, Player sender, Player target, EasyComponent component, Object... datas);
	

}
