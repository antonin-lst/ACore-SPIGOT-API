package fr.azefgh456.acore.player.players;

import java.util.Optional;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import fr.azefgh456.acore.hook.hooks.LuckPermHook;
import fr.azefgh456.acore.hook.hooks.RandomTPHook;
import fr.azefgh456.acore.hook.hooks.VaultHook;
import fr.azefgh456.acore.hook.hooks.WorldEditHook;
import fr.azefgh456.acore.hook.hooks.WorldGuardHook;
import fr.azefgh456.acore.hook.utils.worldedit.WSelectionHook;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryOptions;

public abstract class HookedPlayer extends NMSPlayer{

	private VaultHook vaultHook;
	private RandomTPHook rtpHook;
	private WorldGuardHook worldgHook;
	private WorldEditHook worldeHook;
	private LuckPermHook luckPermHook;
	
	public HookedPlayer(OfflinePlayer player) {
		super(player);
		vaultHook = plugin.getHook("Vault");
		rtpHook = plugin.getHook("RandomTP");
		worldgHook = plugin.getHook("WorldGuard");
		worldeHook = plugin.getHook("WorldEdit");
		luckPermHook = plugin.getHook("LuckPerms");
	}
	
	public String getPermissionPrefix() {
		if(!luckPermHook.hisHooked()) {
			plugin.log("LuckPerms n'est pas disponible");
			return null;
		}
		User user = luckPermHook.getLuckPerm().getUserManager().getUser(player.getUniqueId());
		Optional<QueryOptions> options = LuckPermsProvider.get().getContextManager().getQueryOptions(user);
	    if (!options.isPresent())
	      return ""; 
	    String prefix = user.getCachedData().getMetaData(options.get()).getPrefix();
	    return (prefix == null) ? "" : prefix;
	}
	

	    
	  

	@Override
	public void addMoney(double somme) {
		if(!vaultHook.hisHooked()) {
			plugin.log("Vault n'est pas disponible");
			return;
		}
		vaultHook.getEconomy().depositPlayer(player, somme);
	}
	
	@Override
	public void addMoney(int somme) {
		if(!vaultHook.hisHooked()) {
			plugin.log("Vault n'est pas disponible");
			return;
		}
		vaultHook.getEconomy().depositPlayer(player, somme);
	}
	
	@Override
	public double getBalance() {
		if(!vaultHook.hisHooked()) {
			plugin.log("Vault n'est pas disponible");
			return 0;
		}
		return vaultHook.getEconomy().getBalance(player);
	}
	
	@Override
	public void removeMoney(double somme) {
		if(!vaultHook.hisHooked()) {
			plugin.log("Vault n'est pas disponible");
			return;
		}
		
		vaultHook.getEconomy().withdrawPlayer(player, somme);
	}
	
	@Override
	public void removeMoney(int somme) {
		if(!vaultHook.hisHooked()) {
			plugin.log("Vault n'est pas disponible");
			return;
		}
		vaultHook.getEconomy().withdrawPlayer(player, somme);
	}
	
	@Override
	public boolean removeMoneySafe(double somme) {
		if(!vaultHook.hisHooked()) {
			plugin.log("Vault n'est pas disponible");
			return false;
		}
		
		if(getBalance() < somme) return false;
		
		removeMoney(somme);
		return true;
	}
	
	@Override
	public boolean removeMoneySafe(int somme) {
		if(!vaultHook.hisHooked()) {
			plugin.log("Vault n'est pas disponible");
			return false;
		}
		
		if(getBalance() < somme) return false;
		
		removeMoney(somme);
		return true;
	}
	
	
	@Override
	public void randomTeleport(Player player, int distance) {
		if(!rtpHook.hisHooked()) {
			plugin.log("RandomTP n'est pas disponible");
			return;
		}
		rtpHook.getRandomTP().randomTeleportPlayer(player, player.getWorld(), distance);
	}
	
	@Override
	public void randomTeleport(Player player, int distance, Location centerLocation) {
		if(!rtpHook.hisHooked()) {
			plugin.log("RandomTP n'est pas disponible");
			return;
		}
		rtpHook.getRandomTP().randomTeleportPlayer(player, player.getWorld(), distance, centerLocation);
	}
	
	@Override
	public void randomTeleport(Player player, World world, int distance) {
		if(!rtpHook.hisHooked()) {
			plugin.log("RandomTP n'est pas disponible");
			return;
		}
		
		rtpHook.getRandomTP().randomTeleportPlayer(player, world, distance);
		
	}
	@Override
	public void randomTeleport(Player player, World world, int distance, Location centerLocation) {
		if(!rtpHook.hisHooked()) {
			plugin.log("RandomTP n'est pas disponible");
			return;
		}
		
		rtpHook.getRandomTP().randomTeleportPlayer(player, world, distance, centerLocation);
	}
	@Override
	public void randomTeleport(Player player, World world, int distance, int cost) {
		if(!rtpHook.hisHooked()) {
			plugin.log("RandomTP n'est pas disponible");
			return;
		}
		rtpHook.getRandomTP().randomTeleportPlayer(player, world, distance, cost);
	}
	@Override
	public void randomTeleport(Player player, World world, int distance, int cost, Location centerLocation) {
		if(!rtpHook.hisHooked()) {
			plugin.log("RandomTP n'est pas disponible");
			return;
		}
		
		rtpHook.getRandomTP().randomTeleportPlayer(player, world, distance, centerLocation, cost);
	}
	@Override
	public void randomTeleport(Player player, World world, int distance, int cost, long delay) {
		if(!rtpHook.hisHooked()) {
			plugin.log("RandomTP n'est pas disponible");
			return;
		}

		
		rtpHook.getRandomTP().randomTeleportPlayer(player, world, distance, cost);
	}
	
	@Override
	public void randomTeleport(Player player, World world, int distance, int cost, long delay, Location centerLocation) {
		if(!rtpHook.hisHooked()) {
			plugin.log("RandomTP n'est pas disponible");
			return;
		}
		rtpHook.getRandomTP().randomTeleportPlayer(player, world, distance, centerLocation, cost);
	}
	
	@Override
	public boolean isInRegion(String name) {
		if(!worldgHook.hisHooked()) {
			plugin.log("WorldGuard n'est pas disponible");
			return false;
		}
		Location loc = getPlayer().getLocation();
		ProtectedRegion region = worldgHook.getWorldGuard().getRegionManager(loc.getWorld()).getRegion(name);

		return region != null && region.contains((int)loc.getX(),(int) loc.getY(),(int) loc.getZ());
	}
	
	public WSelectionHook getWorldEditSection() {
		return new WSelectionHook(worldeHook.getWorldEdit().getSelection(getPlayer()));
	}
	
}
