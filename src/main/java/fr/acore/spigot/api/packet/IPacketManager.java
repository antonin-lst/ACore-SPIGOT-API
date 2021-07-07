package fr.acore.spigot.api.packet;

import fr.acore.spigot.api.manager.IManager;

public interface IPacketManager<T extends IPacket, U> extends IManager {

    /*

    Base d'un manager de packet

     */

    //envoie un packet
    public abstract void sendPacket(T packet);

    //recoie le packet parsé
    public abstract void handlePacket(U parsedPacket);

    //Factory
    public IPacketFactory<T> getPacketFactory();


}
