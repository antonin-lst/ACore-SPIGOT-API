package fr.azefgh456.acore.hook.utils.worldedit.capping;

import org.bukkit.entity.Player;

import fr.azefgh456.acore.hook.utils.faction.IFaction;
import fr.azefgh456.acore.player.players.CorePlayer;

public abstract class Capper<T> {

	private T capper;
	public T getCapper() { return this.capper;}
	
	public Capper(T capper) {
		this.capper = capper;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Capper<?>) {
			Capper<?> target = (Capper<?>) obj;
			if(target.getCapper().equals(capper)) return true;
		}
		return false;
	}

	public abstract void sendMessage(String message);
	
	
	
	public static class PlayerCapper extends Capper<Player>{

		public PlayerCapper(Player capper) {
			super(capper);
		}

		@Override
		public void sendMessage(String message) {
			getCapper().sendMessage(message);
		}
	}
	
	
	public static class CorePlayerCapper extends Capper<CorePlayer>{

		public CorePlayerCapper(CorePlayer capper) {
			super(capper);
		}

		@Override
		public void sendMessage(String message) {
			getCapper().sendMessage(message);
		}
	}
	
	
	public static class FactionCapper extends Capper<IFaction<?,?>>{
		
		public FactionCapper(IFaction<?,?> faction) {
			super(faction);
		}
		
		@Override
		public void sendMessage(String message) {
			getCapper().sendMessage(message);
		}
	}
	
}
