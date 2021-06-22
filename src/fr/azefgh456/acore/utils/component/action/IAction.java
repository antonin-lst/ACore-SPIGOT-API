package fr.azefgh456.acore.utils.component.action;

import org.bukkit.entity.Player;

import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.utils.component.EasyComponent;

public interface IAction<T extends AManager> {
	
	public EasyComponent transform(T manager, Player sender, Player target, EasyComponent component, Object... datas);
	

}
