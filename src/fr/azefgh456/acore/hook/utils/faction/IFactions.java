package fr.azefgh456.acore.hook.utils.faction;

import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface IFactions<V, W>{

	public List<IFaction<?, ?>> getFactions();
	public IFaction<V, W> getFactionByName(String name);
	public IFaction<V, W> getFactionById(String id);
	//public IFaction<V,W> getFactionByLoc(Location loc);
	
	public IPlayer<V, W> getFactionPlayer(String name);
	public IPlayer<V, W> getFactionPlayer(UUID uuid);
	public IPlayer<V, W> getFactionPlayer(Player player);
	
	public boolean hasFaction(Player player);
	public boolean hisFaction(IFaction<?,?> faction);
	
	public ChatColor getRelationColor(Player player, Player target);
	public String getColoredFactionName(Player player, Player target);
	//public String getChatTag(Player player, Player recipient) throws UnsuportedVersionException;
	public abstract void registerFactionListener();
}