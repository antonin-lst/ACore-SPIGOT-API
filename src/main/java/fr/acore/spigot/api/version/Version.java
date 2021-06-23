package fr.acore.spigot.api.version;

public class Version {

	public int major;

	public int minor;

	public int patch;

	public Version(int major, int minor, int patch) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}

	public boolean isOlder(Version compare) {
		if (compare.major < this.major)
			return true;
		if (compare.minor < this.minor)
			return true;
		if (compare.patch < this.patch)
			return true;
		return false;
	}

	public boolean isEqual(Version compare) {
		return (compare.getMajor() == this.major && compare.getMinor() == this.minor
				&& compare.getPatch() == this.patch);
	}

	public boolean isNewer(Version compare) {
		if (compare.major > this.major)
			return true;
		if (compare.minor > this.minor)
			return true;
		if (compare.patch > this.patch)
			return true;
		return false;
	}

	public int getMajor() {
		return this.major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return this.minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getPatch() {
		return this.patch;
	}

	public void setPatch(int patch) {
		this.patch = patch;
	}

	public String toString() {
		return "Version [" + getVersion() + "]";
	}

	public String getVersion() {
		return String.valueOf(this.major) + "." + this.minor + "." + this.patch;
	}
	
	public static Version fromString(String sversion) throws ParseVersionException{
		try {
			int major = Integer.parseInt(sversion.substring(0, 1));
			int minor = Integer.parseInt(sversion.substring(2, 3));
			int patch = Integer.parseInt(sversion.substring(4, 5));
			return new Version(major, minor, patch);
			
		}catch(Exception e) {
			throw new ParseVersionException();
		}
	}
	
	public static class ParseVersionException extends Exception{

		private static final long serialVersionUID = 6986487554420433168L;
		
		@Override
		public String getMessage() {
			return "Model : 1.0.0 (major,minor,patch)";
		}
		
	}
}
