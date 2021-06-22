package fr.azefgh456.acore.utils.component.action.impl;

import org.bukkit.entity.Player;

import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.utils.component.EasyComponent;
import fr.azefgh456.acore.utils.component.action.IAction;

public class SuggestCommandAction<T extends AManager> implements IAction<T>{

	private String command;
	
	public SuggestCommandAction(String command) {
		this.command = command;
	}
	
	@Override
	public EasyComponent transform(T manager, Player sender, Player target, EasyComponent component, Object... datas) {
		if(command == null || command.isEmpty()) return component;
		
		component.suggestCommand(manager.replaceAll(command, sender, target, datas));
		
		return component;
	}

}
