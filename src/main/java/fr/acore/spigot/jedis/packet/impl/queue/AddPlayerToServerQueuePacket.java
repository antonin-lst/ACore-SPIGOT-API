package fr.acore.spigot.jedis.packet.impl.queue;


import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.jedis.packet.RedisPacket;

public class AddPlayerToServerQueuePacket extends RedisPacket {

    private String playerUuid;
    private String serverName;

    public AddPlayerToServerQueuePacket(){}

    public AddPlayerToServerQueuePacket(String playerUuid, String serverName){
        this.playerUuid = playerUuid;
        this.serverName = serverName;
    }

    @Override
    public void handle(ACoreSpigotAPI instance) {
    }

    @Override
    public int getId() {
        return 8;
    }
}
