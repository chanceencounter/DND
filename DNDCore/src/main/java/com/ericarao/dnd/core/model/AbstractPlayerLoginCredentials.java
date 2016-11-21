package com.ericarao.dnd.core.model;

import org.immutables.value.Value;

@DndStyle
@Value.Immutable
public abstract class AbstractPlayerLoginCredentials implements NetworkPacket{
    public abstract String roomName();
    public abstract String dmIP();
    public abstract String playerName();
    public abstract String roomPassword();
    public abstract String playerIP();


    @Override
    @Value.Derived
    public PacketType getType() {
        return PacketType.PlayerLoginCred;
    }
}
