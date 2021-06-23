package fr.acore.spigot.utils.time;

import java.text.DateFormat;
import java.util.Date;

public class DateUtils {
	
	public static Jours getJours() {
		Date aujourdhui = new Date();
	    DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
	    String date = shortDateFormat.format(aujourdhui);
	    String data[] = date.split(" ");
	    return Jours.getJoursFromString(data[0]);
	}
	
	public static Heure getHeure() {
		Date aujourdhui = new Date();
	    DateFormat mediumDateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
	    String date = mediumDateFormat.format(aujourdhui);
	    String data[] = date.split(" ");
	    
	    return new Heure(data[3]);
	}
	
	public static <T> T getMostRessentHour(ADate primary, ADate first, ADate seconde, T obj, T obj2) {
		if(first.getInternalHeure().getHours() != seconde.getInternalHeure().getHours()) {
			if(primary.getInternalHeure().getHours() < first.getInternalHeure().getHours() && first.getInternalHeure().getHours() < seconde.getInternalHeure().getHours()) return obj;
			
			return obj2;
			
		}else {
			
			if(first.getInternalHeure().getMinutes() != seconde.getInternalHeure().getMinutes()) {
				if(primary.getInternalHeure().getMinutes() < first.getInternalHeure().getMinutes() && first.getInternalHeure().getMinutes() < seconde.getInternalHeure().getMinutes()) return obj;
				
				return obj2;
			}else {
				if(first.getInternalHeure().getSecondes() != seconde.getInternalHeure().getSecondes()) {
					if(primary.getInternalHeure().getSecondes() < first.getInternalHeure().getSecondes() && first.getInternalHeure().getSecondes() < seconde.getInternalHeure().getSecondes()) return obj;
				
					return obj2;
				}else {
					
					return obj;
				}
			}
			
			
		}
	}
	
	public static ADate getDate() {
		Heure heure = getHeure();
		
		Jours jour = getJours();
		return new ADate(heure, jour);
	}
	
	
	

	
	public static class ADate{
		
		private Heure heure;
		private Jours jour;
		
		public ADate(Heure heure, Jours jour) {
			this.heure = heure;
			this.jour = jour;
		}
		
		public boolean hisTimeIsPassed(ADate date) {
			//System.out.println("jourcheck : " + String.valueOf(jour.ordinal() > date.getInternaleJour().ordinal()));
			if(jour.ordinal() > date.getInternaleJour().ordinal()) return true;
			
			if(jour.equals(date.getInternaleJour())) {
				//System.out.println("heurecheck : " + String.valueOf(heure.isTimeIsPassed(date.getInternalHeure())));
				return heure.isTimeIsPassed(date.getInternalHeure());
			}
			return false;
		}
		
		public boolean hisTimeIsEqualsOrPassed(ADate date) {
			
			if(jour.ordinal() > date.getInternaleJour().ordinal()) return true;
			
			if(jour.equals(date.getInternaleJour())) {
				return heure.isTimeEqualsOrPassed(date.getInternalHeure());
			}
			
			return false;
		}
		
		public boolean hisEqualsDay(ADate date) {
			return hisEqualsDay(date.getInternaleJour());
		}
		
		public boolean hisEqualsDay(Jours jour) {
			return this.jour.getIndex() == jour.getIndex();
		}
		
		public Heure getInternalHeure() {
			return this.heure;
		}
		
		public Jours getInternaleJour() {
			return this.jour;
		}
		
		@Override
		public boolean equals(Object o) {
			if(o instanceof ADate) {
				ADate other = (ADate)o;
				if(heure.equals(other.getInternalHeure()) && jour.equals(other.getInternaleJour())) return true;
			}
			return false;
		}
		
		@Override
		public String toString() {
			return jour.getRegex() + ":" + heure.getRegex();
		}
		
	}
	
	public static class Heure {
		
		private String regex;
		
		public Heure(String regex) {
			this.regex = regex;
		}
		
		public boolean isTimeIsPassed(Heure time2) {
			//System.out.println("hourscheck : " + String.valueOf(getHours() > time2.getHours()));
			if(getHours() > time2.getHours()) return true;
			
			if(getHours() == time2.getHours()) {
				//System.out.println("minutecheck : " + String.valueOf(getMinutes() > time2.getMinutes()));
				if(getMinutes() > time2.getMinutes()) return true;
				
				if(getMinutes() == time2.getMinutes()) {
					//System.out.println("secondecheck : " + String.valueOf(getSecondes() > time2.getSecondes()));
					if(getSecondes() > time2.getSecondes()) return true;
					
				}
			}
			return false;
		}
		
		public boolean isTimeEqualsOrPassed(Heure time2) {
			//return getHours() < time2.getHours() ? true : getHours() == time2.getHours() ? getMinutes() < time2.getMinutes() ? true : getMinutes() == time2.getMinutes() ? getSecondes() <= time2.getSecondes() ? true : false : false : false;
			
			if(getHours() > time2.getHours()) return true;
			
			if(getHours() == time2.getHours()) {
				//System.out.println("minutecheck : " + String.valueOf(getMinutes() > time2.getMinutes()));
				if(getMinutes() > time2.getMinutes()) return true;
				
				if(getMinutes() == time2.getMinutes()) {
					//System.out.println("secondecheck : " + String.valueOf(getSecondes() > time2.getSecondes()));
					if(getSecondes() >= time2.getSecondes()) return true;
					
				}
				
			}
			
			return false;
		}
		
		public int getSecondes() {
			String data[] = regex.split(":");
			return Integer.parseInt(data[2]);
		}
		
		public int getMinutes() {
			String data[] = regex.split(":");
			return Integer.parseInt(data[1]);
		}
		
		public int getHours() {
			String data[] = regex.split(":");
			return Integer.parseInt(data[0]);
		}
		
		public String getRegex() {
			return this.regex;
		}
		
		@Override
		public boolean equals(Object obj) {

			if(obj instanceof Heure) {
				Heure other = (Heure) obj;
				if(regex.equals(other.getRegex())) { return true;}
			}
			
			return false;
		}
		
	}
	
	public static enum Jours {
		
		LUNDI(1, "lundi"),
		MARDI(2, "mardi"),
		MERCREDI(3, "mercredi"),
		JEUDI(4, "jeudi"),
		VENDREDI(5, "vendredi"),
		SAMEDI(6, "samedi"),
		DIMANCHE(7, "dimanche");
		
		private int index;
		private String regex;
		
		Jours(int index, String regex) {
			this.index = index;
			this.regex = regex;
		}
		
		public static Jours getJoursFromString(String regex) {
			for(Jours j : Jours.values()) {
				if(j.getRegex().equals(regex.toLowerCase())) return j;
			}
			return null;
		}
		
		public boolean equals(Jours jours) {
			if(jours == null) return false;
			
			if(jours.getIndex() == this.index) return true;
			
			return false;
		}
		
		public int getIndex() {
			return this.index;
		}
		
		public String getRegex() {
			return this.regex;
		}
	}

}