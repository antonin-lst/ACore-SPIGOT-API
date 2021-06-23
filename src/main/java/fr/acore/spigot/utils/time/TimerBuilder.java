package fr.acore.spigot.utils.time;

import fr.acore.spigot.api.timer.ITimer;

public class TimerBuilder implements ITimer {
	
	private long current;
	private long delay;
	
	public TimerBuilder(long delay){
		this(System.currentTimeMillis(), delay);
	}

	public TimerBuilder(long current, long delay) {
		this.current = current;
		this.delay = delay;
	}
	
	@Override
	public long getCurrent() {
		return current;
	}
	
	@Override
	public void setCurrent(long current) {
		this.current = current;
	}

	@Override
	public long getDelay() {
		return delay;
	}
	
	@Override
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	@Override
	public long getRemainingTime() {
		return (current + delay) - System.currentTimeMillis();
	}

	@Override
	public String getFromatedRemainingTime() {
		return timeToStringFromMiliSecondes(getRemainingTime());
	}
	
	@Override
	public boolean isFinish() {
		return getRemainingTime() <= 0;
	}

}
