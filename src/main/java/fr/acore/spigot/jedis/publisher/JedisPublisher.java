package fr.acore.spigot.jedis.publisher;


import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.jedis.IJedisPublisher;
import fr.acore.spigot.api.jedis.IRedisPacket;
import fr.acore.spigot.config.utils.Conf;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.LinkedList;
import java.util.Queue;

public class JedisPublisher implements IJedisPublisher {

    private ACoreSpigotAPI instance;

    private boolean running;

    private Jedis jedis;
    private Queue<IRedisPacket> packets;

    public JedisPublisher(ACoreSpigotAPI instance){
        this.instance = instance;
        packets = new LinkedList<>();
        this.running = false;
        enable();
    }

    private void enable() {
        try{
            jedis = new JedisPool(Conf.getJedisHost(), Conf.getJedisPort()).getResource();
        }catch(Exception ex){
            instance.logErr("Impossible de se connecter a Redis");
        }
    }


    @Override
    public void write(IRedisPacket packet) {
        packets.add(packet);
    }

    @Override
    public void run() {
        this.running = true;
        while(isRunning()){
            try{
                if(!Conf.getJedisPassword().isEmpty()){
                    jedis.auth(Conf.getJedisPassword());
                }

                while (!packets.isEmpty()) {
                    IRedisPacket packet = packets.poll();

                    //System.out.println(queue.getChannel().getChannel());
                    try {
                        //System.out.println("sending : " + packet.toJson());
                        jedis.publish(packet.getChannel(), packet.toJson());
                    }catch(Exception e) {
                        System.out.println(e.getMessage());
                    }

                }
                Thread.sleep(30);
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
