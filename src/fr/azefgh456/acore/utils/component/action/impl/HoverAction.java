package fr.azefgh456.acore.utils.component.action.impl;

import java.util.List;

import org.bukkit.entity.Player;

import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.utils.component.EasyComponent;
import fr.azefgh456.acore.utils.component.action.IAction;

public class HoverAction<T extends AManager> implements IAction<T>{

	private List<String> hoverMessages;
	
	public HoverAction(List<String> hoverMessages) {
		this.hoverMessages = hoverMessages;
	}
	
	@Override
	public EasyComponent transform(T manager, Player player, Player target, EasyComponent component, Object... ttt) {
		if(hoverMessages == null || hoverMessages.isEmpty()) return component;
		
		StringBuilder hoverMsg = new StringBuilder();
		for(String hm : hoverMessages) {
			System.out.println("Hover component logs : " + ttt.length);
			String cp = manager.replaceAll(hm, player, target, ttt);
			if(cp != null) {
				hoverMsg.append(cp);
				if(hoverMessages.indexOf(cp) < hoverMessages.size()-1) hoverMsg.append("\n");
			}
		}
		return component.showText(hoverMsg.toString());
	}
	
	

}
