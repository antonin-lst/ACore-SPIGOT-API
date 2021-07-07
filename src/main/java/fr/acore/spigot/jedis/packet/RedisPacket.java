package fr.acore.spigot.jedis.packet;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.acore.spigot.api.jedis.IRedisPacket;

import java.lang.reflect.Field;

public abstract class RedisPacket implements IRedisPacket {

    private String channel;

    public RedisPacket(String channel){
        this.channel = channel;
    }


    @Override
    public String toJson() {
        JsonObject serializedPacket = new JsonObject();

        String serializedClazz = gson.toJson(this);
        JsonObject jsonPacketData = JsonParser.parseString(serializedClazz).getAsJsonObject();

        serializedPacket.addProperty("id", getId());

        serializedPacket.add("datas", jsonPacketData);

        return serializedPacket.toString();
    }

    @Override
    public void fromJson(JsonObject datas) {
        for(Field f : getClass().getDeclaredFields()){
            JsonElement currentElement = datas.get(f.getName());
            if(!currentElement.isJsonNull()){
                try {
                    Object value = null;
                    if(currentElement.getAsJsonPrimitive().isString()){
                        value = currentElement.getAsString();
                    }else if(currentElement.getAsJsonPrimitive().isBoolean()){
                        value = currentElement.getAsBoolean();
                    }else if(currentElement.getAsJsonPrimitive().isNumber()){
                        if(f.getType().equals(int.class)){
                            value = currentElement.getAsInt();
                        }else if(f.getType().equals(double.class)){
                            value = currentElement.getAsDouble();
                        }else{
                            value = currentElement.getAsFloat();
                        }
                    }
                    if(value == null) continue;
                    f.setAccessible(true);
                    f.set(this, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getChannel() {
        return channel;
    }
}
