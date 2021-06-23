package fr.acore.spigot.utils.component.action;

import org.bukkit.entity.Player;

public interface IActionReplacer{

	String replaceAll(String command, Player sender, Player target, Object[] datas);

}
