package fr.acore.spigot.jedis.packet.factory;


import fr.acore.spigot.api.jedis.IRedisPacket;
import fr.acore.spigot.api.packet.IPacketFactory;

import java.util.HashMap;
import java.util.Map;

public class RedisPacketFactory implements IPacketFactory<IRedisPacket> {

    private Map<Integer, Class<?>> packets;

    public RedisPacketFactory(){
        this.packets = new HashMap<>();
    }

    @Override
    public Class<IRedisPacket> getPacketClazz(int id) {
        for(Map.Entry<Integer, Class<?>> entry : packets.entrySet()){
            if(entry.getKey().equals(id)) return (Class<IRedisPacket>) entry.getValue();
        }
        return null;
    }

    @Override
    public <U extends IRedisPacket> void addPacket(int packetId, Class<U> clazz) {
        this.packets.put(packetId, clazz);
    }
}
