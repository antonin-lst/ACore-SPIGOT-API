package fr.acore.spigot.hook.hooks;

import java.lang.reflect.Field;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import fr.acore.spigot.api.faction.IFactionManager;
import fr.acore.spigot.api.hook.IHook;
import fr.acore.spigot.api.hook.exception.HookFailException;
import fr.acore.spigot.faction.FactionUUIDManager;
import fr.acore.spigot.faction.MassiveFactionManager;
import fr.acore.spigot.faction.SaberFactionManager;
import fr.acore.spigot.hook.impl.PluginHook;
import fr.acore.spigot.utils.ReflexionUtils;

public class FactionHook extends PluginHook<Plugin> {

	private IFactionManager factionManager;
	public IFactionManager getFactionManager() { return this.factionManager;}
	
	public FactionHook(PluginManager hooker) {
		super(hooker, "Factions");
	}
	
	@Override
	public IHook<PluginManager> hook() throws HookFailException {
		super.hook();
		
		if(isHooked()) {
			String factionMainClazz = hook.getClass().getSimpleName();
			if(factionMainClazz.equals("FactionsPlugin")) {
				if(isSaberVersion()) {
					//factionManager = new SaberFactionManager();
				}else {
					//factionManager = new FactionUUIDManager();
				}
			}else if(factionMainClazz.equals("Factions")){
				factionManager = new MassiveFactionManager();
			}else {
				setHooked(false);
			}
		}
		
		return this;
	}
	
	private boolean isSaberVersion() {
		try {
			Field chatTagHandled = ReflexionUtils.getField(Class.forName("com.massivecraft.factions.Conf"), "chatTagHandledByAnotherPlugin");
			chatTagHandled.setAccessible(true);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
}
