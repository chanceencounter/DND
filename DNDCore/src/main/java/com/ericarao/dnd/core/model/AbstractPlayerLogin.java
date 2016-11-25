package com.ericarao.dnd.core.model;

import org.immutables.value.Value;

@DndStyle
@Value.Immutable
public abstract class AbstractPlayerLogin implements NetworkPacket {
    public abstract String getRoomName();
    public abstract String getRoomPassword();

    @Override
    public PacketType getType() {
        return PacketType.PlayerLogin;
    }
}
