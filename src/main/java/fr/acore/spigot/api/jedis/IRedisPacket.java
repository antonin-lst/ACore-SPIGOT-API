package fr.acore.spigot.api.jedis;

import com.google.gson.Gson;
import fr.acore.spigot.ACoreSpigotAPI;
import fr.acore.spigot.api.json.JsonSerializable;
import fr.acore.spigot.api.packet.IPacket;

public interface IRedisPacket extends JsonSerializable, IPacket {

    public static final Gson gson = new Gson();

    public void handle(ACoreSpigotAPI instance);

    public String getChannel();
}
