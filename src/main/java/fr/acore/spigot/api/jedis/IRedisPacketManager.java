package fr.acore.spigot.api.jedis;

import com.google.gson.JsonObject;
import fr.acore.ACoreMain;
import fr.acore.api.packet.IPacketFactory;
import fr.acore.api.packet.IPacketManager;
import fr.acore.jedis.packet.factory.RedisPacketFactory;
import fr.acore.jedis.publisher.JedisPublisher;
import redis.clients.jedis.JedisPubSub;

public abstract class IRedisPacketManager extends JedisPubSub implements IPacketManager<IRedisPacket, JsonObject> {

    protected IJedisPublisher jedisPublisher;
    protected ACoreMain instance;
    private final IPacketFactory<IRedisPacket> packetFactory;

    public IRedisPacketManager(ACoreMain instance){
        this.instance = instance;
        jedisPublisher = new JedisPublisher(instance);
        packetFactory = new RedisPacketFactory();
    }

    public void init(){

    }

    public abstract void disable();

    @Override
    public IPacketFactory<IRedisPacket> getPacketFactory() {
        return packetFactory;
    }
}
