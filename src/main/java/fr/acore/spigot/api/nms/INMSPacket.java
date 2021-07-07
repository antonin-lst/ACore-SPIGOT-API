package fr.acore.spigot.api.nms;

import fr.acore.spigot.api.packet.IPacket;
import fr.acore.spigot.api.player.impl.CorePlayer;

public interface INMSPacket<T> extends IPacket {

    public T getEncapsuledPacket();

    public void sendPacket(CorePlayer<?> corePlayer);



}
