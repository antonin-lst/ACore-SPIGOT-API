package fr.azefgh456.acore.hook.hooks;


import org.bukkit.plugin.PluginManager;

import com.earth2me.essentials.IEssentials;

import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;

public class EssentialsHook extends IHook{
	
	private IEssentials essentials;
	public IEssentials getEssentials() { return this.essentials;}
	
	public EssentialsHook() {
		essentials = null;
	}
	
	@Override
	public IHook hook(PluginManager pm) throws PluginNotFoundException {
		if(pm.getPlugin(getHookName()) == null) throw new PluginNotFoundException(this);
	
		essentials = (IEssentials) pm.getPlugin(getHookName());
		
		if(essentials != null ) {
			setHooked();
			return this;
		}
		return null;
	}
	/*
	public boolean usePaper() {
		System.out.println(PaperLib.getEnvironment().getName());
		PaperLib.setCustomEnvironment(new PaperEnvironment());
		return PaperLib.isPaper();
	}
	
	public void suggestPaper() {
		PaperLib.suggestPaper(ACore.plugin);
	}
	
	
	public CompletableFuture<Boolean> teleportSyncPlayer(Player player, Location loc) {
		return new AsyncTeleportSync().teleportAsync(player, loc, TeleportCause.PLUGIN);
	}
	
	public CompletableFuture<Boolean> teleportAsyncPlayer(Player player, Location location, boolean safeMode) {
		int x = location.getBlockX();
		int z = location.getBlockZ();
		return PaperLib.getChunkAtAsync(player.getWorld(), x, z, true).thenApplyAsync(chunk -> Boolean.valueOf(player.teleport(location)));
	}
	
	public static CompletableFuture<Boolean> isSafeBlockAsync(Location loc){
		return PaperLib.getChunkAtAsync(loc).thenApplyAsync( chunk -> isLocationSafe(chunk.getBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())));
	}
	

	private static boolean isLocationSafe(Block block) {
	    Block ground = block.getRelative(BlockFace.DOWN);
	    Block head = block.getRelative(BlockFace.UP);
	    if (head.getType().isSolid())
	      return false; 
	    if (ground.isLiquid())
	      return false; 
	    if (ground.getType().isSolid())
	      return true; 
	    return true;
	}
*/
	@Override
	public String getHookName() {
		return "Essentials";
	}

}
