package fr.acore.spigot.jedis.packet.impl.server;


import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.jedis.packet.RedisPacket;

public class StopServerPacket extends RedisPacket {

    public StopServerPacket(){}

    @Override
    public void handle(ACoreSpigotAPI instance) {
        instance.getServer().shutdown();
    }

    @Override
    public int getId() {
        return 3;
    }
}
