package fr.acore.spigot.api.jedis;

public interface IJedisPublisher extends Runnable {

    public void write(IRedisPacket packetToJson);

}
