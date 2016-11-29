package com.ericarao.dnd.model;

import org.immutables.value.Value;

@DndStyle
@Value.Immutable
public abstract class AbstractRegisterPlayerResponse implements NetworkPacket {
    public abstract boolean getSuccess();

    @Override
    @Value.Derived
    public PacketType getType() {
        return PacketType.RegisterPlayerResponse;
    }
}
