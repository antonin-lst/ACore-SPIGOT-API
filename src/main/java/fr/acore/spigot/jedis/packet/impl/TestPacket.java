package fr.acore.spigot.jedis.packet.impl;


import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.jedis.packet.RedisPacket;

public class TestPacket extends RedisPacket {

    private String test;

    private int test2;

    public TestPacket(String channel){
        super(channel);
        this.test = ACoreSpigotAPI.getInstance().getServerName();
        this.test2 = 188;
    }

    @Override
        public void handle(ACoreSpigotAPI instance) {
        System.out.println("handle TestPacket");
    }


    @Override
    public int getId() {
        return 1;
    }
}
