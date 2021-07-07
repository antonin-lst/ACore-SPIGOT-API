package fr.acore.spigot.api.packet;

public interface IPacketFactory<T> {

    /*

    Factory du systeme de packet

     */

    //permet de recuperer un packet grace a son id
    public Class<T> getPacketClazz(int id);

    //permet d'ajouter un packet a la liste des packet existant
    public <U extends T> void addPacket(int packetId, Class<U> clazz);
}
