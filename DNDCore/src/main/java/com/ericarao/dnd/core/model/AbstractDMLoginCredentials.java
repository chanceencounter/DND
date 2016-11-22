package com.ericarao.dnd.core.model;

import org.immutables.value.Value;
import java.net.Inet4Address;
import java.net.UnknownHostException;

@DndStyle
@Value.Immutable
public abstract class AbstractDMLoginCredentials implements NetworkPacket{
    public abstract String roomName();
    public abstract int numPlayers();
    public abstract String roomPassword();

    @Value.Derived
    public String dmIP() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Value.Derived
    public PacketType getType() {
        return PacketType.DMLoginCred;
    }
}
