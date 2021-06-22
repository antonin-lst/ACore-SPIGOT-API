package fr.azefgh456.acore.jedis.config;

public class JedisConfig {
	
	private String jedisHost;
	public String getJedisHost() { return this.jedisHost;}
	private int jedisPort;
	public int getJedisPort() { return this.jedisPort;}
	private String jedisPassword;
	public String getJedisPassword() { return this.jedisPassword;}
	
	public JedisConfig(String jedisHost, int jedisPort, String jedisPassword) {
		this.jedisHost = jedisHost;
		this.jedisPort = jedisPort;
		this.jedisPassword = jedisPassword;
	}



	public boolean hasPassword() {
		return jedisPassword != null && !jedisPassword.isEmpty();
	}
	
}
