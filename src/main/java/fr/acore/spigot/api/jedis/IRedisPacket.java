package fr.acore.spigot.api.jedis;

import com.google.gson.Gson;
import fr.acore.ACoreMain;
import fr.acore.api.json.JsonSerializable;
import fr.acore.api.packet.IPacket;

public interface IRedisPacket extends JsonSerializable, IPacket {

    public static final Gson gson = new Gson();

    public void handle(ACoreMain instance);

    public String getChannel();
}
