package com.ericarao.dnd.core.model;

import org.immutables.value.Value;

import java.net.Inet4Address;
import java.net.UnknownHostException;

@DndStyle
@Value.Immutable
public abstract class AbstractDMLoginCredentials {
    public abstract String roomName();
    public abstract int numPlayers();
    public abstract String roomPassword();

    @Value.Derived
    public String getIP() {
        try {
            return Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

}
