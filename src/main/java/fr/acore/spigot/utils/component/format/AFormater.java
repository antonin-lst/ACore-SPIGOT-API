package fr.acore.spigot.utils.component.format;

import java.util.List;

import org.bukkit.entity.Player;

import fr.acore.spigot.utils.component.action.IActionReplacer;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class AFormater<T extends IActionReplacer, U extends AFormat<T>> {

	protected T manager;
	private List<U> formats;
	public List<U> getFormats(){ return this.formats;}
	
	public AFormater(T manager, List<U> formats) {
		this.manager = manager;
		this.formats = formats;
	}
	
	protected U getFormat(int place) {
		for(U format : formats) {
			if(format.getPlace() == place) {
				return format;
			}
		}
		return null;
	}
	
	public abstract TextComponent format(Player player, Player target, Object... datas);
	
	
}
