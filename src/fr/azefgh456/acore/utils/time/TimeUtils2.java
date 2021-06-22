package fr.azefgh456.acore.utils.time;

import java.util.concurrent.TimeUnit;

public class TimeUtils2 {

	public String timeToStringFromMiliSecondes(long ms) {
		return timeToStringFromSecondes(ms / 1000);
	}
	
	public String timeToStringFromSecondes(long secondes) {
		StringBuilder builder = new StringBuilder("");
		
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
	
	
	private int getMinutes(long secondes) {
		return (int) TimeUnit.SECONDS.toMinutes(secondes);
	}
	
	private int getHours(long secondes) {
		return (int) TimeUnit.SECONDS.toHours(secondes);
	}
	
	private int getDays(long secondes) {
		return (int) TimeUnit.SECONDS.toDays(secondes);
	}
	/*
	private int getWeeks(long secondes) {
		return (int) TimeUnit.SECONDS.toDays(secondes) / 7;
	}
	
	private int getMonths(long secondes) {
		return 0;
	}
	
	private int getYers(long secondes) {
		return 0;
	}*/
}
