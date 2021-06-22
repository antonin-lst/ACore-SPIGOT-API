package fr.azefgh456.acore.hook.utils.faction.impl.player;

import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.struct.Role;

import fr.azefgh456.acore.hook.exception.UnsuportedVersionException;
import fr.azefgh456.acore.hook.utils.faction.IFaction;
import fr.azefgh456.acore.hook.utils.faction.IPlayer;
import fr.azefgh456.acore.hook.utils.faction.impl.faction.SFaction;
import fr.azefgh456.acore.manager.AManager;

public class SPlayer implements IPlayer<Faction, FPlayer>{

	private AManager manager;
	private FPlayer player;
	
	public SPlayer(AManager manager, FPlayer player) {
		this.manager = manager;
		this.player = player;
	}
	

	@Override
	public IFaction<Faction, FPlayer> getIFaction() {
		return new SFaction(manager, player.getFaction());
	}

	@Override
	public double getPower() {
		return player.getPower();
	}

	@Override
	public double getBalance() {
		return manager.getOfflinePlayer(player.getPlayer()).getBalance();
	}

	@Override
	public int getKills() throws UnsuportedVersionException {
		return player.getKills();
	}

	@Override
	public int getDeaths() throws UnsuportedVersionException {
		return player.getDeaths();
	}
	
	@Override
	public String getChatTag(IPlayer<Faction, FPlayer> target) {
		return player.getChatTag(target.getFPlayer());
	}
	
	@Override
	public FPlayer getFPlayer() {
		return player;
	}

	@Override
	public Player getPlayer() {
		return player.getPlayer();
	}

	@Override
	public String getFactionGrade() {
		try {
			//System.out.println("getAnotedSuperClassMethod : " + player.getClass().getSuperclass().getMethods().length);
			
			/*for(Method m : player.getClass().getSuperclass().getMethods()) {
				System.out.println(m.getName());
			}
			System.out.println("getAnnotedInterface : " + player.getClass().getAnnotatedInterfaces().length);
			System.out.println("getInterface : " + player.getClass().getInterfaces().length);
			for(Class<?> inter : player.getClass().getInterfaces()) {
				for(Method m : inter.getMethods()) {
					System.out.println(inter.getName() + " " + m.getName());
				}
			}
			
			for(Method m : player.getClass().getDeclaredMethods()) {
				System.out.println(m.getName());
				
				System.out.println("-----");
			}
			for(Method m : player.getClass().getSuperclass().getMethods()) {
				if(m.getName().equals("getRole")) {
					Role r = (Role) m.invoke(player);
					return r.getPrefix();
				}
			}*/
			Method m = player.getClass().getSuperclass().getMethod("getRole");
			Role r = (Role) m.invoke(player);
			return r.getPrefix();
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
		//return "";
	}

}
