package fr.azefgh456.acore.nms.utils;

import org.bukkit.Bukkit;

public class ServerVersion {
	
	private static Version version;
	
	public static Version getVersion() {
		if(version != null) return version;
		
		return setupVersion();
	}
	
	private static Version setupVersion() {
		return Version.valueOf(Bukkit.getServer().getClass().getPackage().getName().substring(23));
	}
	
	public static enum Version{	
		
		v1_7_R4,
		v1_8_R1,
		v1_8_R2,
		v1_8_R3,
		v1_9_R1,
		v1_9_R2,
		v1_10_R1,
		v1_11_R1,
		v1_12_R1,
		v1_13_R1,
		v1_13_R2,
		v1_14_R1,
		v1_14_R2,
		v1_15_R1,
		v1_15_R2,
		v1_16_R1,
		v1_16_R2,
		v1_17_R1,
		v1_17_R2;
		
		private Integer value; 
		private String shortVersion;
		
		Version() {
			this.value = Integer.valueOf(name().replaceAll("[^\\d.]", ""));
		    this.shortVersion = name().substring(0, name().length() - 3);
		}
		
		 public Integer getValue() { return this.value; }		    
		 public String getShortVersion() { return this.shortVersion; }
		 
		 
		 public boolean isLower(Version version) { return (getValue().intValue() < version.getValue().intValue()); }


		    
		    public boolean isHigher(Version version) { return (getValue().intValue() > version.getValue().intValue()); }


		    
		    public boolean isEqual(Version version) { return getValue().equals(version.getValue()); }


		    
		    public boolean isEqualOrLower(Version version) { return (getValue().intValue() <= version.getValue().intValue()); }


		    
		    public boolean isEqualOrHigher(Version version) { return (getValue().intValue() >= version.getValue().intValue()); }


		    
		    public static boolean isCurrentEqualOrHigher(Version v) { return (getVersion().getValue().intValue() >= v.getValue().intValue()); }


		    
		    public static boolean isCurrentHigher(Version v) { return (getVersion().getValue().intValue() > v.getValue().intValue()); }


		    
		    public static boolean isCurrentLower(Version v) { return (getVersion().getValue().intValue() < v.getValue().intValue()); }


		    
		    public static boolean isCurrentEqualOrLower(Version v) { return (getVersion().getValue().intValue() <= v.getValue().intValue()); }


		    
		    public static boolean isCurrentEqual(Version v) { return getVersion().getValue().equals(v.getValue()); }
	}

}
