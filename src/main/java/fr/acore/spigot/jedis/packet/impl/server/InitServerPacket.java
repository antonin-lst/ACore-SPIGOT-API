package fr.acore.spigot.jedis.packet.impl.server;


import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.jedis.packet.RedisPacket;

public class InitServerPacket extends RedisPacket {

    private String serverName;

    public InitServerPacket(){}

    public InitServerPacket(String serverName){
        this.serverName = serverName;
    }

    @Override
    public void handle(ACoreSpigotAPI instance) {
        instance.setAcoreMainPresence();
    }

    @Override
    public int getId() {
        return 2;
    }
}
