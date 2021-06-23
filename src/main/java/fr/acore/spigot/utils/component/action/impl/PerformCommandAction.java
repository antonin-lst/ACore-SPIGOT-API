package fr.acore.spigot.utils.component.action.impl;

import org.bukkit.entity.Player;

import fr.acore.spigot.utils.component.EasyComponent;
import fr.acore.spigot.utils.component.action.IAction;
import fr.acore.spigot.utils.component.action.IActionReplacer;

public class PerformCommandAction<T extends IActionReplacer> implements IAction<T>{

	private String command;
	
	public PerformCommandAction(String command) {
		this.command = command;
	}
	
	@Override
	public EasyComponent transform(T manager, Player sender, Player target, EasyComponent component, Object... datas) {
		if(command == null || command.isEmpty()) return component;
		
		component.runCommand(manager.replaceAll(command, sender, target, datas));
		
		return component;
	}

	
	
}
