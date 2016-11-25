package com.ericarao.dnd.core.model;

import org.immutables.value.Value;

@DndStyle
@Value.Immutable
public abstract class AbstractPlayerLoginResponse implements NetworkPacket {
    public abstract boolean getSuccess();

    @Override
    public PacketType getType() {
        return PacketType.PlayerLoginResponse;
    }
}
