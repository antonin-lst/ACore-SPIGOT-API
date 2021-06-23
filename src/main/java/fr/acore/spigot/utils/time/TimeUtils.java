package fr.acore.spigot.utils.time;

import java.util.concurrent.TimeUnit;

public interface TimeUtils {

	public default String timeToStringFromMiliSecondes(long ms) {
		return timeToStringFromSecondes(ms / 1000);
	}
	
	public default String timeToStringFromSecondes(long secondes) {
		StringBuilder builder = new StringBuilder("");
		
		int weeks = getWeeks(secondes);
		if(weeks > 0) {
			builder.append(weeks).append("w ");
			secondes -= weeks * 86400 * 7;
		}
		
		int days = getDays(secondes);
		if(days > 0) {
			builder.append(days).append("d ");
			secondes -= days * 86400;
		}
		
		int hours = getHours(secondes);
		if(hours > 0) {
			builder.append(hours).append("h ");
			secondes -= hours * 3600;
		}
		
		int minutes = getMinutes(secondes);
		if(minutes > 0) {
			builder.append(minutes).append("m ");
			secondes -= minutes * 60;
		}
		
		if(secondes > 0) {
			builder.append(secondes).append("s");
		}
		return builder.toString();
	}
	
	
	public default int getMinutes(long secondes) {
		return (int) TimeUnit.SECONDS.toMinutes(secondes);
	}
	
	public default int getHours(long secondes) {
		return (int) TimeUnit.SECONDS.toHours(secondes);
	}
	
	public default int getDays(long secondes) {
		return (int) TimeUnit.SECONDS.toDays(secondes);
	}
	
	public default int getWeeks(long secondes) {
		return (int) TimeUnit.SECONDS.toDays(secondes) / 7;
	}
	/*
	private int getMonths(long secondes) {
		return 0;
	}
	
	private int getYers(long secondes) {
		return 0;
	}*/
}
