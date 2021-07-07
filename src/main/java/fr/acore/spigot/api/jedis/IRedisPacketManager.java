package fr.acore.spigot.api.jedis;

import com.google.gson.JsonObject;
import fr.acore.spigot.api.packet.IPacketManager;

public interface IRedisPacketManager extends IPacketManager<IRedisPacket, JsonObject> {


    public void disable();

    public IJedisPublisher getPublisher();

}
