package fr.acore.spigot.api.faction;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;

import fr.acore.spigot.api.player.impl.CorePlayer;

public interface IFactionManager<T extends IFaction> {

	public List<T> getFactions();
	public T getFactionByName(String name);
	public T getFactionById(String id);
	
	public IFactionPlayer getFactionPlayer(String name);
	public IFactionPlayer getFactionPlayer(UUID uuid);
	public IFactionPlayer getFactionPlayer(CorePlayer<?> player);
	
	public boolean hasFaction(CorePlayer<?> player);
	public boolean hisFaction(IFaction faction);
	
	public ChatColor getRelationColor(CorePlayer<?> player, CorePlayer<?> target);
	public String getColoredFactionName(CorePlayer<?> player, CorePlayer<?> target);
	//public String getChatTag(Player player, Player recipient) throws UnsuportedVersionException;
	public abstract void registerFactionListener();
	
	public FactionType getFactionType();
}
