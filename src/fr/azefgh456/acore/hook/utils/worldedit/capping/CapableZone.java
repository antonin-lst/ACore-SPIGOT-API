package fr.azefgh456.acore.hook.utils.worldedit.capping;

import java.util.List;

import fr.azefgh456.acore.event.cap.CapableZoneWinEvent;
import fr.azefgh456.acore.hook.utils.worldedit.WSelectionHook;
import fr.azefgh456.acore.manager.AManager;
import fr.azefgh456.acore.runnable.ARunnable;
import fr.azefgh456.acore.utils.time.TimerBuilder;

public abstract class CapableZone<T> implements ARunnable{

	protected AManager manager;
	protected WSelectionHook capZone;
	protected long capDelay;
	private TimerBuilder capTimer;
	
	private Capper<T> capper;
	
	//Constructeur pour une zone capturable
	
	public CapableZone(AManager manager, WSelectionHook capZone, long capDelay) {
		this.manager = manager;
		this.capZone = capZone;
		this.capDelay = capDelay;
		manager.registerSyncRunnable(this);
	}
	
	
	/*
	 * Ticks toute les 1 seconde
	 * Main method for the cap
	 */
	
	
	@Override
	public void ticks() {
		if(!zoneIsCapable()) return;
		
		List<Capper<T>> cappers = getCapperInZone();
		
		if(capper == null) {
			
			setCapper(cappers);
			
		}else {
			
			if(cappers.isEmpty() || !cappers.contains(capper)) {
				resetCapping();
				return;
			}
			
			if(capTimer == null) resetEndTimer();
			
			//fin de method a re voir avec un eventHandler
			if(capTimer.hisFished()) {
				
				resetCapping();
			}
			
		}
	}
	
	/*
	 * Methods Utils
	 * 
	 */
	
	//Cette methode peut etre redefinit pour modifier le systeme de cap
	
	public void setCapper(List<Capper<T>> cappers) {
		if(capTimer != null) capTimer = null;
		
		if(!cappers.isEmpty()) {
			Capper<T> fc = getRandomCapper(cappers);
			
			if(capperCanCap(fc)) {
				capper = fc;
				resetEndTimer();
				capper.sendMessage("Tu cap frr");
			}
		}
	}
	
	public boolean zoneIsCapable() {
		return capZone != null;
	}
	
	//Method random
	public Capper<T> getRandomCapper(List<Capper<T>> cappers){
		if(cappers.isEmpty()) return null;
		double random = Math.random();
		double cap = 0 + (cappers.size() - 0) * random;
		return cappers.get((int)cap);
	}
	
	//reset le timer de cap
	public void resetEndTimer() { 
		capTimer = new TimerBuilder(System.currentTimeMillis(), capDelay);
	}
	
	//reset du cap en cour
	public void resetCapping() {
		capper = null;
		capTimer = null;
	}
	
	public void win(Capper<T> winner) {
		manager.callEvent(new CapableZoneWinEvent<T, CapableZone<T>>(winner, this));
	}
	
	
	/*
	 * Abstract Method
	 * 
	 */
	
	public abstract boolean capperCanCap(Capper<T> capper);
	public abstract List<Capper<T>> getCapperInZone();
	
	
	//Getter
	public WSelectionHook getCapZone() {
		return this.capZone;
	}
	
	public long getCapDelay() {
		return this.capDelay;
	}
	
	public Capper<T> getCapper(){
		return this.capper;
	}
	
	//Setter
	public void setCapZone(WSelectionHook capZone) {
		this.capZone = capZone;
	}

}
