package fr.acore.spigot.jedis.packet.impl.queue;


import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.jedis.packet.RedisPacket;

public class RemovePlayerToServerQueuePacket extends RedisPacket {

    private String playerUuid;
    private String serverName;

    public RemovePlayerToServerQueuePacket(){}

    public RemovePlayerToServerQueuePacket(String playerUuid, String serverName){
        this.playerUuid = playerUuid;
        this.serverName = serverName;
    }

    @Override
    public void handle(ACoreSpigotAPI instance) {

    }

    @Override
    public int getId() {
        return 9;
    }
}
