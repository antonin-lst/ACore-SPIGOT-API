package fr.acore.spigot.jedis.channel;

public enum JedisChannelHandling {

    ACORE_MAIN("acore-main"), ACORE_BUNGEECORD("acore-bungeecord"), ACORE_INFORMATIONS("acore-infos");

    private String channel;

    JedisChannelHandling(String channel) {
        this.channel = channel;
    }

    public String getChannel() {
        return this.channel;
    }

}
