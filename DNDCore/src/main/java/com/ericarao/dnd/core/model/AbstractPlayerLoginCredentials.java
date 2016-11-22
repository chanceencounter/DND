package com.ericarao.dnd.core.model;

import org.immutables.value.Value;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@DndStyle
@Value.Immutable
public abstract class AbstractPlayerLoginCredentials implements NetworkPacket{
    public abstract String roomName();
    public abstract String dmIP();
    public abstract String playerName();
    public abstract String roomPassword();

    @Value.Derived
    public String playerIP() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Value.Derived
    public PacketType getType() {
        return PacketType.PlayerLoginCred;
    }
}
