package com.ericarao.dnd.core.model;

import org.immutables.value.Value;

@DndStyle
@Value.Immutable
public abstract class AbstractDMLoginCredentials {
    public abstract String roomName();
    public abstract int numPlayers();
    public abstract String roomPassword();
}
