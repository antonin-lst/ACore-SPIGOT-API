package fr.azefgh456.acore.utils.component.format;

import java.util.List;

import org.bukkit.entity.Player;

import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.utils.component.EasyComponent;
import fr.azefgh456.acore.utils.component.action.IAction;

public abstract class AFormat<T extends AManager> {

	private int place;
	private String format;
	private List<IAction<T>> actions;
	
	public AFormat(int place, String format, List<IAction<T>> actions) {
		this.place = place;
		this.format = format;
		this.actions = actions;
	}
	
	public int getPlace() {
		return this.place;
	}
	public String getFormat() {
		return this.format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public List<IAction<T>> getActions(){
		return this.actions;
	}
	
	public EasyComponent build(T manager, Player player, Player target, Object... datas) {
		EasyComponent component = new EasyComponent();
		component.setText(manager.replaceAll(format, player, target, datas));
		
		for(IAction<T> action : actions) {
			System.out.println("AFormat debug : " + datas.length);
			component = action.transform(manager, player, target, component, datas);
		}
		return component;
	}
	
}
