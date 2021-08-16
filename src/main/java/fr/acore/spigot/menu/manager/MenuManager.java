package fr.acore.spigot.menu.manager;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.menu.IMenu;
import fr.acore.spigot.api.menu.IMenuManager;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.plugin.IPlugin;
import fr.acore.spigot.menu.listener.MenuListener;

import java.util.HashMap;
import java.util.Map;

public class MenuManager implements IMenuManager {

    private ACoreSpigotAPI plugin;

    private Map<CorePlayer<?>, IMenu> playersMenu;

    public MenuManager(ACoreSpigotAPI instance){
        this.plugin = instance;
        this.playersMenu = new HashMap<>();
        instance.registerListener(new MenuListener(this));
    }

    @Override
    public IPlugin<?> getPlugin() {
        return plugin;
    }

    @Override
    public Map<CorePlayer<?>, IMenu> getPlayersMenu() {
        return playersMenu;
    }

    @Override
    public void openMenu(CorePlayer<?> player, IMenu menu) {
        if(playersMenu.containsKey(player)) {
            player.getPlayer().closeInventory();
            removePlayer(player);
        }
        player.getPlayer().openInventory(menu.toInventory());
        playersMenu.put(player, menu);
    }

    @Override
    public boolean containPlayer(CorePlayer<?> player) {
        return playersMenu.containsKey(player);
    }

    @Override
    public void removePlayer(CorePlayer<?> player) {
        playersMenu.remove(player);
    }

    @Override
    public IMenu getPlayerMenu(CorePlayer<?> player) {
        return playersMenu.get(player);
    }

    /*
     *
     * Gestion des logs
     *
     */

    @Override
    public void log(String... args) {
        plugin.log(args);
    }

    @Override
    public void log(Object... args) {
        plugin.log(args);
    }

    @Override
    public void logWarn(String... args) {
        plugin.logWarn(args);
    }

    @Override
    public void logWarn(Object... args) {
        plugin.logWarn(args);
    }

    @Override
    public void logErr(String... args) {
        plugin.logErr(args);
    }

    @Override
    public void logErr(Object... args) {
        plugin.logErr(args);
    }

}
