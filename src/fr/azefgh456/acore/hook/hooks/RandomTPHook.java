package fr.azefgh456.acore.hook.hooks;

import org.bukkit.plugin.PluginManager;

import fr.azefgh456.acore.hook.IHook;
import fr.azefgh456.acore.hook.exception.PluginNotFoundException;
import randomtp.whoktor.RandomTP;
import randomtp.whoktor.other.RandomTPAPI;

public class RandomTPHook extends IHook{

	private RandomTPAPI randomTP;
	public RandomTPAPI getRandomTP() { return this.randomTP;}
	
	public RandomTPHook() {
		randomTP = null;
	}
	
	@Override
	public IHook hook(PluginManager pm) throws PluginNotFoundException {
		if(pm.getPlugin(getHookName()) == null) throw new PluginNotFoundException(this);
		
		randomTP = RandomTP.getAPI();
		if(randomTP != null ) {
			setHooked();
			return this;
		}
		
		return null;
	}

	@Override
	public String getHookName() {
		return "RandomTP";
	}

}
