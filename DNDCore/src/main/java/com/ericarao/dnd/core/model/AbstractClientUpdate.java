package com.ericarao.dnd.core.model;

import org.immutables.value.Value;

@DndStyle
@Value.Immutable
public abstract class AbstractClientUpdate implements NetworkPacket {
    public abstract int getDamage();
    public abstract String getStatusEffect();
    public abstract int getSavingThrow();
    public abstract int getCurrentHP();

    @Override
    @Value.Derived
    public PacketType getType() {
        return PacketType.ClientUpdate;
    }
}
