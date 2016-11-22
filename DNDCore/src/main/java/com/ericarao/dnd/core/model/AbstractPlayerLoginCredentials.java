package com.ericarao.dnd.core.model;

import org.immutables.value.Value;
import java.net.Inet4Address;

@DndStyle
@Value.Immutable
public abstract class AbstractPlayerLoginCredentials{
    public abstract String roomName();
    public abstract String dmIP();
    public abstract String playerName();
    public abstract String roomPassword();
}
