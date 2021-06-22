package fr.azefgh456.acore.hook.hooks;

import java.lang.reflect.Field;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;
import fr.azefgh456.acore.hook.utils.faction.IFactions;
import fr.azefgh456.acore.hook.utils.faction.impl.FactionUUID;
import fr.azefgh456.acore.hook.utils.faction.impl.MassiveFaction;
import fr.azefgh456.acore.hook.utils.faction.impl.SaberFaction;
import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.utils.ReflexionUtils;

public class FactionHook extends IHook{

	private AManager manager;
	
	private FactionVersion factionVersion;
	public FactionVersion getFactionVersion() { return this.factionVersion;}
	
	private IFactions<?,?> factionsInstance;
	public IFactions<?,?> getFactionsInstance(){ return this.factionsInstance;}
	
	public FactionHook(AManager manager) {
		this.manager = manager;
	}
	
	@Override
	public IHook hook(PluginManager pm) throws PluginNotFoundException {
		Plugin factionPl = pm.getPlugin(getHookName());
		if(factionPl == null) throw new PluginNotFoundException(this);
		
		//List<String> authors = factionPl.getDescription().getAuthors();
		String mainClass = factionPl.getClass().getSimpleName();
		System.out.println(factionPl.getClass().getSimpleName());
		
		if(mainClass.equals("FactionsPlugin")) {
			if(isSaberVersion()) {
				factionVersion = FactionVersion.SABER;
				factionsInstance = new SaberFaction(manager);
			}else {
				factionVersion = FactionVersion.UUID;
				factionsInstance = new FactionUUID(manager);
			}
			setHooked();
		}else if(mainClass.equals("Factions")){
			factionVersion = FactionVersion.Massive;
			factionsInstance = new MassiveFaction(manager);
			setHooked();
		}
		
		return this;
	}

	private boolean isSaberVersion() {
		try {
			Field chatTagHandled = ReflexionUtils.getField(Class.forName("com.massivecraft.factions.Conf"), "chatTagHandledByAnotherPlugin");
			chatTagHandled.setAccessible(true);
			manager.getPlugin().logWarn("use saber version");
			return true;
		}catch(Exception e) {
			manager.getPlugin().logWarn("ce nest pas saber faction");
			return false;
		}
	}

	@Override
	public String getHookName() {
		return "Factions";
	}
	
	public static enum FactionVersion{ UUID, Massive, SABER;}
	
	/*
		
		public static String getFactionTagWithColor(Player player, Player target) {
			if(!hasFaction(target)) return "§2Wilderness";
			Faction targetFaction = getFaction(target);
			if(!hasFaction(player)) return getFaction(target).getTag();
			
			return ChatColor.YELLOW + getFPayer(target).getChatTag().replace(targetFaction.getTag(), "") + getFaction(player).getColorTo(targetFaction) + targetFaction.getTag();
		}

		public ChatColor getRelationColor(Faction faction, Faction target){
			return faction.getColorTo(target);
		}

		public static Faction getFaction(FLocation floc) {
			return Board.getInstance().getFactionAt(floc);
		}
	*/

}
