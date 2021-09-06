package fr.acore.spigot.jedis.packet.impl.server;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.jedis.packet.RedisPacket;

public class InfoServerPacket extends RedisPacket {



    @Override
    public void handle(ACoreSpigotAPI instance) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
