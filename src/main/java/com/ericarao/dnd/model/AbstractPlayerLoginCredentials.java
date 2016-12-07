package com.ericarao.dnd.model;

import org.immutables.value.Value;

@DndStyle
@Value.Immutable
public abstract class AbstractPlayerLoginCredentials{
    public abstract String roomName();
    public abstract String dmIP();
    public abstract String playerName();
    public abstract String roomPassword();
}
