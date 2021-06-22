package fr.azefgh456.acore.hook.utils.faction.impl.faction.stats;

public class MassiveFactionStats {
	
	private int tuers;
	private int morts;
	
	public MassiveFactionStats(int tuers, int morts) {
		this.tuers = tuers;
		this.morts = morts;
	}
	
	public int getTuers() {
		return this.tuers;
	}
	
	public int getMorts() {
		return this.morts;
	}

	public void addKills() {
		this.tuers++;
	}
	
	public void addDeaths() {
		this.morts++;
	}

}
