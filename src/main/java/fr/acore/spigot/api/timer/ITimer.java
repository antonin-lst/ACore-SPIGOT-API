package fr.acore.spigot.api.timer;

import fr.acore.spigot.utils.time.TimeUtils;

public interface ITimer extends TimeUtils{
	
	public long getCurrent();
	public void setCurrent(long current);
	public long getDelay();
	public void setDelay(long delay);
	public long getRemainingTime();
	public String getFromatedRemainingTime();
	public boolean isFinish();

}
