package fr.acore.spigot.api.menu;

import java.util.Map;

import fr.acore.spigot.api.manager.IManager;
import fr.acore.spigot.api.player.impl.CorePlayer;

public interface IMenuManager extends IManager {
	
	public Map<CorePlayer<?>, IMenu> getPlayersMenu();
	
	public void openMenu(CorePlayer<?> player, IMenu menu);
	public boolean containPlayer(CorePlayer<?> player);
	public void removePlayer(CorePlayer<?> player);
	public IMenu getPlayerMenu(CorePlayer<?> player);

}
