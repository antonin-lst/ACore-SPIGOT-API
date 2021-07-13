package fr.acore.spigot.jedis.packet.impl.server;

import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.jedis.manager.RedisManager;
import fr.acore.spigot.jedis.packet.RedisPacket;

public class UpdateServerPacket extends RedisPacket {

    private String serverName;

    public UpdateServerPacket(){}

    public UpdateServerPacket(String serverName){
        this.serverName = serverName;
    }

    @Override
    public void handle(ACoreSpigotAPI instance) {
        instance.getInternalManager(RedisManager.class).sendPacket(new UpdateServerPacket(instance.getServerName()));
    }

    @Override
    public int getId() {
        return 4;
    }
}
